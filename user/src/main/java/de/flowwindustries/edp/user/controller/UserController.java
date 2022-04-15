package de.flowwindustries.edp.user.controller;

import de.flowwindustries.edp.user.controller.dto.UserDTO;
import de.flowwindustries.edp.user.domain.User;
import de.flowwindustries.edp.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

/**
 * Resource for {@link User} entity.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * HTTP GET to get a user resource.
     * @param identifier
     * @return
     */
    @GetMapping(value = "/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("identifier") String identifier) {
        return ResponseEntity.ok(userService.getUser(identifier));
    }

    /**
     * HTTP GET all user resources.
     * @return
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * HTTP POST to create a new user resource.
     * @param userDTO
     * @return
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> postUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return ResponseEntity
                .created(URI.create(String.format("/api/v1/user/%s", user.getIdentifier())))
                .body(user);
    }

    /**
     * HTTP PUT to update a user resource.
     * @param identifier
     * @param userDTO
     * @return
     */
    @PutMapping(value = "/{identifier}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> putUser(@PathVariable("identifier") String identifier,
                                        @RequestBody UserDTO userDTO) {
        return ResponseEntity
                .ok(userService.updateUser(identifier, userDTO));
    }

    /**
     * HTTP DELETE to delete a user resource.
     * @param identifier
     * @return
     */
    @DeleteMapping(value = "/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@PathVariable("identifier") String identifier) {
        userService.deleteUser(identifier);
        return ResponseEntity.ok().build();
    }
}
