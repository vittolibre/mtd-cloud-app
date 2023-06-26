package mtd.cloud.mqtt;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mtd.cloud.entity.Timeseries;
import mtd.cloud.repository.TimeseriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Slf4j
//@Component
public class MqttSubscriber {
    @Value("${mqtt.client.id}")
    private String id;
    @Value("${mqtt.server.host}")
    private String host;
    @Value("${mqtt.server.port}")
    private String port;

    @Autowired
    private TimeseriesRepository timeseriesRepository;
    @SneakyThrows
    public void subscribe(){

        Mqtt5BlockingClient client = Mqtt5Client.builder()
                .identifier(id)
                .serverHost(host)
                .serverPort(Integer.valueOf(port))
                .buildBlocking();

        client.connect();

        byte[] secretKey = "9mng65v8jf4lxn93nabf981m".getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "TripleDES");
        byte[] iv = "a76nb5h9".getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        SecretKey key;
        try {
            //key = generateKey(128);
            key = getKeyFromPassword("password", "salt");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String algorithm = "AES/CBC/PKCS5Padding";
        IvParameterSpec ivParameterSpec = generateIv();

        client.toAsync().subscribeWith()
                .topicFilter("events")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(message -> {
                    String str = new String(message.getPayloadAsBytes(), StandardCharsets.UTF_8);
                    log.info(str);
                    String decrypted = "";

                    if(str.contains("AES")){
                        try {
                            log.info(str.substring(3));
                            str = str.substring(3);
                            decrypted = decrypt(algorithm, str, key, ivParameterSpec);
                            log.info("decrypted message with AES: {}", decrypted);
                        } catch (NoSuchPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidAlgorithmParameterException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeyException e) {
                            throw new RuntimeException(e);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalBlockSizeException e) {
                            throw new RuntimeException(e);
                        }
                    } else if(str.contains("3DES")){
                        str = str.substring(4);
                        byte[] base64decoded = Base64.getDecoder().decode(str);
                        //log.info(new String(base64decoded));
                        log.info(str);
                        decrypted = decrypt3DES(base64decoded, ivSpec, secretKeySpec);
                        log.info("decrypted message with 3DES: {}", decrypted);
                    }
                    Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap(decrypted);
                    Timeseries timeseries =  new Timeseries();
                    timeseries.setEdgeHostname(map.get("hostname").toString());
                    timeseries.setIdDevice(map.get("id_device").toString());
                    timeseries.setDeviceAttribute(map.get("device_attribute").toString());
                    timeseries.setDeviceAttributeType(map.get("device_attribute_type").toString());
                    timeseries.setDeviceAttributeValue(map.get("device_attribute_value").toString());
                    timeseries.setTimestamp(Date.from(Instant.now()));
                    timeseriesRepository.save(timeseries);
                }).send();


    }

    @SneakyThrows
    private String decrypt3DES(byte[] encryptedMessageBytes, IvParameterSpec ivSpec, SecretKeySpec secretKeySpec){
        Cipher decryptCipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] decryptedMessageBytes;
        decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
        return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
    }

    public static SecretKey generateKey(int n)
            throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    public static IvParameterSpec generateIv() {
        byte b = 1;
        byte[] iv = new byte[]{b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b};
        //new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }
}
