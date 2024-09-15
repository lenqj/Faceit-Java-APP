package Main.service;

import Main.DTO.*;
import Main.constants.FileType;
import Main.exceptions.ApiExceptionResponse;
import Main.exporter.FileExporter;
import Main.exporter.TXTFileExporter;
import Main.exporter.XMLFileExporter;
import Main.mapper.MyUserMapper;
import Main.model.*;
import Main.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyUserServiceImpl implements MyUserService{
    private final MyUserMapper myUserMapper;
    private final MyUserRepository myUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final MyUserSessionService myUserSessionService;
    private final MyMatchRepository myMatchRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public MyUserDTO createUser(MyUserCreationDTO user) {
        @Valid MyUser userToSave = myUserMapper.myUserForCreationToMyUser(user);
        userToSave.setRole(userRoleRepository.findByName(user.getRole() != null ? user.getRole().getName() : "PLAYER"));
        return myUserMapper.myUserToMyUserDTO(myUserRepository.save(userToSave));
    }

    @Override
    public MyUserDTO findUserByUserName(String userName) throws ApiExceptionResponse {
        Optional<MyUser> user = myUserRepository.findMyUserByUsername(userName);
        if (user.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("User with userName: " + userName + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return myUserMapper.myUserToMyUserDTO(user.get());
    }

    @Override
    public MyUserDTO updateUser(MyUserCreationDTO myUser) throws ApiExceptionResponse, ConstraintViolationException {
        Optional<MyUser> user = myUserRepository.findMyUserByUsername(myUser.getUsername());
        if (user.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("User " + myUser.getPassword() + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        MyUser userForID = user.get();
        String newPassword = myUser.getPassword();
        String currentPassword = userForID.getPassword();
        String updatedPassword = (newPassword != null && !newPassword.isEmpty()) ? newPassword : currentPassword;
        MyUser updatedUser = myUserMapper.myUserForCreationToMyUser(myUser);
        updatedUser.setID(userForID.getID());
        updatedUser.setRole(userRoleRepository.findByName(myUser.getRole().getName()));
        updatedUser.setScore(userForID.getScore());
        updatedUser.setPassword(updatedPassword);

        return myUserMapper.myUserToMyUserDTO(myUserRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(MyUser myUser) throws ApiExceptionResponse {
        Optional<MyUser> user = myUserRepository.findById(myUser.getID());
        if (user.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("User " + myUser.getUsername() + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        deleteUserByUsername(myUser.getUsername());
    }

    @Override
    public void deleteUserByUsername(String userName) throws ApiExceptionResponse {
        Optional<MyUser> user = myUserRepository.findMyUserByUsername(userName);
        if (user.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("User " + userName + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        MyUser myUser = user.get();
        UserRole userRole = userRoleRepository.findByName("GUEST");
        if(userRole == null){
            UserRole userRoleToSave = UserRole.builder().name("GUEST").build();
            userRole = userRoleRepository.save(userRoleToSave);
        }
        myUser.setRole(userRole);
    }

    @Override
    public Page<MyUserDTO> findAllUsers(Pageable pageable, String role) {
        return myUserMapper.myUsersToMyUserDTOS(myUserRepository.findAllUsers(pageable, role));
    }

    @Override
    public MyUserDTO login(LoginDTO loginDTO) throws ApiExceptionResponse {
        Optional<MyUser> user = myUserRepository.findMyUserByUsername(loginDTO.getUsername());

        if (user.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            MyUser myUser = user.get();
            if (myUser.isEnabled()) {
                List<MyUserSessionDTO> myUserSessionList = myUserSessionService.findMyUserSessionsByLogoutAtIsEmpty(myUser.getUsername());
                for(MyUserSessionDTO myUserSessionDTO : myUserSessionList){
                    myUserSessionDTO.setLogoutAt(LocalDateTime.now());
                    myUserSessionService.updateUser(myUserSessionDTO);
                }
                MyUserSessionDTO myUserSessionDTO = MyUserSessionDTO.builder()
                        .myUser(myUserMapper.myUserToMyUserDTO(myUser))
                        .loginAt(LocalDateTime.now())
                        .build();
                myUserSessionService.createUserSession(myUserSessionDTO);
                return myUserMapper.myUserToMyUserDTO(myUser);
            } else {
                throw ApiExceptionResponse.builder()
                        .message("Your account is not activated. Please activate your account.")
                        .status(HttpStatus.UNAUTHORIZED)
                        .build();
            }
        } else {
            throw ApiExceptionResponse.builder()
                    .message("Invalid credentials. Please check your username and password.")
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    @Override
    public MyUserDTO logout(String username) throws ApiExceptionResponse {
        Optional<MyUser> user = myUserRepository.findMyUserByUsername(username);

        if (user.isPresent()) {
            MyUser myUser = user.get();
            MyUserSessionDTO myUserSession = myUserSessionService.findMyUserSessionByMyUserUsernameAndLogoutAtIsNull(myUser.getUsername());
            System.out.println(myUserSession);
            myUserSession.setLogoutAt(LocalDateTime.now());
            System.out.println(myUserSession);
            myUserSessionService.updateUser(myUserSession);
            return myUserMapper.myUserToMyUserDTO(myUser);
        } else {
            throw ApiExceptionResponse.builder()
                    .message("Invalid credentials. Please check your username.")
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    @Override
    public MyUserDTO compareTwoPlayers(CompareTwoPlayersDTO compareTwoPlayersDTO) {
        MyUserDTO result = new MyUserDTO();
        Optional<MyUser> optionalMyUser1 = myUserRepository.findMyUserByUsername(compareTwoPlayersDTO.getFirstPlayerName());
        Optional<MyUser> optionalMyUser2 = myUserRepository.findMyUserByUsername(compareTwoPlayersDTO.getSecondPlayerName());
        if(optionalMyUser1.isPresent() && optionalMyUser2.isPresent()) {
            MyUser myUser1 = optionalMyUser1.get();
            MyUser myUser2 = optionalMyUser2.get();
            result = myUserMapper.myUserToMyUserDTO(myUser1.compareTo(myUser2));
        }
        return result;
    }

    @Override
    public String exportUserDetails(String username, String fileType) {
            Optional<MyUser> myUserOptional = myUserRepository.findMyUserByUsername(username);
            if(myUserOptional.isPresent()) {
                MyUser myUser = myUserOptional.get();
                FileExporter fileExporter;
                if (fileType.equals(FileType.XML)) {
                    fileExporter = new XMLFileExporter();
                    return fileExporter.exportData(myUser);
                } else if (fileType.equals(FileType.TXT)) {
                    fileExporter = new TXTFileExporter();
                    return fileExporter.exportData(myUser);
                }
            }
            return null;
    }
    @Override
    public String exportUserMatchesDetails(String username, String fileType) {
        List<MyMatch> matches = myMatchRepository.findAllListByTeamAOrTeamB_MyUserUsernameAndMap(username);
        if(!matches.isEmpty()) {
            FileExporter fileExporter;
            if (fileType.equals(FileType.XML)) {
                fileExporter = new XMLFileExporter();
                return fileExporter.exportData(matches);
            } else if (fileType.equals(FileType.TXT)) {
                fileExporter = new TXTFileExporter();
                return fileExporter.exportData(matches);
            }
        }
        return null;
    }
}
