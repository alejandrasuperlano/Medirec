package com.medirec.medirec.Controllers;

import java.util.List;

import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/doctor")
@Api(tags = "Doctor", description = "CRUD and other operations for doctors")
public class DoctorController {


    @Autowired
    DoctorServiceImpl doctorService;

    @GetMapping(path = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search doctoros by name or specialization")
    public ResponseEntity<Response> searchDoctor(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String specialization
    ){  
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
}
