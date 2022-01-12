package com.medirec.medirec.Services.Interfaces;

import com.medirec.medirec.Dto.DoctorsListDto;
import com.medirec.medirec.Dto.PatientsListDto;
import com.medirec.medirec.Models.Access;
import com.medirec.medirec.Models.Doctor;

import java.util.ArrayList;
import java.util.List;

public interface AccessService {

    List<DoctorsListDto> getDoctorsRequestingProfile (int patientId) throws Exception;
    List<DoctorsListDto> getMyDoctors(int patientId) throws Exception;
    List<PatientsListDto> getMyPatients(int doctorId) throws Exception;
    List<Integer> getMyPatientsIds(int doctorId) throws Exception;
    void acceptRequest (int patientId, int doctorId) throws Exception;
    void rejectRequest (int doctorId, int patientId) throws Exception;
    void saveRequest (int idDoctor, int idPatient);
}
