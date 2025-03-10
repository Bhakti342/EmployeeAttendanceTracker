//package com.example.project.employee_attendance_tracker.config;
//
//import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//         auth.userDetailsService(userDetailsService);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/auth/register", "/auth/login").permitAll()
//                .antMatchers("/employee/**").hasAnyRole("EMPLOYEE", "HR")
//                .antMatchers("/hr/**").hasRole("HR")
//                .and()
//                .formLogin()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    HttpSession session = request.getSession(false);
//                    if (session != null) {
//                        session.invalidate();
//                    }
//                        response.setContentType("application/json");
//                    response.setStatus(HttpServletResponse.SC_OK);
//                    response.getWriter().write("{\"message\": \"Logged out successfully!\"}");
//                })
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.setContentType("application/json");
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.getWriter().write("{\"error\": \"Unauthorized access\"}");
//                });
//    }
//}
