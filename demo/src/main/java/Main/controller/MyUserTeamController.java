package Main.controller;

import Main.DTO.CompareTwoPlayersDTO;
import Main.DTO.MyUserCreationDTO;
import Main.DTO.MyUserTeamCreationDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.service.MyMatchService;
import Main.service.MyUserService;
import Main.service.MyUserTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
@Tag(name = "MyUserTeam", description = "MyUserTeam API")
@Validated
public class MyUserTeamController {
    private final MyUserTeamService myUserTeamService;
    @Operation(
            summary = "Find all teams",
            description = "Retrieve all teams with optional filtering by leader username"
    )
    @GetMapping
    public ResponseEntity findAllTeams(@PageableDefault Pageable pageable, @RequestParam(required = false) String leaderUsername, @RequestParam(required = false) String playerUsername) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserTeamService.findAllTeams(pageable, leaderUsername, playerUsername));
    }

    @Operation(
            summary = "Find team by name",
            description = "Find team based on the name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team found"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity findMyUserTeamByName(@PathVariable String name) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserTeamService.findMyUserTeamByName(name));
    }
    @Operation(
            summary = "Create team",
            description = "Create a new team with the provided details"
    )
    @PostMapping
    public ResponseEntity createMyUserTeam(@Valid  @RequestBody MyUserTeamCreationDTO myUserTeamCreationDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(myUserTeamService.createMyUserTeam(myUserTeamCreationDTO));
    }
    @Operation(
            summary = "Delete team",
            description = "Delete a team based on the name"
    )
    @DeleteMapping("/{name}")
    public ResponseEntity deleteMyUserTeamByName(@PathVariable String name) throws ApiExceptionResponse {
        myUserTeamService.deleteMyUserTeamByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(name);
    }

}
