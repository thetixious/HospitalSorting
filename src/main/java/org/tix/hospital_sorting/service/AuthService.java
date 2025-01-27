package org.tix.hospital_sorting.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tix.hospital_sorting.repo.StaffRepo;

@Service
public class AuthService {
    private final StaffRepo staffRepo;

    public AuthService(StaffRepo staffRepo) {
        this.staffRepo = staffRepo;
    }

    public ResponseEntity<?> login(String username, String password){
        Boolean isExist = true;
        if (staffRepo.findStaffByUsername(username).isPresent() || staffRepo.findStaffByPassword(password).isPresent()){
            return ResponseEntity.ok().body(staffRepo.findStaffByUsername(username).get().getPosition());
        }
        return ResponseEntity.badRequest().build();
    }
}
