package mtd.cloud.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"cmtd.cloud.entity"})
@ComponentScan(basePackages = {"mtd.cloud"})
public class KubeClientWrapperConfiguration {

}
