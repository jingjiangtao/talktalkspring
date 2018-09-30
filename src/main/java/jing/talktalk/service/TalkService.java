package jing.talktalk.service;

import com.alibaba.fastjson.JSONObject;
import jing.talktalk.dao.TalkListDao;
import jing.talktalk.dao.UserDao;
import jing.talktalk.domain.TalkList;
import jing.talktalk.domain.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class TalkService {

    @Autowired
    TalkListDao talkListDao;
    @Autowired
    UserDao userDao;

    public ArrayList<TalkList> getAllTalks(){
        ArrayList<TalkList> talkLists = talkListDao.findAllTalks();
        talkLists.sort((o1, o2) -> {
            if(o1.getTime().compareTo(o2.getTime()) < 0){
                return 1;
            }else{
                return -1;
            }
        });
        talkLists.forEach((value)-> value.getCommentContent().sort((o1, o2)->{
            if(o1.getReplyTime().compareTo(o2.getReplyTime()) < 0){
                return 1;
            }else{
                return -1;
            }
        }));
        return talkLists;
    }

    public User getUserByUsername(String username){
        ArrayList<User> user = userDao.findUserByName(username);
        if(user.size()>0){
            return user.get(0);
        }
        return new User();
    }

    //初始化主页
    public JSONObject getIndexData(HttpSession session){
        JSONObject data = new JSONObject();
        data.put("unlogin", session.getAttribute("unlogin") == null);
        data.put("username", session.getAttribute("username"));
        data.put("avatar", session.getAttribute("avatar"));
        data.put("listResult", getAllTalks());
        data.put("signature", getUserByUsername((String)session.getAttribute("username")).getSignature());
        return data;
    }

    //注册
    public int doSign(String username, String password, HttpSession session){
        if(userDao.findUserByName(username).size() != 0){
            //用户名已存在
            return -1;
        }else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userDao.insertOneUser(user);
            session.setAttribute("unlogin", false);
            session.setAttribute("username", username);
            session.setAttribute("avatar", user.getAvatar());
            return 1;
        }
    }

    //登录
    public int doLogin(String username, String password, HttpSession session){

        ArrayList<User> result = userDao.findUserByNamePwd(username, password);
        if(userDao.findUserByName(username).size() == 0){
            //用户名不存在
            return -1;
        }
        if(result.size() == 0){
            //密码错误
            return -2;
        }
        session.setAttribute("unlogin", false);
        session.setAttribute("username", username);
        session.setAttribute("avatar", result.get(0).getAvatar());
        return 1;
    }

    //修改密码
    public int updatePassword(String oldPwd, String newPwd, HttpSession session){
        String username = (String)session.getAttribute("username");
        ArrayList<User> result = userDao.findUserByNamePwd(username, oldPwd);
        if(result.size() == 0){
            //旧密码错误
            return -2;
        }
        if(result.get(0).getPassword().equals(newPwd)){
            //新密码与旧密码相同
            return -3;
        }
        result.get(0).setPassword(newPwd);
        userDao.updateUser(result.get(0));
        return 1;
    }

    //修改签名
    public int modifySign(String signature, HttpSession session){
        User user = userDao.findUserByName((String)session.getAttribute("username")).get(0);
        user.setSignature(signature);
        userDao.updateUser(user);
        return 1;
    }

    //点赞
    public int doZan(ObjectId id, String username, HttpSession session){
        System.out.println(id);
        System.out.println(username);
        ArrayList<TalkList> talkResult = talkListDao.findTalkById(id);
        ArrayList<User> userResult = userDao.findUserByName(username);
        if(talkResult.size() == 0 || userResult.size() == 0){
            return -1;
        }
        talkResult.get(0).setZanNum(talkResult.get(0).getZanNum() + 1);
        talkResult.get(0).getZanPerson().add((String)session.getAttribute("username"));
        talkListDao.updateTalk(talkResult.get(0));
        userResult.get(0).setSumZan(userResult.get(0).getSumZan() + 1);
        userDao.updateUser(userResult.get(0));
        return 1;
    }
}
