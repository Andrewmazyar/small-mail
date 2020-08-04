package com.smallmail.smallmail.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.smallmail.smallmail.Service.UserService;
import com.smallmail.smallmail.model.dto.UserResponseDto;
import com.smallmail.smallmail.model.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserResponseDto> users = userService.getAll()
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "user/users";
    }
}
