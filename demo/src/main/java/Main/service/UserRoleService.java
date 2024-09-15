package Main.service;

import Main.DTO.UserRoleDTO;
import Main.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserRoleService {
    UserRoleDTO createRole(UserRole role);
    UserRoleDTO findRoleById(Long id);
    UserRoleDTO findRoleByName(String name);
    UserRoleDTO updateRole(UserRole role);
    void deleteRole(UserRole role);
    List<UserRoleDTO> findAllRoles();
    void deleteRoleByName(String name);
}
