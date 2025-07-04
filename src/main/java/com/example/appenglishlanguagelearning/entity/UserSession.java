package com.example.appenglishlanguagelearning.entity;

import com.example.appenglishlanguagelearning.enums.UserState;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user_session")
@SQLDelete(sql = "UPDATE user_session SET deleted=true WHERE id=?")
@SQLRestriction("deleted = false")
public class UserSession {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Enumerated(EnumType.STRING)
    @Column(name="user_state",nullable = false)
    private UserState userState;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private Boolean deleted = false;

//  @Column(name="language_code")
//  private String languageCode;


    public UserSession(Long chatId, UserState userState) {
        this.chatId = chatId;
        this.userState = userState;
    }

}
