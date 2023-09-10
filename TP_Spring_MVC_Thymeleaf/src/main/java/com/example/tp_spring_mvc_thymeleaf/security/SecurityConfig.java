package com.example.tp_spring_mvc_thymeleaf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager=new
                JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }
    //@Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("123456")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("123456")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("123456")).roles("USER","ADMIN").build()
        );

        return inMemoryUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //un formulaire est demander pour s'authentifier
        httpSecurity.formLogin();
        httpSecurity.authorizeHttpRequests().requestMatchers("/delete","/editPatient","/savePatient").hasRole("ADMIN");
        /*pour acceder à n'importe quil fonctionnalité de l'aapplication
        tu doit s'authentifier d'abord en premier*/
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();

        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");

        return httpSecurity.build();

    }

}
