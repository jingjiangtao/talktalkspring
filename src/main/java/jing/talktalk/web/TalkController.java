package jing.talktalk.web;


import jing.talktalk.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TalkController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/getindexdata", method = RequestMethod.GET)
    public User getIndexData(){
        User user = new User();
        user.setUsername("jing");
        user.setPassword("123456");
        user.setSignature("Don't worry don't cry");
        return user;
    }
}
