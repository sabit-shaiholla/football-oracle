package com.oracle.football.service;

import com.oracle.football.dto.AuthenticationRequest;
import com.oracle.football.dto.AuthenticationResponse;
import com.oracle.football.dto.UserDTO;
import com.oracle.football.model.User;
import com.oracle.football.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, UserDTOMapper userDTOMapper, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDTOMapper = userDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User principal = (User) authentication.getPrincipal();
        UserDTO userDTO = userDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(userDTO.username(), userDTO.roles());
        return new AuthenticationResponse(token, userDTO);
    }
}
