package de.flowwindustries.edp.order.change.events.user.controller;

import de.flowwindustries.edp.order.change.events.user.service.UserService;
import de.flowwindustries.edp.order.change.events.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/debug/holders")
@RequiredArgsConstructor
public class UserDebugController {

    private final UserService userService;

    /**
     * GET all persisted orderHolders
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }


}
