package Main.DTO;

import Main.model.Score;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserCreationDTO {
        String username;
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!~*()_])[A-Za-z\\d@#$%^&+=!~*()_]{8,}$", message = "Password must start with an uppercase letter, contain lowercase letters, at least one digit, at least one special character, and have a minimum length of 8 characters.")
        String password;
        UserRoleDTO role;
        String firstName;
        String lastName;
        String emailAddress;
        Score score;
        Integer numberOfMatches;
}
