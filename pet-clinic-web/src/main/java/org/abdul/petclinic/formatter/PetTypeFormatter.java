package org.abdul.petclinic.formatter;

import org.abdul.petclinic.model.PetType;
import org.abdul.petclinic.service.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class PetTypeFormatter implements Formatter<PetType> {
    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String petName, Locale locale) throws ParseException {
        Optional<PetType> petTypeOptional = petTypeService.findAll().stream()
                .filter(petType -> petType.getName().equalsIgnoreCase(petName))
                .findFirst();

        if (petTypeOptional.isPresent()) {
            return petTypeOptional.get();
        }
        throw new ParseException("Type not found " + petName, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
