package mtd.cloud.application;

import cloud.kubewrapper.configuration.KubeClientWrapperConfiguration;
import mtd.cloud.transport.configuration.TransportConfiguration;
import mtdcloud.configuration.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import({TransportConfiguration.class, CoreConfiguration.class, KubeClientWrapperConfiguration.class})
@PropertySource("classpath:/application.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
