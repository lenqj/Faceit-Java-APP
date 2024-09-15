package Main.model;

import Main.utils.MyMatchEventListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailPayload extends AbstractEntity{

    @NotBlank(message = "Recipient email must not be blank")
    @Email(message = "Invalid email format")
    private String recipient;

    @NotBlank(message = "Category must not be blank")
    private String category;

    @NotBlank(message = "Subject must not be blank")
    private String subject;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Body must not be blank")
    private String body;
}
