/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cml.springbootskills.day3.mappers;

/**
 *
 * @author christopher
 */
import com.cml.springbootskills.day3.dto.CreateUserRequest;
import com.cml.springbootskills.day3.dto.UpdateUserRequest;
import com.cml.springbootskills.day3.dto.UserResponse;
import com.cml.springbootskills.day3.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for User entity and DTOs
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    
    /**
     * Convert CreateUserRequest to User entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(CreateUserRequest request);
    
    /**
     * Convert User entity to UserResponse DTO
     */
    UserResponse toResponse(User user);
    
    /**
     * Update existing User entity from UpdateUserRequest
     * Only updates non-null fields
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);
}