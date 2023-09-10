package com.example.tp_spring_mvc_thymeleaf;

import com.example.tp_spring_mvc_thymeleaf.entities.Patient;
import com.example.tp_spring_mvc_thymeleaf.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class TpSpringMvcThymeleafApplication implements CommandLineRunner {
    @Autowired
    PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(TpSpringMvcThymeleafApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    patientRepository.save(new Patient(null,"Mohamed",new Date(),false,200));
    patientRepository.save(new Patient(null,"Hanane",new Date(),true,600));
    patientRepository.save(new Patient(null,"Imane",new Date(),false,287));
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
         PasswordEncoder passwordEncoder=passwordEncoder();
         return args -> {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user2").password(passwordEncoder.encode("12345")).roles("USER").build()
            );
            jdbcUserDetailsManager.createUser(
                User.withUsername("imane").password(passwordEncoder.encode("12345")).roles("USER").build()
            );
             jdbcUserDetailsManager.createUser(
                     User.withUsername("admin").password(passwordEncoder.encode("12345")).roles("USER","ADMIN").build()
             );

        };
    }
}
