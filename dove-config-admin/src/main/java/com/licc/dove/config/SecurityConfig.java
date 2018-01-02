package com.licc.dove.config;

import com.licc.dove.security.UserDetailService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;


/**
 * @author Jonsy
 *
 */
@Configuration
@EnableWebSecurity //注释掉可以既能享受到springboot的自动配置又能覆盖某些配置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected UserDetailService userDetailService;







    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and().formLogin()
             .loginProcessingUrl("/doLogin")

            .loginPage("/login")
            .failureUrl("/login?error=true")
            .defaultSuccessUrl("/index").successHandler(new AuthenticationSuccessHandler()).successForwardUrl("/index")
             //.failureHandler(new AuthenticationFailHandler())
            .permitAll().and().exceptionHandling().accessDeniedHandler(new  MyAccessDeniedHandler()).and()
            .logout().logoutSuccessHandler(new LogoutSuccessHandler());


    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());


    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/menu/**","/config/**","/register","/**/*.js", "/**/*.js.map", "/**/*.ts", "/**/*.css", "/**/*.css.map", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.fco", "/**/*.woff", "/**/*.woff2", "/**/*.font", "/**/*.svg", "/**/*.ttf", "/**/*.pdf","/*.ico", "/404", "/401","/403", "/error");
    }

    protected boolean isAjax(HttpServletRequest request) {
        return StringUtils.isNotBlank(request.getHeader("X-Requested-With"));
    }
    private class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

            clearAuthenticationAttributes(request);
            if (!isAjax(request)) {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
    private class AuthenticationFailHandler implements AuthenticationFailureHandler {


        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
              HttpSession session =  request.getSession();

              response.sendRedirect("/login?error=true");
        }
    }
    @Component
    public class MyAccessDeniedHandler implements AccessDeniedHandler {


        @Override
        public void handle(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AccessDeniedException e) throws IOException, ServletException {

            Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");

        }
    }
    private class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
            if (!isAjax(request)) {
                super.onLogoutSuccess(request, response, authentication);
            }
        }
    }





}
