package Main.controller;

import Main.DTO.CompareTwoPlayersDTO;
import Main.DTO.MyUserCreationDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.model.EmailPayload;
import Main.service.EmailService;
import Main.service.MyMatchService;
import Main.service.MyUserService;
import Main.service.MyUserSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
@Validated
public class MyUserController {
    private final MyUserService myUserService;
    private final MyMatchService myMatchService;
    private final EmailService emailService;
    private final MyUserSessionService myUserSessionService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String,String> handleValidationException(ConstraintViolationException ex){
        Map<String,String> errorMap = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            errorMap.put(error.getPropertyPath().toString(), error.getMessage());
        });
        return errorMap;
    }

    @Operation(
            summary = "Find all users",
            description = "Retrieve all users with optional filtering by role"
    )
    @GetMapping
    public ResponseEntity findAllUsers(@PageableDefault Pageable pageable, @RequestParam(required = false) String role) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.findAllUsers(pageable, role));
    }

    @Operation(
            summary = "Find user by username",
            description = "Find user based on the username"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    public ResponseEntity findUserByUsername(@PathVariable String username) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.findUserByUserName(username));
    }
    @Operation(
            summary = "Create user",
            description = "Create a new user with the provided details"
    )
    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody MyUserCreationDTO myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.createUser(myUser));
    }
    @Operation(
            summary = "Update user",
            description = "Update an existing user with the provided details"
    )
    @PutMapping
    public ResponseEntity updateUser(@Valid @RequestBody MyUserCreationDTO myUser) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.updateUser(myUser));
    }
    @Operation(
            summary = "Delete user",
            description = "Delete a user based on the username"
    )
    @DeleteMapping("/{username}")
    public ResponseEntity deleteUserByUserName(@PathVariable String username) throws ApiExceptionResponse {
        myUserService.deleteUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(username);
    }
    @Operation(
            summary = "Find user matches",
            description = "Retrieve all matches associated with a user, optionally filtered by map"
    )
    @GetMapping("/{username}/matches")
    public ResponseEntity findUserMatches(@PageableDefault Pageable pageable, @RequestParam(required = false) String map, @PathVariable String username) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myMatchService.findAllMatches(pageable, map, username));
    }
    @Operation(
            summary = "Compare two users",
            description = "Compares two users based on their performance in matches."
    )
    @PostMapping("/compare")
    public ResponseEntity compareTwoUsers(@RequestBody CompareTwoPlayersDTO compareTwoPlayersDTO) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.compareTwoPlayers(compareTwoPlayersDTO));
    }

    @PostMapping("/sendEmail")
    public ResponseEntity sendEmail(@RequestBody EmailPayload emailPayload) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.sendSupportEmail(emailPayload));
    }

    @GetMapping("/export/{username}")
    public ResponseEntity exportUserDetails(@PathVariable String username, @RequestParam String fileType) {
        return ResponseEntity.ok(myUserService.exportUserDetails(username, fileType));
    }

    @GetMapping("/export/matches/{username}")
    public ResponseEntity exportUserMatchesDetails(@PathVariable String username, @RequestParam String fileType) {
        return ResponseEntity.ok(myUserService.exportUserMatchesDetails(username, fileType));
    }

    @GetMapping("/sessions")
    public ResponseEntity findAllUserSessions(@PageableDefault Pageable pageable) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserSessionService.findAllUserSessions(pageable));
    }

}
