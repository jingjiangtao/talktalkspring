package jing.talktalk;

import jing.talktalk.dao.TalkListDao;
import jing.talktalk.dao.UserDao;
import jing.talktalk.domain.TalkList;
import jing.talktalk.domain.User;
import jing.talktalk.service.TalkService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TalktalkApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TalkListDao talkListDao;
    @Autowired
    private TalkService talkService;

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();// 获取mockMvc实例
    }
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
        talkList.setUsername("ddd");
        talkList.setAvatarPath("default.jpg");
        talkList.setTime(new Date());
        talkListDao.insertOneTalk(talkList);
    }

    @Test
    public void deleteOneTalkTest(){
        ArrayList<TalkList> result = talkListDao.findTalkById(new ObjectId("5bad9d63bf354212d010c836"));
        result.forEach((value)->{
            System.out.println(value);
            talkListDao.deleteOneTalk(value.getId());
        });
    }

    @Test
    public void findOneByUsernameTest(){
        ArrayList<TalkList> result = talkListDao.findTalksByUsername("指压闪光师");
        result.forEach(System.out::println);
    }

    @Test
    public void findAllTalksTest(){
        talkListDao.findAllTalks().forEach((value)->{
            System.out.println(value);
        });
    }

    @Test
    public void updateTalkTest(){
        ArrayList<TalkList> result = talkListDao.findTalksByUsername("vbs");
        result.forEach((value)->{
            value.setTalkContent("哈哈哈，我是vbs，这是修改后的说说哦");
            talkListDao.updateTalk(value);
        });
        talkListDao.findTalksByUsername("vbs").forEach((value)->{
            System.out.println(value);
        });
    }

    @Test
    public void getAllTalksTest(){
        System.out.println(talkService.getAllTalks());
    }

    @Test
    public void doSignTest() throws Exception{
        String requestBody = "{\"username\":\"jing\", \"password\":\"123456\"}";
        mockMvc.perform(
                get("/dosign").contentType(MediaType.APPLICATION_JSON).content(requestBody));

    }
}
