package ma.youcode.supplychainxjwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication(scanBasePackages = "ma.youcode.supplychainxjwt")
public class SupplyChainXApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyChainXApplication.class, args);
//        SecretKey key = Jwts.SIG.HS256.key().build();
//        String base64 = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println(base64);
    }

}
