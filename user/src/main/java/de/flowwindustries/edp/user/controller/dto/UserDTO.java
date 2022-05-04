package de.flowwindustries.edp.user.controller.dto;

import de.flowwindustries.edp.user.domain.User;
import de.flowwindustries.edp.user.domain.Status;
import lombok.Data;

/**
 * Data Transfer Object for {@link User} entities.
 */
@Data
public class UserDTO {

    /**
     * User name.
     */
    private String name;

    /**
     * User mail.
     */
    private String mail;

    /**
     * User address.
     */
    private String address;

    /**
     * User status.
     */
    private Status status;
}
