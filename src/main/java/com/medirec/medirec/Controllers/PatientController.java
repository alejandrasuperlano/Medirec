package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.*;
import com.medirec.medirec.Models.*;
import com.medirec.medirec.Repositories.ScoreRepository;
import com.medirec.medirec.Security.JWT.JwtProvider;
import com.medirec.medirec.Services.Interfaces.AccessService;
import com.medirec.medirec.Services.DoctorServiceImpl;
import com.medirec.medirec.Services.Interfaces.SymptomService;
import com.medirec.medirec.Services.PatientServiceImpl;
import com.medirec.medirec.Services.ScoreServiceImpl;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    DoctorServiceImpl doctorService;

    @Autowired
    AccessService accessService;

    @Autowired
    ScoreServiceImpl scoreService;

    @Autowired
    SymptomService symptomService;

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

    @ApiOperation(value = "Update patient's information")
    @PutMapping(path = {"{patientId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePatient(
        @PathVariable("patientId") int id,
        @RequestBody PatientUpdateInfoDto dto,
        @RequestParam("sessionToken") String sessionToken
    ){
        if(!jwtProvider.tokenValidation(sessionToken)){
            return new ResponseEntity<String>("Invalid session token", HttpStatus.BAD_REQUEST);
        }

        Patient patient = patientService.getPatientById(id);
        if(patient == null){
            return new ResponseEntity<String>("No patient with such id", HttpStatus.BAD_REQUEST);
        }
        
        patient.setUserGender(dto.getGender());
        patient.setUserAddress(dto.getHomeAddress());
        patient.setPatientEps(dto.getEps());
        patient.setPatientMaritalStatus(dto.getMaritalStatus());
        
        patientService.savePatient(patient);

        return new ResponseEntity<String>("Patient's info updated successfully", HttpStatus.OK);
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

    @GetMapping(path = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search patients by name or document id")
    public ResponseEntity<Response> searchPatients(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String doc,
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
        List<Patient> results = new ArrayList<>();

        if(name != null && doc == null){

            results = patientService.searchByName(name);

        }else if(doc != null && name == null){
            Patient patient;
            try {
                patient = patientService.searchByDoc(doc).get();
            } catch (NoSuchElementException e) {
                response = new Response(
                    HttpStatus.BAD_REQUEST.toString(),
                    "No patient found",
                    null
                );
    
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            }
            results.add(patient);

        }else if(name == null && doc == null){

            response = new Response(
                HttpStatus.BAD_REQUEST.toString(),
                "Either name or doc parameters are required",
                null
            );

            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }else{
            response = new Response(
                HttpStatus.BAD_REQUEST.toString(),
                "Can't search using both parameters",
                null
            );

            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        String message = results.isEmpty() ? "No doctors found" : "Search done correctly";
        
        response = new Response(
            HttpStatus.OK.toString(),
            message,
            results
        );

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PostMapping(path = "scores/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Send a score of a doctor")
    public ResponseEntity<Response> sendScore(
        @PathVariable("patientId") int patientId,
        @RequestParam("doctorId") int doctorId,
        @RequestParam("sessionToken") String sessionToken,
        @RequestBody ScoreDto dto
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

        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientById(patientId);

        if(doctor == null){
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No doctor found with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        if(patient == null){
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No patient found with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        Score score = new Score(dto);
        score.setDoctor(doctor);
        score.setPatient(patient);
        scoreService.saveScore(score);
        
        response = new Response(HttpStatus.OK.toString(), "Score saved succesfully", null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PutMapping(path = "scores/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a score of a doctor")
    public ResponseEntity<Response> updateScore(
        @PathVariable("patientId") int patientId,
        @RequestParam("doctorId") int doctorId,
        @RequestParam("sessionToken") String sessionToken,
        @RequestBody ScoreDto dto
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

        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientById(patientId);

        if(doctor == null){
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No doctor found with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        if(patient == null){
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No patient found with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        Score score = scoreService.getScoreById(dto.getScoreId());
        if(score == null){
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No score found with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        score.setOpinion(dto.getOpinion());
        score.setScore(dto.getScore());
        scoreService.saveScore(score);
        
        response = new Response(HttpStatus.OK.toString(), "Score updated succesfully", null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Register the symptoms and medicines consumed by a specific patient by day")
    @PostMapping ("/prof={patientId}/symptoms/register")
    public ResponseEntity registerSymptom (@PathVariable("patientId") int patientId,
                                           @RequestBody SymptomDto symptomDto,
                                           @RequestParam("sessionToken") String sessionToken,
                                           BindingResult result){
        if(!result.hasErrors()){
            if(!jwtProvider.tokenValidation(sessionToken)){
                return new ResponseEntity(new Response("BAD", "El token de sesión ha expirado " +
                        "o no es un token válido", null), HttpStatus.BAD_REQUEST);
            } else {
                try{
                    Symptom symptom = new Symptom();
                    symptom = modelMapper.map(symptomDto, Symptom.class);
                    if(symptomService.saveSymptom(symptom, patientId) != null){
                        return new ResponseEntity(new Response("OK", "Síntomas y medicinas registradas con éxito",
                                null), HttpStatus.ACCEPTED);
                    }
                } catch (Exception e){
                    return new ResponseEntity(new Response("BAD", e.getMessage(),
                            null), HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity(new Response("BAD", "No se han registrado los síntomas y/o medicinas",
                        null), HttpStatus.ACCEPTED);
            }
        } else{
            return new ResponseEntity(new Response("BAD", "Hay uno o más campos en blanco",
                    symptomDto), HttpStatus.BAD_REQUEST);
        }
    }

}
