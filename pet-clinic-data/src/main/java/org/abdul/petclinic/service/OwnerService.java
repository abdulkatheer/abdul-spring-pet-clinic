package org.abdul.petclinic.service;

import org.abdul.petclinic.model.Owner;

import java.util.Set;

public interface OwnerService {
    Owner findById(Long id);

    void save(Owner owner);

    Set<Owner> findAll();
}
