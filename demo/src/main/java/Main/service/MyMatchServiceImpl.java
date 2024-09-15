package Main.service;

import Main.DTO.MyMatchDTO;
import Main.DTO.MyUserDTO;
import Main.DTO.MyUserInMatchDTO;
import Main.constants.Map;
import Main.exceptions.ApiExceptionResponse;
import Main.mapper.MyMatchMapper;
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
public class MyMatchServiceImpl implements MyMatchService{
    private final MyMatchMapper myMatchMapper;
    private final MyMatchRepository myMatchRepository;
    private final ScoreRepository scoreRepository;
    private final MyMatchScoreRepository myMatchScoreRepository;
    private final MyUserRepository myUserRepository;
    private final MyUserInMatchRepository myUserInMatchRepository;

    @Override
    public MyMatchDTO createMatch(MyMatchDTO myMatchDTO) {
        MyMatch myMatch = myMatchMapper.myMatchDTOToMyMatch(myMatchDTO);
        List<MyUserInMatch> myUserInMatchesTeamA = new ArrayList<>();
        List<MyUserInMatch> myUserInMatchesTeamB = new ArrayList<>();
        for(MyUserInMatchDTO myUserInMatchDTO : myMatchDTO.getTeamA()){
            Optional<MyUser> myUser = myUserRepository.findMyUserByUsername(myUserInMatchDTO.getMyUser().getUsername());
            if(myUser.isPresent()) {
                MyUserInMatch myUserInMatch = new MyUserInMatch();
                myUserInMatch.setMyUser(myUser.get());
                myUserInMatch.setScore(scoreRepository.save(myUserInMatchDTO.getScore()));
                myUserInMatchesTeamA.add(myUserInMatchRepository.save(myUserInMatch));
            }
        }
        for(MyUserInMatchDTO myUserInMatchDTO : myMatchDTO.getTeamB()){
            Optional<MyUser> myUser = myUserRepository.findMyUserByUsername(myUserInMatchDTO.getMyUser().getUsername());
            if(myUser.isPresent()) {
                MyUserInMatch myUserInMatch = new MyUserInMatch();
                MyUser myUserIn = myUser.get();
                myUserInMatch.setMyUser(myUserIn);
                myUserInMatch.setScore(scoreRepository.save(myUserInMatchDTO.getScore()));
                myUserInMatchesTeamB.add(myUserInMatchRepository.save(myUserInMatch));
            }
        }

        Optional<MyUser> teamLeaderA = myUserRepository.findMyUserByUsername(myMatchDTO.getTeamLeaderA().getUsername());
        teamLeaderA.ifPresent(myMatch::setTeamLeaderA);
        Optional<MyUser> teamLeaderB = myUserRepository.findMyUserByUsername(myMatchDTO.getTeamLeaderB().getUsername());
        teamLeaderB.ifPresent(myMatch::setTeamLeaderB);

        myMatch.setTeamA(myUserInMatchesTeamA);
        myMatch.setTeamB(myUserInMatchesTeamB);

        MyMatchScore myMatchTeamAScore = myMatch.getTeamAScore();
        myMatch.setTeamAScore(myMatchScoreRepository.save(myMatchTeamAScore));
        MyMatchScore myMatchTeamBScore = myMatch.getTeamBScore();
        myMatch.setTeamBScore(myMatchScoreRepository.save(myMatchTeamBScore));
        return myMatchMapper.myMatchToMyMatchDTO(myMatchRepository.save(myMatch));
    }

    @Override
    public MyMatchDTO findMatchByName(String name) throws ApiExceptionResponse {
        Optional<MyMatch> myMatch = myMatchRepository.findByName(name);
        if (myMatch.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Match with id: " + name + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return myMatchMapper.myMatchToMyMatchDTO(myMatch.get());
    }

    @Override
    public Page<MyMatchDTO> findAllMatches(Pageable page, String map, String username) {
        return myMatchMapper.myMatchesToMyMatchDTOS(myMatchRepository.findAllMatches(page, map, username));
    }
    @Override
    public void deleteByName(String name) throws ApiExceptionResponse {
        Optional<MyMatch> myMatch = myMatchRepository.findByName(name);
        if (myMatch.isEmpty()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Match " + name + " might not exist");
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        myMatchRepository.delete(myMatch.get());
    }
}
