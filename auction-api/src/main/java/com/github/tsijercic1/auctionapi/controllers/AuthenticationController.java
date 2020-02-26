package com.github.tsijercic1.auctionapi.controllers;

import com.github.tsijercic1.auctionapi.exceptions.AppException;
import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.exceptions.UnauthorizedException;
import com.github.tsijercic1.auctionapi.models.Role;
import com.github.tsijercic1.auctionapi.models.RoleType;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.payload.*;
import com.github.tsijercic1.auctionapi.repositories.RoleRepository;
import com.github.tsijercic1.auctionapi.repositories.UserRepository;
import com.github.tsijercic1.auctionapi.security.CurrentUser;
import com.github.tsijercic1.auctionapi.security.JwtAuthenticationFilter;
import com.github.tsijercic1.auctionapi.security.JwtTokenProvider;
import com.github.tsijercic1.auctionapi.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    final JwtTokenProvider tokenProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository,
                                    RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                    JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshUserData(@CurrentUser UserPrincipal userPrincipal) {
        if (userPrincipal != null) {
            logger.info(userPrincipal.getUsername());
            logger.info(userPrincipal.getPassword());
            logger.info(userPrincipal.getEmail());


            String jwt = tokenProvider.generateToken(userPrincipal);
            Optional<User> userOptional = userRepository.findByEmail(userPrincipal.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserData userData = new UserData(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getName(),
                        user.getSurname(),
                        jwt
                );
                return ResponseEntity.ok(userData);
            }
            throw new ResourceNotFoundException("User", "email", userPrincipal.getEmail());
        }

        throw new UnauthorizedException("Unauthorized");
    }

    /**
     *
     * @param registrationRequest
     * body of the registration username, email, password, name, surname
     *
     * checks if the username and email are unique
     * creates a user and sets his role of type USER which is default
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        // the request body gets mapped into the RegistrationRequest class
        String username = registrationRequest.getEmail().split("@")[0];
        if (userRepository.existsByUsername(username)) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User(registrationRequest.getName(), registrationRequest.getSurname(), username,
                registrationRequest.getEmail(), registrationRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new AppException("User Role not set."));

//        user.setRole(userRole);


        User result = null;
        result = userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "s"));
    }
}
