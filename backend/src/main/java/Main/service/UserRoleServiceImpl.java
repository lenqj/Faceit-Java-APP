package Main.service;

import Main.DTO.UserRoleDTO;
import Main.mapper.UserRoleMapper;
import Main.model.UserRole;
import Main.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRoleServiceImpl implements UserRoleService{
    private final UserRoleMapper userRoleMapper;
    private final UserRoleRepository userRoleRepository;
    @Override
    public UserRoleDTO createRole(UserRole role) {
        return userRoleMapper.userRoleToUserRoleDTO(userRoleRepository.save(role));
    }
    @Override
    public UserRoleDTO findRoleById(Long id) {
        return userRoleMapper.userRoleToUserRoleDTO(userRoleRepository.findById(id).orElseThrow());
    }
    @Override
    public UserRoleDTO findRoleByName(String name) {
        return userRoleMapper.userRoleToUserRoleDTO(userRoleRepository.findByName(name));
    }
    @Override
    public UserRoleDTO updateRole(UserRole role) {
        return userRoleMapper.userRoleToUserRoleDTO(userRoleRepository.save(role));
    }
    @Override
    public void deleteRole(UserRole role) {
        userRoleRepository.delete(role);
    }
    @Override
    public List<UserRoleDTO> findAllRoles() {
        return userRoleMapper.userRolesToUserRoleDTOS((List<UserRole>) userRoleRepository.findAll());
    }

    @Override
    public void deleteRoleByName(String name) {
        userRoleRepository.deleteRoleByName(name);
    }
}
