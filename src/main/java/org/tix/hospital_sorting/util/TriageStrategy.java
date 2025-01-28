package org.tix.hospital_sorting.util;

public interface TriageStrategy {
    int calculateTriageLevel(String symptoms, int heartRate, int systolicBP, int respiratoryRate);
}
