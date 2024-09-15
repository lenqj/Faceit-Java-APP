package Main.service;

import Main.DTO.MyUserSessionDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.model.MyUserSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MyUserSessionService {
    MyUserSessionDTO createUserSession(MyUserSessionDTO myUserSessionDTO);
    MyUserSessionDTO findMyUserSessionByMyUserUsernameAndLogoutAtIsNull(String username) throws ApiExceptionResponse;
    List<MyUserSessionDTO> findMyUserSessionsByLogoutAtIsEmpty(String username) throws ApiExceptionResponse;
    Page<MyUserSessionDTO> findUserSessionByUserUsername(Pageable page, String username) throws ApiExceptionResponse;
    MyUserSessionDTO updateUser(MyUserSessionDTO myUserSessionDTO) throws ApiExceptionResponse;
    Page<MyUserSessionDTO> findAllUserSessions(Pageable page) throws ApiExceptionResponse;
}
