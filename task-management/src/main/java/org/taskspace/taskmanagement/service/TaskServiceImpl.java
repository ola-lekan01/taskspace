package org.taskspace.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.taskspace.taskmanagement.data.model.Priority;
import org.taskspace.taskmanagement.data.model.Task;
import org.taskspace.taskmanagement.data.repositories.TaskRepository;
import org.taskspace.taskmanagement.data.request.TaskRequest;
import org.taskspace.taskmanagement.exception.TaskSpaceException;
import org.taskspace.usermanagement.data.models.AppUser;
import org.taskspace.usermanagement.exception.TaskSpaceUserManagementException;
import org.taskspace.usermanagement.security.UserPrincipal;
import org.taskspace.usermanagement.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.taskspace.taskmanagement.data.model.TaskStatus.DELETED;
import static org.taskspace.taskmanagement.utils.TaskUtils.generateTaskId;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    public Task createTask(TaskRequest request, UserPrincipal userPrincipal){
        AppUser foundUser = internalFindUserByEmail(userPrincipal.getEmail());
        Task task = mapper.map(request, Task.class);
        task.setTaskPriority(Priority.valueOf(request.getTaskPriority()));
        task.setTaskId(generateTaskId());
        task.setAppUser(foundUser);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(UserPrincipal userPrincipal){
        AppUser foundUser = internalFindUserByEmail(userPrincipal.getEmail());
       return taskRepository.findAllById(Collections.singleton(foundUser.getId()));
    }

    public Task updateTask(String taskId, TaskRequest request, UserPrincipal userPrincipal){
        AppUser foundUser = internalFindUserByEmail(userPrincipal.getEmail());
        Task foundTask = taskRepository.findByTaskIdAndAppUser(taskId, foundUser)
                .orElseThrow((()-> new TaskSpaceException("Task Id Does not Exist")));
        Task updatedTask = mapper.map(request, foundTask.getClass());
        updatedTask.setModifiedAt(LocalDateTime.now());
        if(request.getTaskPriority() != null) foundTask.setTaskPriority(Priority.valueOf(request.getTaskPriority()));
        return taskRepository.save(updatedTask);
    }


    public String deleteTask(String taskId, UserPrincipal userPrincipal){
        AppUser foundUser = internalFindUserByEmail(userPrincipal.getEmail());
        Task foundTask = taskRepository.findByTaskIdAndAppUser(taskId, foundUser)
                .orElseThrow((()-> new TaskSpaceException("Task Id Does not Exist")));
        foundTask.setDeletedAt(LocalDateTime.now());
        foundTask.setTaskStatus(DELETED);
        return "Task Successfully Deleted";
    }

    private AppUser internalFindUserByEmail(String email){
       return userService.findByEmailIgnoreCase(email)
                .orElseThrow(()-> new TaskSpaceUserManagementException("User not Found"));
    }
}