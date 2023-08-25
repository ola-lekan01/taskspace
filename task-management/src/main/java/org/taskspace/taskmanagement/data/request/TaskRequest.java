package org.taskspace.taskmanagement.data.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    private String taskTitle;

    private String taskBody;

    private String taskPriority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;
}