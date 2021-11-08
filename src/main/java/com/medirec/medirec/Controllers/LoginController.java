package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.JwtDto;
import com.medirec.medirec.Dto.LoginDto;
import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Security.JWT.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @PostMapping
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto loginDto, BindingResult result){
        if(!result.hasErrors()){
            try {
                Authentication auth = authenticationManager.authenticate(new
                        UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(auth);
                String jwt = jwtProvider.tokenGenerator(auth);
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                JwtDto jwtDto;
                if (patientRepository.findPatientByUserEmail(loginDto.getEmail()).get() != null){
                    jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
                    return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
                } else if (doctorRepository.findDoctorByUserEmail(loginDto.getEmail()).get() != null){
                    jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
                    return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
                }
            } catch (Exception e){
                return new ResponseEntity(new Response("BAD", "Hay un problema con: " +
                        e.getMessage(), null), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new Response("BAD", "Hay campos en blanco o no coinciden", null),
                HttpStatus.BAD_REQUEST);
    }
}
