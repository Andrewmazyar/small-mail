package com.smallmail.smallmail.controller;

import com.smallmail.smallmail.service.LetterService;
import com.smallmail.smallmail.model.dto.LetterDetailDto;
import com.smallmail.smallmail.model.dto.LetterRedirectDto;
import com.smallmail.smallmail.model.mapper.LetterMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/details")
public class LetterDetailController {
    private final LetterService letterService;
    private final LetterMapper letterMapper;

    public LetterDetailController(LetterService letterService,
                                  LetterMapper letterMapper) {
        this.letterService = letterService;
        this.letterMapper = letterMapper;
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        LetterDetailDto response = letterMapper.ifSender(letterService.getById(id));
        model.addAttribute("details", response);
        return "mail/details";
    }

    @PostMapping("/{id}")
    public String redirectToWritePage(Model model, @RequestParam String sender, String theme, @PathVariable Long id) {
        LetterRedirectDto dto = new LetterRedirectDto();
        dto.setSender(sender);
        dto.setTheme(theme);
        model.addAttribute("letter", dto);
        return "redirect:/mail";
    }
}
