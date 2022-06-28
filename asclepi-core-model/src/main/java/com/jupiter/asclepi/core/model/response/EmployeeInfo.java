package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.response.common.CreationInfo;

public record EmployeeInfo(
        int id,
        String login,
        Integer roleId,
        String name,
        String surname,
        String middleName,
        String additionalInfo,
        CreationInfo creation
) {
}
