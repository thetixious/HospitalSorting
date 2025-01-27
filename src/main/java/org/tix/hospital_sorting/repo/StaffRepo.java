package org.tix.hospital_sorting.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tix.hospital_sorting.model.Staff;

import java.util.Optional;

@Repository
public interface StaffRepo extends CrudRepository<Staff, Long> {

    Optional<Staff> findStaffByUsername(String username);

    Optional<Staff> findStaffByPassword(String password);

}
