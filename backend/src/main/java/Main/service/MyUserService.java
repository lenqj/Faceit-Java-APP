package Main.service;

import Main.DTO.CompareTwoPlayersDTO;
import Main.DTO.LoginDTO;
import Main.DTO.MyUserDTO;
import Main.DTO.MyUserCreationDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.model.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface MyUserService {
    MyUserDTO createUser(MyUserCreationDTO user);
    MyUserDTO findUserByUserName(String username) throws ApiExceptionResponse;
    MyUserDTO updateUser(MyUserCreationDTO myUser) throws ApiExceptionResponse;
    void deleteUser(MyUser myUser) throws ApiExceptionResponse;
    void deleteUserByUsername(String userName) throws ApiExceptionResponse;
    Page<MyUserDTO> findAllUsers(Pageable page, String role) throws ApiExceptionResponse;
    MyUserDTO login(LoginDTO loginDTO) throws ApiExceptionResponse;
    MyUserDTO logout(String username) throws ApiExceptionResponse;
    MyUserDTO compareTwoPlayers(CompareTwoPlayersDTO compareTwoPlayersDTO);
    String exportUserDetails(String username, String fileType);
    String exportUserMatchesDetails(String username, String fileType);

}
