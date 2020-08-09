package com.smallmail.smallmail.controller;

import com.smallmail.smallmail.Service.RoleService;
import com.smallmail.smallmail.Service.UserService;
import com.smallmail.smallmail.model.dto.UserDetailDto;
import com.smallmail.smallmail.model.entity.Role;
import com.smallmail.smallmail.model.entity.User;
import com.smallmail.smallmail.model.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user-details")
public class UserDetailController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleService roleService;

    public UserDetailController(UserService userService,
                                UserMapper userMapper,
                                RoleService roleService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @GetMapping("/{email}")
    public String getUserDetail(Model model, @PathVariable String email) {
        UserDetailDto dto = userMapper.convertToUserDetailDto(userService.getByEmail(email));
        model.addAttribute("user", dto);
        return "user/details";
    }

    @PostMapping
    public String addRoleApiCallForUser(@RequestParam String email) {
        User user = userService.getByEmail(email);
        for (Role role : user.getRoles()) {
            if (role.getRoleName().equals(Role.RoleName.APICALL)) {
                user.getRoles().remove(role);
                userService.update(user);
                return "redirect:/";
            }
        }
        user.getRoles().add(roleService.getByRoleName(String.valueOf(Role.RoleName.APICALL)));
        userService.update(user);
        return "redirect:/";
    }
}
