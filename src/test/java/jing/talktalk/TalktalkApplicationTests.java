package jing.talktalk;

import jing.talktalk.dao.TalkListDao;
import jing.talktalk.dao.UserDao;
import jing.talktalk.domain.TalkList;
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
    private UserDao userDao;
    @Autowired
    private TalkListDao talkListDao;

    @Test
    public void contextLoads() {

    }

    @Test
    public void finAllUsersTest(){
        ArrayList<User> result = userDao.findAllUsers();
        result.forEach((user)->{
            System.out.println(user.toString());
        });

    }


    @Test
    public void findUserByNameTest(){
        ArrayList<User> result = userDao.findUserByName("jing");
        System.out.println(result.size());
        try {
            System.out.println(result.get(0).toString());
        }catch (Exception e){
            System.out.println("用户名不存在");
        }
    }

    @Test
    public void findUserByNamePwdTest(){
        ArrayList<User> result = userDao.findUserByNamePwd("jing", "4QrcOUm6Wau+VuBX8g+IPg==");
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
        userDao.insertOneUser(user);
        userDao.findUserByName("ddd").forEach((value)->{
            System.out.println(value);
        });
    }

    @Test
    public void updatePwdTest(){
        try {
            userDao.findUserByName("ddd").forEach((user)->{
                int sumZan = user.getSumZan();
                user.setSumZan(++sumZan);
                userDao.updateUser(user);
                userDao.findUserByName("ddd").forEach((value)->{
                    System.out.println(value);
                });
            });
        }catch (Exception e){
            System.out.println("用户名不存在");
        }
    }

    @Test
    public void deleteOneUserTest(){
        userDao.deleteOneUser("ddd");
        try {
            userDao.findUserByName("ddd").get(0);
        }catch (Exception e){
            System.out.println("用户名不存在");
        }

    }

    @Test
    public void insertOneTalkTest(){
        TalkList talkList = new TalkList();

    }

}
