package com.example.JobTask.mapper;

import com.example.JobTask.dto.user.RegisterRequestDTO;
import com.example.JobTask.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {com.example.JobTask.enums.Role.class})
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "role", expression = "java(Role.ROLE_USER)")
    User toEntity(RegisterRequestDTO registerRequestDTO);
}
