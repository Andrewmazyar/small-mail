package com.smallmail.smallmail.controller;

import javax.annotation.PostConstruct;
import java.util.Set;
import com.smallmail.smallmail.Service.RoleService;
import com.smallmail.smallmail.Service.UserService;
import com.smallmail.smallmail.model.entity.Role;
import com.smallmail.smallmail.model.entity.User;
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
    public String injectUsers() {
        setRoles();

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Set.of(roleService.getByRoleName("USER")));
        userService.create(user);

        User president = new User();
        president.setEmail("president@gmail.com");
        president.setPassword(passwordEncoder.encode("president"));
        president.setRoles(Set.of(roleService.getByRoleName("USER")));
        userService.create(president);

        User minister = new User();
        minister.setEmail("minister@gmail.com");
        minister.setPassword(passwordEncoder.encode("minister"));
        minister.setRoles(Set.of(roleService.getByRoleName("USER")));
        userService.create(minister);

        User deputy = new User();
        deputy.setEmail("deputy@gmail.com");
        deputy.setPassword(passwordEncoder.encode("deputy"));
        deputy.setRoles(Set.of(roleService.getByRoleName("USER")));
        userService.create(deputy);

        User citizen = new User();
        citizen.setEmail("citizen@gmail.com");
        citizen.setPassword(passwordEncoder.encode("citizen"));
        citizen.setRoles(Set.of(roleService.getByRoleName("USER")));
        userService.create(citizen);
        return "user/inject";
    }

    private void setRoles() {
        Role user = new Role();
        user.setRoleName(Role.RoleName.USER);
        roleService.create(user);

        Role admin = new Role();
        admin.setRoleName(Role.RoleName.APICALL);
        roleService.create(admin);
    }
}
