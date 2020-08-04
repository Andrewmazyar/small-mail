package com.smallmail.smallmail.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "letters")
@Data
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    @Column(name = "theme", length = 150)
    private String theme;
    @Column(name = "body", length = 1024)
    private String body;
    @ManyToOne
    private User sender;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> recipient;
    private String owner;
    private String receivers;
}
