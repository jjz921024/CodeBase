import gdut.SpringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Jun on 2018/2/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:AppContextMybatis.xml")
public class SpringMybatisTest {
    @Resource
    private SpringService springService;

    @Test
    public void test1() {
        springService.doService();
    }
}
