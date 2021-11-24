package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Services.PatientServiceImpl;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/patient")
@Api(tags = "Patient", description = "CRUD and other operations for patients")
public class PatientController {

    @Autowired
    UserService userService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(path = {"{patientId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch patient information by patient id")
    public ResponseEntity<Response> getPatientById(@PathVariable("patientId") int id){
        
        Response response;

        try {
            Patient patient = patientService.getPatientById(id);
            response = new Response(HttpStatus.OK.toString(), null, patient);
        } catch (IllegalStateException e) {
            response = new Response(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
