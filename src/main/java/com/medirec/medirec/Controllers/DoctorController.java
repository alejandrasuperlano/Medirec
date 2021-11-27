package com.medirec.medirec.Controllers;

import java.util.List;

import com.medirec.medirec.Dto.DoctorUpdateInfoDto;
import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Security.JWT.JwtProvider;
import com.medirec.medirec.Services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/doctor")
@Api(tags = "Doctor", description = "CRUD and other operations for doctors")
public class DoctorController {

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping(path = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search doctors by name or specialization")
    public ResponseEntity<Response> searchDoctor(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String specialization,
        @RequestParam("sessionToken") String sessionToken
    ){  
        
        if(!jwtProvider.tokenValidation(sessionToken)){
            Response tokenResponse = new Response(
                HttpStatus.BAD_REQUEST.toString(),
                "Invalid session token",
                null
            );
            
            return new ResponseEntity<Response>(tokenResponse, HttpStatus.BAD_REQUEST);
        }
        
        Response response;
        List<Doctor> results;

        if(name != null && specialization == null){

            results = doctorService.searchByName(name);

        }else if(specialization != null && name == null){

            results = doctorService.searchBySpecialization(specialization);

        }else if(name == null && specialization == null){

            response = new Response(
                HttpStatus.BAD_REQUEST.toString(),
                "Either name or specialization parameters are required",
                null
            );

            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }else{
            response = new Response(
                HttpStatus.OK.toString(),
                "Search using both",
                null
            );

            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }

        String message = results.isEmpty() ? "No doctors found" : "Search done correctly";
        
        response = new Response(
            HttpStatus.OK.toString(),
            message,
            results
        );

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping(path = {"{doctorId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch doctor information by doctor id")
    public ResponseEntity<Response> getDoctorById(
        @PathVariable("doctorId") int id,
        @RequestParam("sessionToken") String sessionToken
    ){
        if(!jwtProvider.tokenValidation(sessionToken)){
            Response tokenResponse = new Response(
                HttpStatus.BAD_REQUEST.toString(),
                "Invalid session token",
                null
            );
            
            return new ResponseEntity<Response>(tokenResponse, HttpStatus.BAD_REQUEST);
        }
        
        Response response;

        Doctor doctor = doctorService.getDoctorById(id);
        if(doctor != null){
            response = new Response(HttpStatus.OK.toString(), null, doctor);
        }else{
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No doctor with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PutMapping(path = {"{doctorId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update doctor's information")
    public ResponseEntity<String> updateDoctor(
        @PathVariable("doctorId") int id,
        @RequestBody DoctorUpdateInfoDto dto,
        @RequestParam("sessionToken") String sessionToken
    ){
        if(!jwtProvider.tokenValidation(sessionToken)){
            return new ResponseEntity<String>("Invalid session token", HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = doctorService.getDoctorById(id);
        if(doctor == null){
            return new ResponseEntity<String>("No doctor with such id", HttpStatus.BAD_REQUEST);
        }
        
        doctor.setDoctorExperience(dto.getExperience());
        doctor.setUserGender(dto.getGender());
        doctor.setUserAddress(dto.getHomeAddress());
        doctor.setDoctorConsultory(dto.getConsultory());
        
        doctorService.saveDoctor(doctor);

        return new ResponseEntity<String>("Doctor's info updated successfully", HttpStatus.OK);
    }
}
