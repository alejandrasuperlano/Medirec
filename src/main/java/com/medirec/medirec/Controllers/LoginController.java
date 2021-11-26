package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.JwtDto;
import com.medirec.medirec.Dto.LoginDto;
import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Security.JWT.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@Api(tags = "Login", description = "User authentication")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "User login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto loginDto, BindingResult result){
        if(!result.hasErrors()){
            try {
                Authentication auth = authenticationManager.authenticate(new
                        UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(auth);
                String jwt = jwtProvider.tokenGenerator(auth);
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                JwtDto jwtDto;
                
                String role = loginDto.getRole();
                if (role.equals("PATIENT")){
                    Patient patient = patientRepository.findPatientByUserEmail(loginDto.getEmail()).get();
                    if ( patient != null){
                        jwtDto = new JwtDto(jwt, userDetails.getUsername(), patient.getUserId(),userDetails.getAuthorities());
                        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
                    } else {
                        return new ResponseEntity(new Response("BAD", "El usuario no se ha registrado " +
                                "en la aplicacion", null), HttpStatus.BAD_REQUEST);
                    }
                } else if (role.equals("DOCTOR")){
                    Doctor doctor = doctorRepository.findDoctorByUserEmail(loginDto.getEmail()).get();
                    if (doctor != null){
                        jwtDto = new JwtDto(jwt, userDetails.getUsername(), doctor.getUserId(), userDetails.getAuthorities());
                        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
                    } else {
                        return new ResponseEntity(new Response("BAD", "El usuario no se ha registrado " +
                                "en la aplicacion", null), HttpStatus.BAD_REQUEST);
                    }
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
