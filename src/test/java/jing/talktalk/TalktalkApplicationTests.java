package jing.talktalk;

import jing.talktalk.dao.DBDao;
import jing.talktalk.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TalktalkApplicationTests {

    @Autowired
    private DBDao dbDao;

    @Test
    public void contextLoads() {

    }

    @Test
    public void finAllUsersTest(){
        ArrayList<User> result = dbDao.findAllUsers();
        result.forEach((user)->{
            System.out.println(user.toString());
        });

    }


    @Test
    public void findUserByNameTest(){
        ArrayList<User> result = dbDao.findUserByName("jing");
        System.out.println(result.size());
        try {
            System.out.println(result.get(0).toString());
        }catch (Exception e){
            System.out.println("用户名不存在");
        }
    }

    @Test
    public void findUserByNamePwdTest(){
        ArrayList<User> result = dbDao.findUserByNamePwd("jing", "4QrcOUm6Wau+VuBX8g+IPg==");
        System.out.println(result.size());
        try {
            System.out.println(result.get(0).toString());
        }catch (Exception e){
            System.out.println("用户名或密码不正确");
        }
    }

    @Test
    public void insertOneUserTest(){
        User user = new User();
        user.setUsername("ddd");
        user.setPassword("123456");
        user.setSignature("hahahahahah");
        dbDao.insertOneUser(user);
        dbDao.findUserByName("ddd").forEach((value)->{
            System.out.println(value);
        });
    }

    @Test
    public void updatePwdTest(){
        try {
            dbDao.findUserByName("ddd").forEach((user)->{
                int sumZan = user.getSumZan();
                user.setSumZan(++sumZan);
                dbDao.updateUser(user);
                dbDao.findUserByName("ddd").forEach((value)->{
                    System.out.println(value);
                });
            });
        }catch (Exception e){
            System.out.println("用户名不存在");
        }
    }

    @Test
    public void deleteOneUserTest(){
        dbDao.deleteOneUser("ddd");
        try {
            dbDao.findUserByName("ddd").get(0);
        }catch (Exception e){
            System.out.println("用户名不存在");
        }

    }

}
