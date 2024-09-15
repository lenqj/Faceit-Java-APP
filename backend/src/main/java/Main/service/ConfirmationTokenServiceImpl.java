package Main.service;

import Main.DTO.MyUserDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.mapper.MyUserMapper;
import Main.model.ConfirmationToken;
import Main.model.EmailPayload;
import Main.model.MyUser;
import Main.repository.ConfirmationTokenRepository;
import Main.repository.MyUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MyUserRepository myUserRepository;
    private final MyUserMapper myUserMapper;
    private final EmailService emailService;

    @Override
    public MyUserDTO confirmEmail(String confirmationToken) throws ApiExceptionResponse {
        ConfirmationToken token = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(confirmationToken);
        if (token != null) {
            if (token.isValidForUser(token.getMyUser())) {
                token.confirm();
                confirmationTokenRepository.save(token);
                Optional<MyUser> myUser = myUserRepository.findMyUserByUsername(token.getMyUser());
                if (myUser.isPresent()) {
                    myUser.get().setEnabled(true);
                    myUserRepository.save(myUser.get());
                    return myUserMapper.myUserToMyUserDTO(myUser.get());
                } else {
                    throw new ApiExceptionResponse("User not found", HttpStatus.NOT_FOUND, null);
                }
            } else {
                throw new ApiExceptionResponse("Expired token", HttpStatus.BAD_REQUEST, null);
            }
        } else {
            throw new ApiExceptionResponse("Token not found", HttpStatus.NOT_FOUND, null);
        }
    }

    @Override
    public MyUserDTO resendToken(String confirmationToken) throws ApiExceptionResponse {
        ConfirmationToken newConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(confirmationToken);
        String username = newConfirmationToken.getMyUser();
        Optional<MyUser> myUserOptional = myUserRepository.findMyUserByUsername(username);
        if (myUserOptional.isPresent()) {
            if(newConfirmationToken.isConfirmed()) {
                MyUser myUser = myUserOptional.get();
                newConfirmationToken.refetch(username);
                confirmationTokenRepository.save(newConfirmationToken);

                EmailPayload emailPayload = EmailPayload.builder()
                        .recipient(myUser.getEmailAddress())
                        .category("refetch-token")
                        .subject("Token confirmation")
                        .username(myUser.getUsername())
                        .body("To confirm your account, please click here: "
                                + "http://localhost/confirm-account/" + newConfirmationToken.getConfirmationToken()).build();
                emailService.sendWelcomeEmail(emailPayload);
                emailService.save(emailPayload);
                return myUserMapper.myUserToMyUserDTO(myUser);
            }else{
                throw new ApiExceptionResponse("Token has been already confirmed.", HttpStatus.NOT_ACCEPTABLE, null);

            }
        } else {
            throw new ApiExceptionResponse("User not found", HttpStatus.NOT_FOUND, null);
        }
    }
}
