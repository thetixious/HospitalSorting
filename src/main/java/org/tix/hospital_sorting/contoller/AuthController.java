package org.tix.hospital_sorting.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tix.hospital_sorting.dto.AuthDTO;
import org.tix.hospital_sorting.service.AuthService;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logIn")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO){

        return authService.login(authDTO.getUsername(), authDTO.getPassword());
    }

}
