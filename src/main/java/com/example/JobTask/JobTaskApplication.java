package com.example.JobTask;

import com.example.JobTask.Repository.UserRepository;
import com.example.JobTask.entity.User;
import com.example.JobTask.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JobTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobTaskApplication.class, args);
	}

    @Bean
    public CommandLineRunner init(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  @Value("${admin.email}") String email,
                                  @Value("${admin.password}") String password){

        return args -> {
            if(!userRepository.existsByEmail(email)){
                User admin = User.builder()
                        .name("mohamed")
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .role(Role.ROLE_ADMIN)
                        .build();
                userRepository.save(admin);
            }
        };
    }

}
