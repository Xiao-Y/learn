import com.billow.user.AdminUserApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试redis
 *
 * @author liuyongtao
 * @create 2018-05-11 10:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminUserApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        System.out.println(redisTemplate);
    }
}
