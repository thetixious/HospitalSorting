package org.tix.hospital_sorting.util;

import org.springframework.stereotype.Component;

@Component
public class BasicTriageStrategy implements TriageStrategy {
    @Override
    public int calculateTriageLevel(String symptoms, int heartRate, int systolicBP, int respiratoryRate) {
        int score = 0;
        if (heartRate > 120 || heartRate < 50) score += 2;
        if (systolicBP < 90) score += 3;
        if (respiratoryRate > 30 || respiratoryRate < 12) score += 2;
        if (symptoms.toLowerCase().contains("боль в груди")) score += 3;
        if (symptoms.toLowerCase().contains("потеря сознания")) score += 4;

        return switch (score) {
            case 0, 1, 2 -> 5;
            case 3, 4 -> 4;
            case 5, 6 -> 3;
            case 7, 8 -> 2;
            default -> 1;
        };
    }
}