package mtd.cloud.stomp;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mtd.cloud.entity.Timeseries;
import mtd.cloud.repository.TimeseriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class StompPublisher  implements ApplicationRunner {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private TimeseriesRepository timeseriesRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread t = new Thread(() -> {

            log.info("start Stomp...");

            while (true) {
                Random random = new Random();
                Integer temp = random.ints(20, 25)
                        .findFirst()
                        .getAsInt();
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
