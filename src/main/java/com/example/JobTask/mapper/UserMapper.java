package com.example.JobTask.mapper;

import com.example.JobTask.dto.user.RegisterRequestDTO;
import com.example.JobTask.entity.User;
import com.example.JobTask.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "role", expression = "java(Role.USER)")
    User toEntity(RegisterRequestDTO registerRequestDTO);
}
