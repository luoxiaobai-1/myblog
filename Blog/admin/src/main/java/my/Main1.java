package my;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableTransactionManagement
public class Main1 {
    public static void main(String[] args) {
        SpringApplication.run(Main1.class,args);


    }

}
