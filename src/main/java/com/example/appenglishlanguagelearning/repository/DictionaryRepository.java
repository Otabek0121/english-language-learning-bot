package com.example.appenglishlanguagelearning.repository;

import com.example.appenglishlanguagelearning.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {

    List<Dictionary> findAllByUser_ChatId(long chatId);
}
