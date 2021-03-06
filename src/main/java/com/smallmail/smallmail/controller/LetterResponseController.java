package com.smallmail.smallmail.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.smallmail.smallmail.service.LetterService;
import com.smallmail.smallmail.service.UserService;
import com.smallmail.smallmail.model.dto.LetterResponseDto;
import com.smallmail.smallmail.model.entity.User;
import com.smallmail.smallmail.model.mapper.LetterMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LetterResponseController {
    private final UserService userService;
    private final LetterService letterService;
    private final LetterMapper letterMapper;

    public LetterResponseController(UserService userService,
                                    LetterService letterService,
                                    LetterMapper letterMapper) {
        this.userService = userService;
        this.letterService = letterService;
        this.letterMapper = letterMapper;
    }

    @GetMapping
    public String getALLLettersByUserLogin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByEmail(userDetails.getUsername());
        model.addAttribute("letters", letterService.getAllByUser(user.getId())
                .stream()
                .map(letterMapper::convertToDto)
                .collect(Collectors.toList()));
        model.addAttribute("email", user.getEmail());
        return "mail/index";
    }

    @PostMapping
    public String searchByText(Model model, @RequestParam String search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByEmail(userDetails.getUsername());
        List<LetterResponseDto> letters = letterService.getByPhrase(search, user.getId())
                .stream()
                .map(letterMapper::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("letters", letters);
        model.addAttribute("email", user.getEmail());
        return "mail/index";
    }

    @PostMapping("/delete")
    public String deleteAllMarks(@RequestParam("in")Long[] in) {
        for (Long letter: in) {
            letterMapper.deleteLetterForUser(letter);
        }
        return "redirect:/";
    }
}
