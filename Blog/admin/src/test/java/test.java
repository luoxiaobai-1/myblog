
import my.Main1;

import my.blog.mapper.*;

import my.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest(classes = Main1.class)
public class test {

    @Autowired
    UserService userService;
    @Autowired
   MenuMapper menuMapper;

    @Test
    public  void TestTimeSource ()

    {


        System.out.println(menuMapper.selectPermsbyuserid(1652587371021033479L));
    }


}
