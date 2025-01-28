package org.tix.hospital_sorting.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tix.hospital_sorting.dto.TriageRequest;
import org.tix.hospital_sorting.model.Patient;
import org.tix.hospital_sorting.util.SortingAlgorithm;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/nurse")
public class NurseController {
    public NurseController(SortingAlgorithm sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    @PostMapping("/assess")
    public ResponseEntity<Patient> assessPatient(
            @RequestBody TriageRequest request,
            @RequestParam(defaultValue = "basic") String algorithm) {

        sortingAlgorithm.setSortingStrategy(algorithm);

        int triageLevel = sortingAlgorithm.calculateTriageLevel(
                request.getSymptoms(),
                request.getHeartRate(),
                parseSystolicBloodPressure(request.getBloodPressure()),
                request.getRespiratoryRate()
        );

        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setSymptoms(request.getSymptoms());
        patient.setTriageLevel(triageLevel);
        patient.setAdmissionTime(LocalDateTime.now());

        sortingAlgorithm.addToQueue(patient);

        return ResponseEntity.ok(patient);
    }

    private int parseSystolicBloodPressure(String bp) {
        return Integer.parseInt(bp.split("/")[0]);
    }

}
