package com.jupiter.asclepi.core.service.converter.security;

import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.User;

public class EmployeeToUserConverter implements Converter<Employee, User> {
    @Override
    public User convert(@Nullable Employee source) {
        return new User(source.getLogin(), source.getPassword(), source.getRole().getAuthorities());
    }
}
