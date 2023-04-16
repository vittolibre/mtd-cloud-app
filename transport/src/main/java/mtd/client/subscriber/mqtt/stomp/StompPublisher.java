package mtd.client.subscriber.mqtt.stomp;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import mtdcloud.entity.Timeseries;
import mtdcloud.repository.TimeseriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class StompPublisher {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private TimeseriesRepository timeseriesRepository;

    @PostConstruct
    public void publish() {

        Thread t = new Thread(() -> {

            while (true) {
//                Random random = new Random();
//                Integer temp = random.ints(20, 25)
//                        .findFirst()
//                        .getAsInt();
                Timeseries ts = timeseriesRepository.findTopByDeviceAttributeOrderByTimestampDesc("temperature");
                if (ts != null) {

                    log.info(ts.toString());
                    simpMessagingTemplate.convertAndSend("/topic/device/1", ts.getIdDevice() + "/" + ts.getDeviceAttributeValue());
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        t.setName("stomp-publisher");
        t.start();
    }
}
