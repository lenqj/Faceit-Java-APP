package Main.service;

import Main.DTO.*;
import Main.exceptions.ApiExceptionResponse;
import Main.model.MyTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface MyUserTeamService {
    MyUserTeamDTO createMyUserTeam(MyUserTeamCreationDTO myUserTeamCreationDTO);
    MyUserTeamDTO findMyUserTeamByName(String name) throws ApiExceptionResponse;
    void deleteMyUserTeamByName(String name) throws ApiExceptionResponse;
    Page<MyUserTeamDTO> findAllTeams(Pageable page, String leaderUsername, String playerUsername) throws ApiExceptionResponse;
    MyUserTeamDTO updateMyUserTeam(MyTeam myTeam) throws ApiExceptionResponse;
}

