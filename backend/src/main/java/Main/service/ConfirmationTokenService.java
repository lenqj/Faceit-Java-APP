package Main.service;

import Main.DTO.MyUserDTO;
import Main.exceptions.ApiExceptionResponse;
import org.springframework.stereotype.Component;

@Component
public interface ConfirmationTokenService {
        MyUserDTO confirmEmail(String confirmationToken) throws ApiExceptionResponse;
    MyUserDTO resendToken(String confirmationToken) throws ApiExceptionResponse;

}
