package com.citc.cso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigWebMvc implements WebMvcConfigurer {
 

	/**
	 * Now, to serve the new admin.html page, we must add this page to our MvcConfig.
	 * If you don’t have any custom logic that needs to be performed before showing the login page,
	 * you can simply specify the view name resolution in a Spring MVC configuration class
	 * */
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("welcome");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/adminPage").setViewName("adminPage");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/userInf").setViewName("userInfoPage");
        registry.addViewController("/403").setViewName("403Page");
        registry.addViewController("/about").setViewName("about");
    }

}