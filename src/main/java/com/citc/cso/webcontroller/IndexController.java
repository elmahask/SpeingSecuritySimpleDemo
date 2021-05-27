package com.citc.cso.webcontroller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.citc.cso.model.User;
import com.citc.cso.service.SecurityService;
import com.citc.cso.service.UserService;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
    	model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcome";
    }
 
    @GetMapping("/adminPage")
    public String adminPage(Model model, Principal principal) {
//        User loginedUser = (User) ((Authentication) principal).getPrincipal();
//        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", securityService.isAuthenticated()+" "+principal.getName());
         
        return "adminPage";
    }
 
    @GetMapping("/userInf")
    public String userInfo(Model model, Principal principal) {
        // After user login successfully.
        String userName = principal.getName();
        System.out.println("User Name: " + userName);
        model.addAttribute("userInfo", securityService.isAuthenticated());
 
        return "userInfoPage";
    }
 
    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("userInfo", securityService.isAuthenticated());
        return "about";
    }
    
    @GetMapping("/403")
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
//            User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
//            String userInfo = WebUtils.toString(loginedUser);
 
            model.addAttribute("userInfo", principal.getName());
 
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
 
        }
 
        return "403Page";
    }
}
