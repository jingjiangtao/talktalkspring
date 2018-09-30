package jing.talktalk.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class TalkController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/getindexdata", method = RequestMethod.GET)
    public String getIndexData(){

        ArrayList<String> str = new ArrayList<>();
        str.add("aaa");
        str.add("bbb");
        str.add("ccc");
        JSONObject json = new JSONObject();
        json.put("a", "b");
        json.put("str", str);

        return json.toJSONString();
    }
}
