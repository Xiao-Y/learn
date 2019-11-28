import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author liuyongtao
 * @create 2019-11-13 9:07
 */
public class PasswordEncoder {

    @Test
    public void encoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("swagger"));
    }
}
