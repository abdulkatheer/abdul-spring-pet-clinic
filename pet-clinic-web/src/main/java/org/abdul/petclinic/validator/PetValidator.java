package org.abdul.petclinic.validator;

import org.abdul.petclinic.model.Pet;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PetValidator implements Validator {
    private static final String REQUIRED_ERROR_CODE = "required";

    @Override
    public boolean supports(Class<?> aClass) {
        return Pet.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Pet pet = (Pet) o;

        String petName = pet.getName();
        if (! StringUtils.hasLength(petName)) {
            errors.rejectValue("name", REQUIRED_ERROR_CODE, "Name required");
        }
        if (pet.isNew() && pet.getPetType() == null) {
            errors.rejectValue("petType", REQUIRED_ERROR_CODE, "Pet Type is required");
        }
        if (pet.getBirthDate() == null) {
            errors.rejectValue("birthDate", REQUIRED_ERROR_CODE, "Birth Date is required");
        }
    }
}
