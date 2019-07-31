package com.yorosoft.econsom.controller;

import com.yorosoft.econsom.Dao.RoleDao;
import com.yorosoft.econsom.Service.UserService;
import com.yorosoft.econsom.models.User;
import com.yorosoft.econsom.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        ModelAndView view = new ModelAndView();
        view.setViewName("dashboard");
        return view;
    }

    @GetMapping("/page-forgot-password")
    public ModelAndView resetPasswordPage(){
        ModelAndView view = new ModelAndView();
        view.setViewName("page-forgot-password");
        return view;
    }

    @GetMapping("/page-lockscreen")
    public ModelAndView lockscreenPage(){
        ModelAndView view = new ModelAndView();
        view.setViewName("page-lockscreen");
        return view;
    }

    @GetMapping("/page-register")
    public String registerPage(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "page-register";
    }

    @PostMapping(value = "/page-register")
    public String registerPagePost(@ModelAttribute("user") User user, Model model) {
        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {
            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }
            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }
            return "page-register";
        } else {
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName("USER")));
            userService.createUser(user, userRoles);
            model.addAttribute("userCreated", true);
            return "page-register";
        }
    }

}
