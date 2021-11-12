package org.demo.springbootThymeleaf;

import org.demo.springbootThymeleaf.user.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppUserServiceImplTests {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void saveTest() {

//        User me = new User();
//        me.setName("Jakub");
//        me.setLastName("Przygodzki");
//        me.setEmail("jakub.przygodzki@onet.pl");
//        me.setPhone("123456789");
//
//        userServiceImpl.save(me);
//
//        assertThat(userServiceImpl.findAll().size())
//                .isEqualTo(1);
    }

    @Test
    public void findAllTest() {

        assertThat(userServiceImpl.findAll()).isInstanceOf(java.util.ArrayList.class);
    }
}
