package org.tix.hospital_sorting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Staff {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String position;
    private String username;
    private String password;



}
