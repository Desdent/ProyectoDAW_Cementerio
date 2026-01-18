package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import com.imo.cementery.model.dto.user.UserResponseDTO;
import com.imo.cementery.model.entity.User;
import org.springframework.stereotype.Component;

@Component // Con esto spring lo gestiona y permite su inyección en otras clases con @Autowired
public class UserMapper {

    // De DTO de entrada a entity
    public User toEntity(UserCreateDTO dto)
    {
        return User.builder() // se puede usar builder gracias a que la clase User tiene el @Builder
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build(); // Se puede usar gracias a que la clase User tiene el @Builder
    }


    // De entity a DTOa de salida
    public UserResponseDTO toResponseDTO(User entity)
    {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }

}
