package org.tix.hospital_sorting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PatientStory {
    @Id
    private Long id;
    private String story;
    private String category;

}
