package ma.payment.auth.jwt;

import org.springframework.context.annotation.Configuration;


@Configuration

public class JwtConfig {

    String secretKey = "anasanasanasanassanassanassanass";
    String tokenPrefix= "Bearer ";

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public JwtConfig() {
    }
}
