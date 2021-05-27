package com.citc.cso.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    
    
    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//    	http.csrf().disable();
    	   // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/about").permitAll();
 
        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
        // If no login, it will redirect to /login page.
//        http.authorizeRequests().antMatchers("/userInf").hasAnyAuthority("USER", "ADMIN");
 
        // For ADMIN only.
//        http.authorizeRequests().antMatchers("/adminPage").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers("/adminPage").hasAnyRole("ADMIN");
    	
    	http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.failureHandler(authenticationFailureHandler)
            	.successHandler(authenticationSuccessHandler)
                .loginPage("/login")
                //.usernameParameter("email") if need use different name for the username field in the custom login form, e.g. email
                //.passwordParameter("myPassword") if need use different name for the password field in the custom login form, e.g. email
                //.loginProcessingUrl("/doLogin");   form th:action="@{/doLogin}"
                //.failureUrl("/login_error");By default, Spring Security will redirects to /login?error if the user failed to login. If you want to change this behavior, e.g. showing your own page that displays login error message to the user – then specify the custom login error page
                //.successForwardUrl("/login_success_handler"); If you want to execute some extra code after the user has logged in successfully, e.g. logging or auditing, then specify the success forward URL
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout()
            	//.logoutUrl("/doLogout"); By default, Spring Security processes the /logout URL via HTTP POST method. You can configure the HttpSecurity object to change this URL
                //<form th:action="@{/doLogout}" method="post">
            	.permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    	auth
//        .inMemoryAuthentication()
//        .withUser("MElmahask").password("$2a$10$Cca/CvNz/X3s2wTjyYOIOeZYDNKzgMcE6fZAXjahkcU/02iu49K4K").roles("USER")
//        .and()
//        .withUser("elmahask").password("$2a$10$DHV4Un2fGeFrnIWPDF9ru.LITKPJ/erFi5LaN7.NYY0iZ.fucLN0q").roles("ADMIN");
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
