package org.example.survey.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String userName;
    private String passWord;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
