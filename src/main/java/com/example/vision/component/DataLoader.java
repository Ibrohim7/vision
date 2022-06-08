//package com.example.vision.component;
//
//import com.example.vision.entity.Role;
//import com.example.vision.entity.User;
//import com.example.vision.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    @Value("never")
//    private String initialMode;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    Role role;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (initialMode.equals("always")) {
//            userRepository.save(new User("ibrakhim",
//                            passwordEncoder.encode("1707")),
//                    role("USER_ADMIN"));
//        }
//    }
//}