package jing.talktalk.web;


import com.alibaba.fastjson.JSONObject;
import jing.talktalk.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class TalkController {

    @Autowired
    TalkService talkService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    //页面初始化
    @ResponseBody
    @RequestMapping(value = "/getindexdata", method = RequestMethod.GET)
    public JSONObject getIndexData(HttpSession session){
        return talkService.getIndexData(session);
    }

    //注册
    @ResponseBody
    @RequestMapping(value = "/dosign", method = RequestMethod.POST)
    public JSONObject doSign(String username, String password, HttpSession session){
        int result = talkService.doSign(username, password, session);
        JSONObject data = new JSONObject();
        data.put("result", result);
        return data;
    }

    //登录
    @ResponseBody
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public JSONObject doLogin(String username, String password, HttpSession session){
        int result = talkService.doLogin(username, password, session);
        JSONObject data = new JSONObject();
        data.put("result", result);
        return data;
    }

    //修改密码
    @ResponseBody
    @RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
    public JSONObject updatePassword(String oldPwd, String newPwd, HttpSession session){
        int result = talkService.updatePassword(oldPwd, newPwd, session);
        JSONObject data = new JSONObject();
        data.put("result", result);
        return data;
    }

    //修改签名
    @ResponseBody
    @RequestMapping(value = "/modifysign", method = RequestMethod.GET)
    public JSONObject modifySign(String signature, HttpSession session){
        JSONObject data = new JSONObject();
        data.put("result", talkService.modifySign(signature, session));
        return data;
    }

    //退出登录
    @ResponseBody
    @RequestMapping(value = "/quit", method = RequestMethod.GET)
    public JSONObject quit(HttpSession session){
        session.invalidate();
        JSONObject result = new JSONObject();
        result.put("result", 1);
        return result;
    }

    //修改头像
    @ResponseBody
    @RequestMapping(value = "/modiavatar", method = RequestMethod.POST)
    public JSONObject modifyAvatar(){
        return new JSONObject();
    }

    //发送动态
    @ResponseBody
    @RequestMapping(value = "/sendstate", method = RequestMethod.GET)
    public JSONObject sendState(){
        return new JSONObject();
    }

    //上传图片
    @ResponseBody
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public JSONObject postImg(){
        return new JSONObject();
    }

    //点赞
    @ResponseBody
    @RequestMapping(value = "/dozan", method = RequestMethod.GET)
    public JSONObject doZan(){
        return new JSONObject();
    }

    //取消点赞
    @ResponseBody
    @RequestMapping(value = "/docancelzan", method = RequestMethod.GET)
    public JSONObject doCancelZan(){
        return new JSONObject();
    }

    //回复
    @ResponseBody
    @RequestMapping(value = "/replay", method = RequestMethod.GET)
    public JSONObject replay(){
        return new JSONObject();
    }

    //删除一条说说
    @ResponseBody
    @RequestMapping(value = "/deletetalk", method = RequestMethod.GET)
    public JSONObject deleteTalk(){
        return new JSONObject();
    }

    //列出所有用户
    @ResponseBody
    @RequestMapping(value = "/allmembers", method = RequestMethod.GET)
    public JSONObject allMembers(){
        return new JSONObject();
    }

}
