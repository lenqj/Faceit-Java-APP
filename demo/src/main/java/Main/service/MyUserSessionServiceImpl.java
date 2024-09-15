package Main.service;

import Main.DTO.MyUserDTO;
import Main.DTO.MyUserSessionDTO;
import Main.DTO.UserRoleDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.mapper.MyUserSessionMapper;
import Main.mapper.UserRoleMapper;
import Main.model.MyUser;
import Main.model.MyUserSession;
import Main.model.UserRole;
import Main.repository.MyUserRepository;
import Main.repository.MyUserSessionRepository;
import Main.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyUserSessionServiceImpl implements MyUserSessionService{
    private final MyUserSessionRepository myUserSessionRepository;
    private final MyUserRepository myUserRepository;
    private final MyUserSessionMapper myUserSessionMapper;

    @Override
    public MyUserSessionDTO createUserSession(MyUserSessionDTO myUserSessionDTO) {
        MyUserSession myUserSession = myUserSessionMapper.myUserSessionDTOToMyUserSession(myUserSessionDTO);
        Optional<MyUser> myUser = myUserRepository.findMyUserByUsername(myUserSessionDTO.getMyUser().getUsername());
        myUser.ifPresent(myUserSession::setMyUser);
        return myUserSessionMapper.myUserSessionToMyUserSessionDTO(myUserSessionRepository.save(myUserSession));
    }

    @Override
    public MyUserSessionDTO findMyUserSessionByMyUserUsernameAndLogoutAtIsNull(String username) throws ApiExceptionResponse {
        return myUserSessionMapper.myUserSessionToMyUserSessionDTO(myUserSessionRepository.findMyUserSessionByMyUserUsernameAndLogoutAtIsNull(username));

    }

    @Override
    public List<MyUserSessionDTO> findMyUserSessionsByLogoutAtIsEmpty(String username) throws ApiExceptionResponse {
        return myUserSessionMapper.myUserSessionsToMyUserSessionDTOS(myUserSessionRepository.findMyUserSessionsByMyUserUsernameAndLogoutAtIsNull(username));
    }

    @Override
    public Page<MyUserSessionDTO> findUserSessionByUserUsername(Pageable page, String username) throws ApiExceptionResponse {
        return myUserSessionMapper.myUserSessionsToMyUserSessionDTOS(myUserSessionRepository.findAllUserSessions(page, username));

    }

    @Override
    public MyUserSessionDTO updateUser(MyUserSessionDTO myUserSessionDTO) throws ApiExceptionResponse {
        MyUserSession myUserSessionForID = myUserSessionRepository.findMyUserSessionByMyUserUsernameAndLogoutAtIsNull(myUserSessionDTO.getMyUser().getUsername());
        MyUserSession myUserSessionForSave = myUserSessionMapper.myUserSessionDTOToMyUserSession(myUserSessionDTO);
        Optional<MyUser> myUser = myUserRepository.findMyUserByUsername(myUserSessionDTO.getMyUser().getUsername());
        myUser.ifPresent(myUserSessionForSave::setMyUser);
        myUserSessionForSave.setID(myUserSessionForID.getID());
        return myUserSessionMapper.myUserSessionToMyUserSessionDTO(myUserSessionRepository.save(myUserSessionForSave));
    }

    @Override
    public Page<MyUserSessionDTO> findAllUserSessions(Pageable page) throws ApiExceptionResponse {
        return myUserSessionMapper.myUserSessionsToMyUserSessionDTOS(myUserSessionRepository.findAll(page));
    }
}

