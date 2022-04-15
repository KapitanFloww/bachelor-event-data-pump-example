package de.flowwindustries.edp.user.controller.dto;

import de.flowwindustries.edp.user.domain.User;
import de.flowwindustries.edp.user.domain.Status;
import lombok.Data;

/**
 * Data Transfer Object for {@link User} entities.
 */
@Data
public class UserDTO {
    private String name;
    private String mail;
    private String address;
    private Status status;
}
