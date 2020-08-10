package com.smallmail.smallmail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import com.smallmail.smallmail.service.LetterService;
import com.smallmail.smallmail.service.UserService;
import com.smallmail.smallmail.model.dto.LetterRedirectDto;
import com.smallmail.smallmail.model.entity.Letter;
import com.smallmail.smallmail.model.entity.User;
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
@RequestMapping("/mail")
public class LetterController {

    private final UserService userService;
    private final LetterService letterService;

    public LetterController(UserService userService, LetterService letterService) {
        this.userService = userService;
        this.letterService = letterService;
    }

    @PostMapping
    public String create(@RequestParam String theme, String body, String recipient) {
        List<User> users = new ArrayList<>();
        for (String email : recipient.split(";")) {
            User user = userService.getByEmail(email.trim());
            if (user != null) {
                users.add(user);
            } else {
                throw new NoSuchElementException("no such email: " + email);
            }
        }
        Letter letter = new Letter();
        letter.setRecipient(users);
        letter.setTheme(theme);
        letter.setBody(body);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User sender = userService.getByEmail(userDetails.getUsername());
        letter.setSender(sender);
        letter.setOwner(sender.getEmail());
        letter.setReceivers(letter.getRecipient()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.joining("; ")));
        letterService.create(letter);
        return "redirect:/";
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("letter", new LetterRedirectDto());
        return "mail/writeMail";
    }
}
