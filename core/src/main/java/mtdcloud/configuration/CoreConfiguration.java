package mtdcloud.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages ={"mtdcloud.repository"})
@EntityScan(basePackages = {"mtdcloud.entity"})
@ComponentScan(basePackages = "mtdcloud.service")
public class CoreConfiguration {
}
