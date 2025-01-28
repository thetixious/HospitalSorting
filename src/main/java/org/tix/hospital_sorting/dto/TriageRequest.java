package org.tix.hospital_sorting.dto;

import lombok.Data;

@Data
public class TriageRequest {
    private String name;
    private int age;
    private String symptoms;
    private int heartRate;
    private String bloodPressure;
    private int respiratoryRate;

}