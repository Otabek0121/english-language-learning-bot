package com.example.appenglishlanguagelearning.entity;

import com.example.appenglishlanguagelearning.enums.UserState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user_session")
public class UserSession {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Enumerated(EnumType.STRING)
    @Column(name="user_state",nullable = false)
    private UserState userState;

//    @Column(name="language_code")
//    private String languageCode;
}
