package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.AccessRequestDto;
import com.medirec.medirec.Dto.DoctorsListDto;
import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Access;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Security.JWT.JwtProvider;
import com.medirec.medirec.Services.Interfaces.AccessService;
import com.medirec.medirec.Services.PatientServiceImpl;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/patient")
@Api(tags = "Patient", description = "CRUD and other operations for patients")
public class PatientController {

    @Autowired
    UserService userService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    AccessService accessService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping(path = {"{patientId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch patient information by patient id")
    public ResponseEntity<Response> getPatientById(
        @PathVariable("patientId") int id,
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

        Patient patient = patientService.getPatientById(id);
        if(patient != null){
            response = new Response(HttpStatus.OK.toString(), null, patient);
        }else{
            
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No patient with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Show a list of doctors that are requesting for accessing to a patient profile")
    @PostMapping ("/prof={patientId}/management-requests")
    public ResponseEntity requestManagement(@PathVariable("patientId") int patientId){
        try{
            List<DoctorsListDto> requesters = accessService.getDoctorsRequestingProfile(patientId);
            if (requesters != null && requesters.size() > 0){
                int numOfRequests = requesters.size();
                return new ResponseEntity(new Response("OK", "Tienes "+ numOfRequests + " solicitudes de" +
                        " acceso a tu perfil", requesters), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(new Response("BAD", "No tienes ninguna solicitud",
                        null), HttpStatus.ACCEPTED);
            }
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Accept a request made by a doctor")
    @PostMapping("/acceptRequest")
    public ResponseEntity acceptRequest (@RequestBody AccessRequestDto access){
        try{
            accessService.acceptRequest(access.getPatientId(), access.getDoctorId());
            return new ResponseEntity(new Response("OK", "Has aceptado a este doctor!",
                    null), HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Reject a request made by a doctor")
    @PostMapping("/rejectRequest")
    public ResponseEntity rejectRequest (@RequestBody AccessRequestDto access){
        try{
            accessService.rejectRequest(access.getPatientId(), access.getDoctorId());
            return new ResponseEntity(new Response("OK", "Has rechazado a este doctor!",
                    null), HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Show a list of doctors that are treating a patient and were accepted by the patient")
    @GetMapping ("/prof={patientId}/mydoctors")
    public ResponseEntity myDoctors (@PathVariable("patientId") int patientId){
        try{
            List<DoctorsListDto> myDoctors = accessService.getMyDoctors(patientId);
            if (myDoctors != null && myDoctors.size() > 0){
                int numOfDoctors = myDoctors.size();
                return new ResponseEntity(new Response("OK", "Tus doctores son: ", myDoctors), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(new Response("BAD", "No tienes ningun doctor asignado",
                        null), HttpStatus.ACCEPTED);
            }
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

}
