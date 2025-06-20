package com.example.appenglishlanguagelearning.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dictionary")
@SQLDelete(sql = "UPDATE dictionary SET deleted=true WHERE id=?")
@SQLRestriction("deleted = false")
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,length = 100)
    private String word;

    @Column(nullable = false)
    private String translateWord;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private Boolean deleted = false;
}
