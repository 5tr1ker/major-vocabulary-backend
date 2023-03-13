package com.konkuk.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konkuk.vocabulary.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findById(String id);

}
