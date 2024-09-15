package Main.controller;

import Main.exceptions.ApiExceptionResponse;
import Main.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@Tag(name = "Token", description = "Token API")
@Validated
public class ConfirmationTokenController {
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/confirm-account")
    public ResponseEntity confirmUserAccount(@RequestParam String confirmationToken) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(confirmationTokenService.confirmEmail(confirmationToken));
    }
    @GetMapping("/resend-token")
    public ResponseEntity resendToken(@RequestParam String confirmationToken) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(confirmationTokenService.resendToken(confirmationToken));
    }
}
