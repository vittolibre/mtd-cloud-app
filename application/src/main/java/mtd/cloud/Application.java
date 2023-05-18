package mtd.cloud;

import mtd.cloud.configuration.KubeClientWrapperConfiguration;
import mtd.cloud.configuration.CoreConfiguration;
import mtd.cloud.configuration.TransportConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages ={"mtd.cloud.repository"})
@Import({TransportConfiguration.class, CoreConfiguration.class, KubeClientWrapperConfiguration.class})
@PropertySource("classpath:/application.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
