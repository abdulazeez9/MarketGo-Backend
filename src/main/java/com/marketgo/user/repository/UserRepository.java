package com.marketgo.user.repository;

import com.marketgo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndDeleteAtIsNull(String email);

    boolean existsByEmail(String email);

    Optional<User> findByIdAndDeletedAtIsNull(UUID id);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);
}
