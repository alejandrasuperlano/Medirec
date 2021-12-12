package com.medirec.medirec.Services;

import com.medirec.medirec.Dto.DoctorsListDto;
import com.medirec.medirec.Dto.PatientsListDto;
import com.medirec.medirec.Models.Access;
import com.medirec.medirec.Models.AccessPK;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Repositories.AccessRepository;
import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Services.Interfaces.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    AccessRepository accessRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;


    @Override
    public List<DoctorsListDto> getDoctorsRequestingProfile(int patientId) throws Exception{
        try{
            List<Access> requesters = accessRepository.getRequestsByPatientUserId(patientId);

            if (requesters == null){
                return null;
            }

            List<DoctorsListDto> doctorsRequesting = new ArrayList<>();
            for (Access element : requesters){
                Doctor doctor = doctorRepository.findById(element.getDoctor().getUserId()).get();
                DoctorsListDto doctorDto = new DoctorsListDto();
                doctorDto.setFirstName(doctor.getUserFirstName());
                doctorDto.setLastName(doctor.getUserLastName());
                doctorDto.setSpecialization(doctor.getDoctorSpecialization());
                doctorDto.setId(doctor.getUserId()); // Este campo debe estar oculto en front
                doctorsRequesting.add(doctorDto);
            }
            return doctorsRequesting;

        } catch (Exception e){
            throw new Exception("No se ha encontrado al usuario");
        }

    }

    @Override
    public List<DoctorsListDto> getMyDoctors(int patientId) throws Exception{
        try{
            List<Access> requesters = accessRepository.getMyDoctorsByPatientId(patientId);

            if (requesters == null){
                return null;
            }

            List<DoctorsListDto> myDoctors = new ArrayList<>();
            for (Access element : requesters){
                Doctor doctor = doctorRepository.findById(element.getDoctor().getUserId()).get();
                DoctorsListDto doctorDto = new DoctorsListDto();
                doctorDto.setFirstName(doctor.getUserFirstName());
                doctorDto.setLastName(doctor.getUserLastName());
                doctorDto.setSpecialization(doctor.getDoctorSpecialization());
                doctorDto.setId(doctor.getUserId()); // Este campo debe estar oculto en frontend
                myDoctors.add(doctorDto);
            }
            return myDoctors;
        } catch (Exception e){
            throw new Exception("No se ha encontrado al usuario");
        }

    }

    @Override
    public List<PatientsListDto> getMyPatients(int doctorId) throws Exception{
        try{
            List<Access> requesters = accessRepository.getMyPatientsByDoctorId(doctorId);

            if (requesters == null){
                return null;
            }

            List<PatientsListDto> myPatients = new ArrayList<>();
            for (Access element : requesters){
                Patient patient = patientRepository.findById(element.getPatient().getUserId()).get();
                PatientsListDto patientDto = new PatientsListDto();
                patientDto.setFirstName(patient.getUserFirstName());
                patientDto.setLastName(patient.getUserLastName());
                patientDto.setGender(patient.getUserGender());
                patientDto.setDoc(patient.getUserDoc());
                patientDto.setId(patient.getUserId()); // Este campo debe estar oculto en frontend
                myPatients.add(patientDto);
            }
            return myPatients;

        } catch (Exception e){
            throw new Exception("No se ha encontrado al usuario");
        }

    }

    @Override
    public void acceptRequest(int patientId, int doctorId) throws Exception {
        try{
            Access requestDB = accessRepository.getAccessByPatientIdAndDoctorId(doctorId, patientId).get();
            Access requestMod = new Access();
            requestMod.setId(requestDB.getId());
            requestMod.setPatient(requestDB.getPatient());
            requestMod.setDoctor(requestDB.getDoctor());
            requestMod.setPermission(1);
            accessRepository.save(requestMod);
        } catch (Exception e){
            throw new Exception("No se ha encontrado la solicitud o ya has aceptado a este doctor");
        }

    }

    @Override
    public void rejectRequest(int doctorId, int patientId) throws Exception {
        try{
            Access request = accessRepository.getAccessByPatientIdAndDoctorId(patientId, doctorId).get();
            request.setPatient(null);
            request.setDoctor(null);
            AccessPK pk = new AccessPK(doctorId, patientId);
            accessRepository.deleteById(pk);
        } catch (Exception e){
            throw new Exception("No se ha encontrado la solicitud");
        }
    }


    @Override
    public void saveRequest(int idDoctor, int idPatient) {
        Access request = new Access();
        AccessPK pk = new AccessPK(idPatient, idDoctor);
        request.setId(pk);
        request.setDoctor(doctorRepository.findById(idDoctor).get());
        request.setPatient(patientRepository.findById(idPatient).get());
        request.setPermission(0);
        accessRepository.save(request);
    }

}
