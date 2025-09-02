package com.example.appenglishlanguagelearning.repository;

import com.example.appenglishlanguagelearning.entity.UserWordProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserWordProgressRepository extends JpaRepository<UserWordProgress, UUID> {

}
