package flatrock.technology.orderservice;

import flatrock.technology.orderservice.config.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableConfigurationProperties(MyConfig.class)
//@RefreshScope
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
