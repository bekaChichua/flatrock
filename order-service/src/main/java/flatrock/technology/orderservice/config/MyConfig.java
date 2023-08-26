package flatrock.technology.orderservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("api")
@Data
public class MyConfig {
    private String name;
}
