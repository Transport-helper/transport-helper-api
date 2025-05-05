package com.transport.annotations;

import com.transport.model.Location;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class TwoLocationsValidator implements ConstraintValidator<TwoLocations, List<Location>> {

    @Override
    public boolean isValid(List<Location> locations, ConstraintValidatorContext constraintValidatorContext) {
        return locations != null && locations.size() == 2;
    }
}
