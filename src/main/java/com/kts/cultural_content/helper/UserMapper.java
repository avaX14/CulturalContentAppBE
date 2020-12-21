package com.kts.cultural_content.helper;


import com.kts.cultural_content.dto.UserDTO;
import com.kts.cultural_content.model.User;

public class UserMapper implements MapperInterface<User, UserDTO> {

    @Override
    public User toEntity(UserDTO dto) {
        return new User(dto.getEmail(),dto.getPassword());
    }

    @Override
    public UserDTO toDto(User entity) {
        return new UserDTO(entity.getId(), entity.getEmail(),entity.getPassword());
    }
}
