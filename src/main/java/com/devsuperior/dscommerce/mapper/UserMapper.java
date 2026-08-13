package com.devsuperior.dscommerce.mapper;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO userDTO);

    default Role mapAuthorityToRole(String roleName) {
        if (roleName == null) return null;
        Role role = new Role();
        role.setAuthority(roleName);
        return role;
    }

    default String mapRoleToString(Role role) {
        if (role == null) return null;
        return role.getAuthority();
    }

}
