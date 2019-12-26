package org.abdul.petclinic.repository;

import org.abdul.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    List<Owner> findByLastName(String lastName);
    List<Owner> findByLastNameIsLike(String lastNamePattern);
}
