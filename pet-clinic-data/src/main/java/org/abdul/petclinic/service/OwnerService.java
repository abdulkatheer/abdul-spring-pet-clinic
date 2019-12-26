package org.abdul.petclinic.service;

import org.abdul.petclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {

    List<Owner> findByLastName(String lastName);

    List<Owner> findByLastNameLike(String lastNamePattern);
}
