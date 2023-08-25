package org.taskspace.taskmanagement.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskspace.taskmanagement.data.model.Task;
import org.taskspace.usermanagement.data.models.AppUser;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findById(long id);

    Optional<Task> findByTaskIdAndAppUser(String taskId, AppUser appUser);
}
