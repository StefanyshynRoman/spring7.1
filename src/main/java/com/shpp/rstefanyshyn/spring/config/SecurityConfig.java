package com.shpp.rstefanyshyn.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class SecurityConfig  {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests()
//                .antMatchers("/task/**").authenticated()
//                .antMatchers(HttpMethod.POST, "/task").hasRole("ADMIN") // Додайте цей рядок
//                .antMatchers(HttpMethod.DELETE, "/task/**").hasRole("ADMIN") // Додайте цей рядок
//                .antMatchers(HttpMethod.GET, "/task/{id}/**").authenticated()
//                .antMatchers(HttpMethod.PUT, "/task/{id}/status").authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .logout().logoutSuccessUrl("/")
//                .and()
//                .httpBasic();
//
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/task").hasRole("ADMIN") // Додайте цей рядок
                .antMatchers(HttpMethod.DELETE, "/task/{id}").hasRole("ADMIN") // Додайте цей рядок
                .antMatchers(HttpMethod.GET, "/task/{id}/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.PUT, "/task/{id}/status").hasAnyRole("ADMIN","USER")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService user(){
        UserDetails user= User.builder()
                .username("User")
                .password("{bcrypt}$2a$12$5rU0G8y0FNICyvruFIC4keZohyQuTRybMRqzUDgOa5Q2YFWk0dSku")
                .roles("USER")
                .build();

            UserDetails admin= User.builder()
                    .username("Admin")
                    .password("{bcrypt}$2a$12$hrMvuBMzycL/XmNeWnw.RucZvxVPDmhyukjRAosJBbuJWIrebfPaW")
                    .roles("ADMIN","USER")
                    .build();

                return new InMemoryUserDetailsManager(user,admin);
    }

//@Bean
//public JdbcUserDetailsManager users(DataSource dataSource){
//    UserDetails user= User.builder()
//            .username("User")
//            .password("{bcrypt}$2a$12$5rU0G8y0FNICyvruFIC4keZohyQuTRybMRqzUDgOa5Q2YFWk0dSku")
//            .roles("USER")
//            .build();
//
//    UserDetails admin= User.builder()
//            .username("Admin")
//            .password("{bcrypt}$2a$12$hrMvuBMzycL/XmNeWnw.RucZvxVPDmhyukjRAosJBbuJWIrebfPaW")
//            .roles("ADMIN","USER")
//            .build();
//JdbcUserDetailsManager users=new JdbcUserDetailsManager(dataSource);
//users.createUser(user);
//users.createUser(admin);
//    return users;
//}

}
