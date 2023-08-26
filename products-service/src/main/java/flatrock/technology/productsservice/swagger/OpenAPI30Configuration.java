package flatrock.technology.productsservice.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
//@SecurityScheme(
//        name = "Bearer Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
@SecurityScheme(
        name = "security_auth", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        tokenUrl = "http://localhost:8081/oauth2/token",
                        authorizationUrl ="http://localhost:8081/login",
                        scopes = {@OAuthScope(name = "openid", description = "openid scope")}
                )
        )
)
public class OpenAPI30Configuration {
}
