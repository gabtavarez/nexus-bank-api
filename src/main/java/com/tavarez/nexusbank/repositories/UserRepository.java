package com.tavarez.nexusbank.repositories;

import com.tavarez.nexusbank.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
