package Main.mapper;

import Main.DTO.UserRoleDTO;
import Main.model.UserRole;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Lazy
public class UserRoleMapper {

    public UserRoleDTO userRoleToUserRoleDTO(UserRole role){
        return UserRoleDTO.builder()
                .name(role.getName())
                .build();
    }
    public List<UserRoleDTO> userRolesToUserRoleDTOS(List<UserRole> roles){
        return roles.stream()
                .map(this::userRoleToUserRoleDTO)
                .toList();
    }

    public UserRole userRoleDTOToUserRole(UserRoleDTO userRoleDTO){
        return UserRole.builder()
                .name(userRoleDTO.getName())
                .build();
    }
}
