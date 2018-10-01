package jing.talktalk.service;

import com.alibaba.fastjson.JSONObject;
import jing.talktalk.dao.TalkListDao;
import jing.talktalk.dao.UserDao;
import jing.talktalk.domain.Comment;
import jing.talktalk.domain.TalkList;
import jing.talktalk.domain.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TalkService {

    @Autowired
    TalkListDao talkListDao;
    @Autowired
    UserDao userDao;

    private ArrayList<TalkList> getAllTalks() {
        ArrayList<TalkList> talkLists = talkListDao.findAllTalks();
        talkLists.sort((o1, o2) -> {
            if (o1.getTime().compareTo(o2.getTime()) < 0) {
                return 1;
            } else {
                return -1;
            }
        });
        talkLists.forEach((value) -> {
            value.getCommentContent().sort((o1, o2) -> {
                if (o1.getReplyTime().compareTo(o2.getReplyTime()) < 0) {
                    return 1;
                } else {
                    return -1;
                }
            });
            value.setIdString(value.getId().toHexString());
        });
        return talkLists;
    }

    private User getUserByUsername(String username) {
        ArrayList<User> user = userDao.findUserByName(username);
        if (user.size() > 0) {
            return user.get(0);
        }
        return new User();
    }

    //初始化主页
    public JSONObject getIndexData(HttpSession session) {
        JSONObject data = new JSONObject();
        data.put("unlogin", session.getAttribute("unlogin") == null);
        data.put("username", session.getAttribute("username"));
        data.put("avatar", session.getAttribute("avatar"));
        data.put("listResult", getAllTalks());
        data.put("signature", getUserByUsername((String) session.getAttribute("username")).getSignature());
        return data;
    }

    //注册
    public int doSign(String username, String password, HttpSession session) {
        if (userDao.findUserByName(username).size() != 0) {
            //用户名已存在
            return -1;
        } else {
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
    public int doLogin(String username, String password, HttpSession session) {

        ArrayList<User> result = userDao.findUserByNamePwd(username, password);
        if (userDao.findUserByName(username).size() == 0) {
            //用户名不存在
            return -1;
        }
        if (result.size() == 0) {
            //密码错误
            return -2;
        }
        session.setAttribute("unlogin", false);
        session.setAttribute("username", username);
        session.setAttribute("avatar", result.get(0).getAvatar());
        return 1;
    }

    //修改密码
    public int updatePassword(String oldPwd, String newPwd, HttpSession session) {
        String username = (String) session.getAttribute("username");
        ArrayList<User> result = userDao.findUserByNamePwd(username, oldPwd);
        if (result.size() == 0) {
            //旧密码错误
            return -2;
        }
        if (result.get(0).getPassword().equals(newPwd)) {
            //新密码与旧密码相同
            return -3;
        }
        result.get(0).setPassword(newPwd);
        userDao.updateUser(result.get(0));
        return 1;
    }

    //修改签名
    public int modifySign(String signature, HttpSession session) {
        User user = userDao.findUserByName((String) session.getAttribute("username")).get(0);
        user.setSignature(signature);
        userDao.updateUser(user);
        return 1;
    }

    //点赞
    public int doZan(ObjectId id, String username, HttpSession session) {
        ArrayList<TalkList> talkResult = talkListDao.findTalkById(id);
        ArrayList<User> userResult = userDao.findUserByName(username);
        if (talkResult.size() == 0 || userResult.size() == 0) {
            return -1;
        }
        talkResult.get(0).setZanNum(talkResult.get(0).getZanNum() + 1);
        talkResult.get(0).getZanPerson().add((String) session.getAttribute("username"));
        talkListDao.updateTalk(talkResult.get(0));
        userResult.get(0).setSumZan(userResult.get(0).getSumZan() + 1);
        userDao.updateUser(userResult.get(0));
        return 1;
    }

    //取消点赞
    public int doCancelZan(ObjectId id, String username, HttpSession session) {
        ArrayList<TalkList> talkResult = talkListDao.findTalkById(id);
        ArrayList<User> userResult = userDao.findUserByName(username);
        if (talkResult.size() == 0 || userResult.size() == 0) {
            return -1;
        }
        talkResult.get(0).setZanNum(talkResult.get(0).getZanNum() - 1);
        String user = (String) session.getAttribute("username");
        talkResult.get(0).getZanPerson().remove(user);
        talkListDao.updateTalk(talkResult.get(0));
        userResult.get(0).setSumZan(userResult.get(0).getSumZan() - 1);
        userDao.updateUser(userResult.get(0));
        return 1;
    }

    //回复
    public JSONObject replay(String textContent, String replyUsername, ObjectId id, HttpSession session) {
        Comment comment = new Comment();
        comment.setReplyContent(textContent);
        comment.setReplyTime(new Date());
        comment.setReplyToUser(replyUsername);
        comment.setReplyUser((String) session.getAttribute("username"));
        ArrayList<TalkList> talkResult = talkListDao.findTalkById(id);
        if (talkResult.size() == 0) {
            JSONObject result = new JSONObject();
            result.put("result", -1);
            return result;
        }
        talkResult.get(0).setCommentNum(talkResult.get(0).getCommentNum() + 1);
        talkResult.get(0).getCommentContent().add(comment);
        talkListDao.updateTalk(talkResult.get(0));
        JSONObject result = new JSONObject();
        result.put("result", 1);
        result.put("replyUser", comment.getReplyUser());
        result.put("replyTime", comment.getReplyTime());
        return result;
    }

    //删除说说
    public int deleteTalk(ObjectId id, HttpSession session) {
        ArrayList<User> userResult = userDao.findUserByName((String) session.getAttribute("username"));
        ArrayList<TalkList> talkResult = talkListDao.findTalkById(id);
        if (userResult.size() == 0 || talkResult.size() == 0) {
            return -1;
        }
        //删除有关此说说的图片
        ArrayList<String> talkImages = talkResult.get(0).getTalkImages();
        int flag = 1;
        String prefix = System.getProperty("user.dir") + "/src/main/resources/static";
        for (int i = 0; i < talkImages.size(); i++) {
            File file = new File(prefix + talkImages.get(i));
            System.out.println(prefix + talkImages.get(i));
            if (!file.delete()) {
                flag = -1;
            }
        }
        if (flag == 1) {
            //总赞数减一
            userResult.get(0).setSumTalk(userResult.get(0).getSumTalk() - 1);
            userDao.updateUser(userResult.get(0));
            //删除说说
            talkListDao.deleteOneTalk(id);
        }
        return flag;
    }

    //显示所有
    public ArrayList<User> allMembers() {
        ArrayList<User> allUsers = userDao.findAllUsers();
        allUsers.sort((o1, o2) -> {
            if (o1.getSumZan() > o2.getSumZan()) {
                return -1;
            } else if (o1.getSumZan() < o2.getSumZan()) {
                return 1;
            } else {
                if (o1.getSumTalk() > o2.getSumTalk()) {
                    return -1;
                } else if (o1.getSumTalk() < o2.getSumTalk()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return allUsers;
    }

}
