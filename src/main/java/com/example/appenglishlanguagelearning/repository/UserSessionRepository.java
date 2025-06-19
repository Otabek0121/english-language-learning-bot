package com.example.appenglishlanguagelearning.repository;

import com.example.appenglishlanguagelearning.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByChatId(Long chatId);

}
