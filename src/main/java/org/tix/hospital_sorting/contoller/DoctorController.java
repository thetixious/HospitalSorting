package org.tix.hospital_sorting.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tix.hospital_sorting.model.Patient;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @PostMapping("/triage")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        // Валидация уровня срочности
        if (patient.getTriageLevel() < 1 || patient.getTriageLevel() > 5) {
            return ResponseEntity.badRequest().build();
        }

        // Генерация ID и времени поступления
        patient.setId(idCounter.getAndIncrement());
        patient.setAdmissionTime(LocalDateTime.now());

        triageQueue.add(patient);
        return ResponseEntity.ok(patient);
    }

    // Получение отсортированного списка пациентов
    @GetMapping("/triage")
    public ResponseEntity<List<Patient>> getTriageList() {
        // Сортировка по приоритету (1 -> 5) и времени поступления
        List<Patient> sorted = triageQueue.stream()
                .sorted(Comparator
                        .comparingInt(Patient::getTriageLevel)
                        .thenComparing(Patient::getAdmissionTime)
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(sorted);
    }

    // Обновление уровня срочности
    @PutMapping("/triage/{id}")
    public ResponseEntity<Patient> updateTriageLevel(
            @PathVariable Long id,
            @RequestParam int newLevel
    ) {
        if (newLevel < 1 || newLevel > 5) {
            return ResponseEntity.badRequest().build();
        }

        return triageQueue.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(p -> {
                    p.setTriageLevel(newLevel);
                    return ResponseEntity.ok(p);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удаление пациента из очереди
    @DeleteMapping("/triage/{id}")
    public ResponseEntity<Void> dischargePatient(@PathVariable Long id) {
        boolean removed = triageQueue.removeIf(p -> p.getId().equals(id));
        return removed
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
