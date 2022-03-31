package kijin.bang.keygenie.repository;

import kijin.bang.keygenie.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
