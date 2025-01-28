package org.tix.hospital_sorting.util;

import org.springframework.stereotype.Component;

@Component
public class AdvancedTriageStrategy implements TriageStrategy {
    @Override
    public int calculateTriageLevel(String symptoms, int heartRate, int systolicBP, int respiratoryRate) {
        int score = 0;
        // Расширенная логика оценки
        if (heartRate > 130) score += 3;
        else if (heartRate > 120) score += 2;
        if (systolicBP < 80) score += 4;
        else if (systolicBP < 90) score += 2;
        if (respiratoryRate > 30) score += 3;
        else if (respiratoryRate > 25) score += 1;
        if (symptoms.toLowerCase().contains("боль в груди")) score += 4;
        if (symptoms.toLowerCase().contains("потеря сознания")) score += 5;
        if (symptoms.toLowerCase().contains("одышка")) score += 2;

        return score >= 10 ? 1 :
                score >= 7 ? 2 :
                        score >= 5 ? 3 :
                                score >= 3 ? 4 : 5;
    }
}