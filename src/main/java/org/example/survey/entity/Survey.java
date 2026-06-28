package org.example.survey.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Survey {
    private Long id;
    private String title;
    private String description;
    private Integer status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
