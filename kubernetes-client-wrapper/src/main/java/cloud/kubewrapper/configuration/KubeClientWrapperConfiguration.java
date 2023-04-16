package cloud.kubewrapper.configuration;

import cloud.kubewrapper.service.StrategyService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages ={"cloud.kubewrapper.repository"})
@EntityScan(basePackages = {"cloud.kubewrapper.entity"})
@ComponentScan(basePackages = {"cloud.kubewrapper.service"})
public class KubeClientWrapperConfiguration {

    @Bean
    StrategyService strategyService(){
        return new StrategyService();
    }
}
