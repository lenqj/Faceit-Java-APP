package Main.controller;

import Main.DTO.LoginDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.service.MyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost")
@RestController
@AllArgsConstructor
@RequestMapping
@Tag(name = "Auth", description = "Authentication API")
@Validated
public class AuthController {
    private final MyUserService myUserService;
    @Operation(
            summary = "User login",
            description = "Authenticate user with provided credentials"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.login(loginDTO));
    }

    @Operation(
            summary = "User logout",
            description = "Deuthenticate user with provided credentials"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestParam String username) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.logout(username));
    }

}