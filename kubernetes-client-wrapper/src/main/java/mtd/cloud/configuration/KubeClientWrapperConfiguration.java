package mtd.cloud.configuration;

import mtd.cloud.service.StrategyService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"cmtd.cloud.entity"})
@ComponentScan(basePackages = {"mtd.cloud"})
public class KubeClientWrapperConfiguration {

}
