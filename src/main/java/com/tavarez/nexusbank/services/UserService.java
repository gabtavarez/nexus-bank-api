package com.tavarez.nexusbank.services;

import com.tavarez.nexusbank.dto.request.UserRequestDTO;
import com.tavarez.nexusbank.dto.response.UserResponseDTO;
import com.tavarez.nexusbank.entities.User;
import com.tavarez.nexusbank.exceptions.BusinessException;
import com.tavarez.nexusbank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Email already exists");
        }

        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .password(dto.password())
                .build();

        user = userRepository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
