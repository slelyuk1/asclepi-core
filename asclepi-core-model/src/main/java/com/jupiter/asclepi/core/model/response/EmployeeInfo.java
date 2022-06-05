package com.jupiter.asclepi.core.model.response;

public record EmployeeInfo(
        int id,
        String login,
        Integer roleId,
        String name,
        String surname,
        String middleName,
        String additionalInfo
) {
}
