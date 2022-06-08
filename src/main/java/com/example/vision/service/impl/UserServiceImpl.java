package com.example.vision.service.impl;

import com.example.vision.controller.models.Response;
import com.example.vision.controller.models.Status;
import com.example.vision.entity.Project;
import com.example.vision.entity.Role;
import com.example.vision.entity.User;
import com.example.vision.payload.ReqSignUp;
import com.example.vision.repository.ProjectRepository;
import com.example.vision.repository.RoleRepository;
import com.example.vision.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl {
    public static final String ACCOUNT_SID = "ACf680a237a788f3a50845ba858f1c5b43";
    public static final String AUTH_TOKEN = "9ae18c44f224416031fa8c0ea7fe858c";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final JdbcTemplate jdbcTemplate;
//        private final ObjectMapper objectMapper;
//        private final JdbcTemplate jdbcTemplate;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ProjectRepository projectRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response signUp(ReqSignUp reqSignUp) {

        Response response = new Response();
        Object data = null;
        if (reqSignUp.getId() == null) {
            Optional<User> exist = userRepository.findByUsername(reqSignUp.getUsername());
//            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=S+$).{8}$";
//            Pattern pattern = Pattern.compile(regex);
//            pattern.matcher(reqSignUp.getPassword()).find();
//            System.out.println(pattern.matcher(regex));
            if (exist.isPresent()) {
                response.setMessage(new Status(105, "User exists"));
            } else {

                if (reqSignUp.getProjectId() != null) {
                    Optional<Project> byId = projectRepository.findById(reqSignUp.getProjectId());
                    if (byId.isPresent()) {
                        List<Role> roles = new ArrayList<>();
                        for (int i = 0; i < reqSignUp.getRolesId().size(); i++) {
                            roles.add(roleRepository.findById(reqSignUp.getRolesId().get(i)).get());
                        }
//                        if (matcher.equals(true)) {
//                            User user = new User();
//                            user.setPassword(reqSignUp.getPassword());
//                            User save = userRepository.save(user);
//                            response.setData(save);
//                            response.setMessage(new Status(0, "Saved"));
//                            System.out.println("Saved");
//                        } else {
//                            response.setMessage(new Status(1, "Password incorrect"));
//                            System.out.println("Password incorrect");
//                        }
                        User save = userRepository.save(new User(reqSignUp.getUsername(), passwordEncoder.encode(reqSignUp.getPassword()), roles, byId.get()));
//            save.setActive(true);

                        data = save.getId();
                        response.setMessage(new Status(0, "Success"));
                    } else {
                        response.setMessage(new Status(404, "Project not found"));
                    }
                } else {
                    List<Role> roles = new ArrayList<>();
                    if (reqSignUp.getRolesId() != null) {
                        for (int i = 0; i < reqSignUp.getRolesId().size(); i++) {
                            Optional<Role> byId = roleRepository.findById(reqSignUp.getRolesId().get(i));
                            if (byId.isPresent()) {
                                roles.add(roleRepository.findById(reqSignUp.getRolesId().get(i)).get());
                            }
                        }
                    } else {
                        roles.add(roleRepository.findById(3).get());
                    }
                    User save = userRepository.save(new User(reqSignUp.getUsername(), passwordEncoder.encode(reqSignUp.getPassword()), roles));
//            save.setActive(true);

                    data = save.getId();
                    response.setMessage(new Status(0, "Success"));
                }
            }
        } else {
            Optional<User> user = userRepository.findById(reqSignUp.getId());
            if (user.isPresent()) {
                Optional<User> exist = userRepository.findByUsername(reqSignUp.getUsername());
                if (exist.isPresent() && !(user.get().getUsername().equals(reqSignUp.getUsername()))) {
                    response.setMessage(new Status(105, "User exists"));
                } else {
                    if (reqSignUp.getProjectId() != null) {
                        Optional<Project> byId = projectRepository.findById(reqSignUp.getProjectId());
                        if (byId.isPresent()) {
                            List<Role> roles = new ArrayList<>();
                            for (int i = 0; i < reqSignUp.getRolesId().size(); i++) {
                                roles.add(roleRepository.findById(reqSignUp.getRolesId().get(i)).get());
                            }
                            User user1 = user.get();
                            user1.setUsername(reqSignUp.getUsername());
                            user1.setPassword(passwordEncoder.encode(reqSignUp.getPassword()));
                            user1.setRoles(roles);
                            data = user1.getId();
                            userRepository.save(user1);
                            response.setMessage(new Status(0, "Success"));
                        } else {
                            response.setMessage(new Status(404, "Project not found"));
                        }
                    } else {
                        List<Role> roles = new ArrayList<>();
                        for (int i = 0; i < reqSignUp.getRolesId().size(); i++) {
                            roles.add(roleRepository.findById(reqSignUp.getRolesId().get(i)).get());
                        }
                        User user1 = user.get();
                        user1.setUsername(reqSignUp.getUsername());
                        user1.setPassword(passwordEncoder.encode(reqSignUp.getPassword()));
                        user1.setRoles(roles);
                        data = user1.getId();
                        userRepository.save(user1);
                        response.setMessage(new Status(0, "Success"));
                        response.setData(data);
                    }
                }
            } else {
                response.setMessage(new Status(404, "User not found"));
            }
        }
        response.setData(data);
        return response;
    }
}