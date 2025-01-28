package org.tix.hospital_sorting.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tix.hospital_sorting.model.Patient;
import org.tix.hospital_sorting.util.SortingAlgorithm;

@Controller
@RequestMapping("/nurse")
public class NurseController {
    private final SortingAlgorithm sortingAlgorithm; // Общий сервис с DoctorController

    public NurseController(SortingAlgorithm sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    // Модель для ввода данных пациентов медсестрой
    public static class TriageRequest {
        private String name;
        private int age;
        private String symptoms;
        private int heartRate;
        private String bloodPressure; // в формате "120/80"
        private int respiratoryRate;

        // Геттеры и сеттеры
    }

    // Автоматическая оценка состояния пациента
    @PostMapping("/assess")
    public ResponseEntity<Patient> assessPatient(@RequestBody TriageRequest request) {
        // Определение уровня срочности на основе медицинских показателей
        int triageLevel = sortingAlgorithm.calculateTriageLevel(
                request.getSymptoms(),
                request.getHeartRate(),
                parseSystolicBloodPressure(request.getBloodPressure()),
                request.getRespiratoryRate()
        );

        // Создание пациента
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setSymptoms(request.getSymptoms());
        patient.setTriageLevel(triageLevel);
        patient.setAdmissionTime(LocalDateTime.now());

        // Добавление в общую очередь (как в DoctorController)
        triageService.addToQueue(patient);

        return ResponseEntity.ok(patient);
    }

    private int parseSystolicBloodPressure(String bp) {
        return Integer.parseInt(bp.split("/")[0]);
    }
}
}
