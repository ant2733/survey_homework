package org.example.survey.mapper;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface PermissionMapper {
    List<String> selectPermissionsByUserId(Long userId);
}
