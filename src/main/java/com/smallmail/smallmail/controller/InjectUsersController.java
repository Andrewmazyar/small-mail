package com.smallmail.smallmail.controller;

import java.io.IOException;
import java.util.Set;
import com.smallmail.smallmail.Service.RoleService;
import com.smallmail.smallmail.Service.UserService;
import com.smallmail.smallmail.model.entity.Role;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inject-users")
public class InjectUsersController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    public InjectUsersController(PasswordEncoder passwordEncoder,
                                 UserService userService,
                                 RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String injectUsers() throws IOException {
        setRoles();

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Set.of(roleService.getByRoleName("USER")));
        ClassPathResource imageFile = new ClassPathResource("/photo/quen.jpg");
        user.setPicture(new byte[(int) imageFile.contentLength()]);
        userService.create(user);

        User president = new User();
        president.setEmail("president@gmail.com");
        president.setPassword(passwordEncoder.encode("president"));
        president.setRoles(Set.of(roleService.getByRoleName("USER")));
        ClassPathResource imageFile1 = new ClassPathResource("/photo/king.jpg");
        president.setPicture(new byte[(int) imageFile1.contentLength()]);
        userService.create(president);

        User minister = new User();
        minister.setEmail("minister@gmail.com");
        minister.setPassword(passwordEncoder.encode("minister"));
        minister.setRoles(Set.of(roleService.getByRoleName("USER")));
        ClassPathResource imageFile2 = new ClassPathResource("/photo/minister.jpg");
        minister.setPicture(new byte[(int) imageFile2.contentLength()]);
        userService.create(minister);

        User deputy = new User();
        deputy.setEmail("deputy@gmail.com");
        deputy.setPassword(passwordEncoder.encode("deputy"));
        deputy.setRoles(Set.of(roleService.getByRoleName("USER")));
        ClassPathResource imageFile3 = new ClassPathResource("/photo/deputy.png");
        deputy.setPicture(new byte[(int) imageFile3.contentLength()]);
        userService.create(deputy);

        User citizen = new User();
        citizen.setEmail("citizen@gmail.com");
        citizen.setPassword(passwordEncoder.encode("citizen"));
        citizen.setRoles(Set.of(roleService.getByRoleName("USER")));
        ClassPathResource imageFile4 = new ClassPathResource("/photo/citizen.png");
        citizen.setPicture(new byte[(int) imageFile4.contentLength()]);
        userService.create(citizen);

        return "user/inject";
    }

    private void setRoles() {
        Role user = new Role();
        user.setRoleName(Role.RoleName.valueOf("USER"));
        roleService.create(user);

        Role admin = new Role();
        admin.setRoleName(Role.RoleName.valueOf("ADMIN"));
        roleService.create(user);
    }
}
