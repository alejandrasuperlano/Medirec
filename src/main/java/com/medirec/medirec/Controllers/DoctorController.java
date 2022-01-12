package com.medirec.medirec.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.medirec.medirec.Dto.*;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.Symptom;
import com.medirec.medirec.Security.JWT.JwtProvider;
import com.medirec.medirec.Services.DoctorServiceImpl;
import com.medirec.medirec.Services.PatientServiceImpl;
import com.medirec.medirec.Services.Interfaces.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    PatientServiceImpl patientService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AccessService accessService;

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

    @ApiOperation(value = "Make a request for accessing to a patient profile")
    @PostMapping("/prof={doctorId}/makeRequest")
    public ResponseEntity requestingProfile(@PathVariable("doctorId") int doctorId,
                                            @RequestBody ProfileRequestDto access,
                                            BindingResult result){
        if (!result.hasErrors()){
            try{
                accessService.saveRequest(doctorId, access.getPatientId());
                return new ResponseEntity(new Response("OK", "Se ha realizado la solicitud con éxito",
                        null), HttpStatus.ACCEPTED);
            } catch (Exception e){
                return new ResponseEntity(new Response("BAD", "No se ha podido realizar la solicitud",
                        null), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new Response("BAD", "Hay campos en blanco",
                null), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Show a list of patients that are being treated by a doctor")
    @GetMapping ("/prof={doctorId}/mypatients")
    public ResponseEntity myPatients (@PathVariable("doctorId") int doctorId){
        try{
            List<PatientsListDto> myPatients = accessService.getMyPatients(doctorId);
            if (myPatients != null && myPatients.size() > 0){
                int numOfDoctors = myPatients.size();
                return new ResponseEntity(new Response("OK", "Tus pacientes son: ", myPatients), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(new Response("BAD", "No tienes ningun paciente en atención",
                        null), HttpStatus.ACCEPTED);
            }
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get patients latest symptoms")
    @GetMapping(path = "{doctorId}/mypatients/latestSymptoms")
    public ResponseEntity<Response> getLatestSymptoms(
        @PathVariable("doctorId") int doctorId,
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
        
        List<Integer> patientsIds;
        try {
            patientsIds = accessService.getMyPatientsIds(doctorId);
        } catch (Exception e) {
            
            response = new Response(HttpStatus.BAD_REQUEST.toString(), "No doctor with such id", null);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        List<PatientSymptomsDto> patientsSymptoms = new ArrayList<>();
        
        Calendar maxDateCalendar = Calendar.getInstance();
        maxDateCalendar.add(Calendar.DATE, -5);
        Date maxDate = maxDateCalendar.getTime();

        for (int patientId : patientsIds) {
            Patient patient = patientService.getPatientById(patientId);
            List<Symptom> symptoms = patient.getPatientMedicalHistory().getSymptoms();

            List<Symptom> latestSymptoms = new ArrayList<>();

            for (Symptom symptom : symptoms) {


                if(symptom.getDate().after(maxDate)){
                    latestSymptoms.add(symptom);
                }
            }

            PatientSymptomsDto dto = new PatientSymptomsDto();
            dto.setPatientName(patient.getUserFirstName() + " " + patient.getUserLastName());
            dto.setSymptoms(latestSymptoms);
            patientsSymptoms.add(dto);
        }

        response = new Response(HttpStatus.OK.toString(), "These are the patient's latest symptoms", patientsSymptoms);
        return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
    }
}
