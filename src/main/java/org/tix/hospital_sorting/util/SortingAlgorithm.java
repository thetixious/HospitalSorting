package org.tix.hospital_sorting.util;

import org.springframework.stereotype.Service;
import org.tix.hospital_sorting.model.Patient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class SortingAlgorithm {
    private final List<Patient> triageQueue = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public int calculateTriageLevel(String symptoms, int heartRate, int systolicBP, int respiratoryRate) {
        int score = 0;

        // Логика оценки (пример):
        if (heartRate > 120 || heartRate < 50) score += 2;
        if (systolicBP < 90) score += 3;
        if (respiratoryRate > 30 || respiratoryRate < 12) score += 2;
        if (symptoms.toLowerCase().contains("боль в груди")) score += 3;
        if (symptoms.toLowerCase().contains("потеря сознания")) score += 4;

        // Определение уровня срочности
        return switch (score) {
            case 0, 1, 2 -> 5; // Не срочно
            case 3, 4 -> 4;    // Низкий приоритет
            case 5, 6 -> 3;    // Умеренный
            case 7, 8 -> 2;    // Высокий
            default -> 1;      // Экстренный
        };
    }

    public void addToQueue(Patient patient) {
        patient.setId(idCounter.getAndIncrement());
        triageQueue.add(patient);
    }

    // Методы для DoctorController
    public List<Patient> getSortedQueue() {
        return triageQueue.stream()
                .sorted(Comparator
                        .comparingInt(Patient::getTriageLevel)
                        .thenComparing(Patient::getAdmissionTime)
                )
                .collect(Collectors.toList());
    }
}
