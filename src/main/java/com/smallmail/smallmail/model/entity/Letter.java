package com.smallmail.smallmail.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Letter {
    private Long id;
    private LocalDateTime time;
    private String theme;
    private String body;
    private User sender;
    private List<User> recipient;
    private String owner;
    private String receivers;

}
