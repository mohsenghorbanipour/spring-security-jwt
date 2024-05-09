package com.devpro.jwt.repositories;

import com.devpro.jwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findAllByUsername(String email);

}
