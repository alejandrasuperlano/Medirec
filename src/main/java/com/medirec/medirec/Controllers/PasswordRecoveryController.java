package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.EmailForRecoveryDto;
import com.medirec.medirec.Dto.PasswordRecoveryDto;
import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.User;
import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Security.Service.SecurityService;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import com.medirec.medirec.Services.Interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/password/recovery")
public class PasswordRecoveryController {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    MailSender mailSender;

    SecurityService securityService;

    @Autowired
    private Environment env;

    @PostMapping("/patient/resetPassword")
    public ResponseEntity patientResetPassword(@RequestBody EmailForRecoveryDto email, HttpServletRequest request) {
        Patient patient = null;
        try{
            patient = patientRepository.findPatientByUserEmail(email.getEmail()).get();
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", "No se ha encontrado al usuario",
                    null), HttpStatus.BAD_REQUEST);
        }

        String token = UUID.randomUUID().toString();
        patientService.createPasswordResetTokenForUser(patient, token);
        mailSender.send(constructResetTokenEmail(request.getLocalName(),
                request.getLocale(), token, patient));
        return new ResponseEntity(new Response("OK", "Correo enviado",
                null), HttpStatus.ACCEPTED);
    }

    @PostMapping("/doctor/resetPassword")
    public ResponseEntity doctorResetPassword(@RequestBody EmailForRecoveryDto email, HttpServletRequest request) {
        Doctor doctor = null;
        try{
            doctor = doctorRepository.findDoctorByUserEmail(email.getEmail()).get();
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", "No se ha encontrado al usuario",
                    null), HttpStatus.BAD_REQUEST);
        }

        String token = UUID.randomUUID().toString();
        doctorService.createPasswordResetTokenForUser(doctor, token);
        mailSender.send(constructResetTokenEmail(request.getLocalName(),
                request.getLocale(), token, doctor));
        return new ResponseEntity(new Response("OK", "Correo enviado",
                null), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/patient/changePassword", params = "token")
    public String patientShowChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("token") String token) {
        String result = securityService.validatePasswordResetToken(token);
        if(result != null) {
            return "redirect:/login.html?lang="
                    + locale.getLanguage() + "No hay token valido";
        } else {
            model.addAttribute("token", token);
            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        }
    }

    @GetMapping(value = "/doctor/changePassword", params = "token")
    public String doctorShowChangePasswordPage(Locale locale, Model model,
                                                @RequestParam("token") String token) {
        String result = securityService.validatePasswordResetTokenDoctor(token);
        if(result != null) {
            return "redirect:/login.html?lang="
                    + locale.getLanguage() + "No hay token valido";
        } else {
            model.addAttribute("token", token);
            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        }
    }

    @PostMapping("/patient/savePassword")
    public ResponseEntity savePassword(@Valid @RequestBody PasswordRecoveryDto passwordDto) {
        String result = patientService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new ResponseEntity(new Response("BAD", "No se ha encontrado al usuario",
                    null), HttpStatus.BAD_REQUEST);
        }

        Patient patient = patientRepository.getPatientByPasswordResetTokenPatient(passwordDto.getToken());
        if(patient != null && patientService.passwordConfirm(passwordDto.getPassword(), passwordDto.getConfirmPassword())) {
            patientService.passwordRecovery(patient);
            return new ResponseEntity(new Response("OK", "La contraseña ha sido cambiada con " +
                    "éxito", null), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(new Response("OK", "La contraseña no se ha cambiado",
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/doctor/savePassword")
    public ResponseEntity savePasswordDoctor(@Valid @RequestBody PasswordRecoveryDto passwordDto) {
        String result = doctorService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new ResponseEntity(new Response("BAD", "No se ha encontrado al usuario",
                    null), HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = doctorRepository.getDoctorByPasswordResetTokenDoctor(passwordDto.getToken());
        if(doctor != null && doctorService.passwordConfirm(passwordDto.getPassword(), passwordDto.getConfirmPassword())) {
            doctorService.passwordRecovery(doctor);
            return new ResponseEntity(new Response("OK", "La contraseña ha sido cambiada con " +
                    "éxito", null), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(new Response("OK", "La contraseña no se ha cambiado",
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    /* --------------------------- EMAIL FOR PATIENTS --------------------------- */
    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, Patient patient) {
        String url = "http://localhost:8080/password/recovery/patient/changePassword?token=" + token;
        return constructEmail("Reset Password", "hola" + " \r\n" + url, patient);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             Patient patient) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(patient.getUserEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    /* --------------------------- EMAIL FOR DOCTORS --------------------------- */
    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, Doctor doctor) {
        String url = "http://localhost:8080/password/recovery/patient/changePassword?token=" + token;
        return constructEmail("Reset Password", "hola" + " \r\n" + url, doctor);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             Doctor doctor) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(doctor.getUserEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}
