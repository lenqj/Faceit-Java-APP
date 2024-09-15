package Main.service;

import Main.DTO.*;
import Main.exceptions.ApiExceptionResponse;
import Main.mapper.MyUserTeamMapper;
import Main.model.*;
import Main.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyUserTeamServiceImpl implements MyUserTeamService{
    private final MyUserTeamMapper myUserTeamMapper;
    private final MyUserTeamRepository myUserTeamRepository;
    private final MyUserRepository myUserRepository;
    private final MyMatchRepository myMatchRepository;

    @Override
    public MyUserTeamDTO createMyUserTeam(MyUserTeamCreationDTO myUserTeamCreationDTO) {
        MyTeam myUserTeam = new MyTeam();
        myUserTeam.setName(myUserTeamCreationDTO.getName());
        List<MyUser> myUsersInTeam = new ArrayList<>();
        myUserTeam.setNumberOfMatches(0);
        for(String myUserInTeam : myUserTeamCreationDTO.getTeamMembers()){
            Optional<MyUser> myUserOptional = myUserRepository.findMyUserByUsername(myUserInTeam);
            if(myUserOptional.isPresent()) {
                MyUser myUser = myUserOptional.get();
                myUsersInTeam.add(myUser);
            }
        }
        Optional<MyUser> teamLeader = myUserRepository.findMyUserByUsername(myUserTeamCreationDTO.getTeamLeader());
        teamLeader.ifPresent(myUserTeam::setTeamLeader);
        myUserTeam.setTeamMembers(myUsersInTeam);
        List<MyMatch> myMatches = new ArrayList<>();
        if(myUserTeamCreationDTO.getMatches() != null) {
            for (String match : myUserTeamCreationDTO.getMatches()) {
                Optional<MyMatch> myMatchOptional = myMatchRepository.findByName(match);
                if (myMatchOptional.isPresent()) {
                    MyMatch myMatch = myMatchOptional.get();
                    myMatches.add(myMatch);
                    myUserTeam.addNumberOfMatches(1);
                }
            }
        }
        myUserTeam.setMatches(myMatches);
        return myUserTeamMapper.myUserTeamToMyUserTeamDTO(myUserTeamRepository.save(myUserTeam));
    }

    @Override
    public MyUserTeamDTO findMyUserTeamByName(String name) throws ApiExceptionResponse {
        Optional<MyTeam> myUserTeam = myUserTeamRepository.findMyUserTeamByName(name);
        if (myUserTeam.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Team with name: " + name + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return myUserTeamMapper.myUserTeamToMyUserTeamDTO(myUserTeam.get());
    }


    @Override
    public Page<MyUserTeamDTO> findAllTeams(Pageable page, String leaderUsername, String playerUsername) {
        return myUserTeamMapper.myUserTeamsToMyUserTeamDTOS(myUserTeamRepository.findAllTeams(page, leaderUsername, playerUsername));
    }

    @Override
    public MyUserTeamDTO updateMyUserTeam(MyTeam myTeam) throws ApiExceptionResponse {
        Optional<MyTeam> myUserTeamForId = myUserTeamRepository.findMyUserTeamByName(myTeam.getName());
        if (myUserTeamForId.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Team with name: " + myTeam.getName() + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        myUserTeamForId.get().setName(myTeam.getName());
        myUserTeamForId.get().setTeamLeader(myTeam.getTeamLeader());
        myUserTeamForId.get().getMatches().addAll(myTeam.getMatches());
        myUserTeamForId.get().addNumberOfMatches(myTeam.getNumberOfMatches());
        return myUserTeamMapper.myUserTeamToMyUserTeamDTO(myUserTeamRepository.save(myTeam));
    }

    @Override
    public void deleteMyUserTeamByName(String name) throws ApiExceptionResponse {
        Optional<MyTeam> myUserTeam = myUserTeamRepository.findMyUserTeamByName(name);
        if (myUserTeam.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Team " + name + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        myUserTeamRepository.delete(myUserTeam.get());
    }
}
