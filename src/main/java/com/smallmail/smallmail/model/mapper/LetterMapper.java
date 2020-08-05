package com.smallmail.smallmail.model.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.smallmail.smallmail.Service.LetterService;
import com.smallmail.smallmail.model.dto.LetterDetailDto;
import com.smallmail.smallmail.model.dto.LetterResponseDto;
import com.smallmail.smallmail.model.entity.Letter;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LetterMapper {

    private final LetterService letterService;

    public LetterMapper(LetterService letterService) {
        this.letterService = letterService;
    }

    public LetterDetailDto whoSender(Letter letter) {
        LetterDetailDto response;
        if (isLogger(letter.getOwner())) {
             response = convertToDetailDto(letter.getReceivers(),
                    letter.getTheme(),
                    letter.getBody(),
                     "to");
        } else {
            response = convertToDetailDto(letter.getOwner(),
                    letter.getTheme(),
                    letter.getBody(),
                    "from");
        }
        return response;
    }

    public LetterResponseDto convertToDto(Letter letter) {
        LetterResponseDto dto = new LetterResponseDto();
        dto.setId(letter.getId());
        dto.setTheme(letter.getTheme());
        dto.setInOrOut(ifUserLogged(letter.getOwner()));
        dto.setTime(convertTimeToString(letter.getTime()));
        return dto;
    }

    public LetterDetailDto convertToDetailDto(String users,
                                              String theme,
                                              String body,
                                              String income) {
        LetterDetailDto dto = new LetterDetailDto();
        dto.setTheme(theme);
        dto.setBody(body);
        dto.setForWhom(income);
        dto.setUser(users);
        return dto;
    }

    public void deleteLetterForUser(Long id) {
        Letter letter = letterService.getById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getUsername().equals(letter.getOwner())) {
            letter.setSender(null);
        } else {
            for (User user : letter.getRecipient()) {
                if (user.getEmail().equals(userDetails.getUsername())) {
                    letter.getRecipient().remove(user);
                    break;
                }
            }
        }
        if (letter.getSender() == null && letter.getRecipient().isEmpty()) {
            letterService.remove(id);
        } else {
            letterService.update(letter);
        }
    }

    private String convertTimeToString(LocalDateTime time) {
        String result;
        if (time.isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))) {
            result = String.valueOf(time.getHour()) + ":"
                    + String.valueOf(time.getMinute());
        } else if (time.getYear() == (LocalDate.now().getYear())) {
            result = String.valueOf(time.getDayOfMonth())
                    + " " + String.valueOf(time.getMonth());
        } else {
            result = String.valueOf(time.getDayOfMonth())
                    + String.valueOf(time.getMonth())
                    + " " + String.valueOf(time.getYear());
        }
        return result;
    }

    private Boolean isLogger(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername().equals(email);
    }

    private String ifUserLogged(String email) {
        if (isLogger(email)) {
            return "outcome";
        } else {
            return "income";
        }
    }
}
