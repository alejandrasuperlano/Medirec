package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Services.DoctorServiceImpl;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {


    @Autowired
    DoctorServiceImpl doctorService;

    @GetMapping(path = "search")
    public ResponseEntity<Response> searchDoctor(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String specialization
    ){  
        Response response;

        if(name != null && specialization == null){

            return doctorService.searchByName(name);

        }else if(specialization != null && name == null){

            return doctorService.searchBySpecialization(specialization);

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

    }
}
