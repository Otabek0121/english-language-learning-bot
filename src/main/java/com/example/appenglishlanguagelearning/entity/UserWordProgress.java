package com.example.appenglishlanguagelearning.entity;

import com.example.appenglishlanguagelearning.enums.DictionaryStatusEnum;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE user_word_progress SET deleted=true WHERE id=?")
@SQLRestriction("deleted = false")
@Table(
        name = "user_word_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "wordId"})
)
public class UserWordProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private UUID wordId;

    private int knowCount;
    private LocalDateTime lastSeen;

    private LocalDateTime showDate;

    @Enumerated(EnumType.STRING)
    private DictionaryStatusEnum status;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private Boolean deleted = false;

}
