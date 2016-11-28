package org.keega.idea.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.keega.idea.model.User;
import org.keega.idea.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;


@RequestMapping("/user")
@Controller
public class UserController {

    @Inject
    private IUserService userService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {

        return "/views/datagrid";
    }

    @RequestMapping(value = "/jsp", method = RequestMethod.GET)
    public String go2Jsp(Model model) {
        User user = this.userService.load(7 + "");
        model.addAttribute("user", user);
        return "/views/my";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public void getForm(User user) {
        System.out.println(user);
    }

    /*@RequestMapping(value = "/jsp2", method = RequestMethod.GET)
    public String go2Jsp2(Model model, @RequestParam(name = "name", defaultValue = "fuck", required = true) String name,String testValue) {
        //System.out.println(name);
        return "/views/my2";
    }*/

    @RequestMapping(value = "/jsp2", method = RequestMethod.GET)
    public String go2Jsp2(Model model,@RequestParam(name = "name", defaultValue = "fuck", required = true) String name,String testValue) {
        //System.out.println(name);
        model.addAttribute("id", "60");
        return "/views/my2";
    }

    @RequestMapping(value = "/ajax",method = RequestMethod.POST)
    public @ResponseBody String testAjax(Model model,String id,String nickname) {
        //System.out.println(id + "---" + nickname);
        //model.addAttribute("user",this.userService.load(id));
        //return this.userService.load(id);//以对象的方式返回；
        List<User> user = this.userService.findAllUser();
        //User user = this.userService.load(id);
        //System.out.println(JSON.toJSONStringWithDateFormat(mapList,"yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));
        String userStr = JSON.toJSONString(user, true);//alibaba的fastjson包可以把对象转换Json;也可以转换list集合
        //String userStr = JSONObject.valueToString(user);//此方法用于转换一个List集合成json数组。这个不可以转对象
        //System.out.println(userStr);
        return userStr;
    }

    @RequestMapping(value = "/exeJson",method = RequestMethod.POST)
    public @ResponseBody String testExeJson(String testJson){
        //JSON.parse(testJson);
        //List<User> users = (List<User>) JSON.toJavaObject((JSON) JSON.parse(testJson), User.class);
        List<User> users = JSON.parseArray(testJson, User.class);//将json数组转成java对象List集合。
        for (User user : users) {
            System.out.println(user);
        }
       /* User user = JSON.parseObject(testJson, User.class);
        System.out.println(user);*/
        return null;
    }

    @RequestMapping(value = "/boot", method = RequestMethod.GET)
    public String bootstrap() {
        //System.out.println("aaa");
        return "/user/index";
    }

}



