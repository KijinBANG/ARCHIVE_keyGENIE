package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.TaskDTO;
import kijin.bang.keygenie.entity.Task;
import kijin.bang.keygenie.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    @Override
    public List<TaskDTO> getAll() {
        List<Task> tasks = taskRepository.findAll();
        log.info("here is TaskServiceImpl. tasks: " + tasks);
        return tasks.stream().map(plan -> entityToDTO(plan)).collect(Collectors.toList());
    }

    @Override
    public TaskDTO register(TaskDTO taskDTO) {
        Task task = dtoToEntity(taskDTO);
        taskRepository.save(task);
        return entityToDTO(task);
    }

    @Override
    public TaskDTO modify(TaskDTO taskDTO) {
        Optional<Task> result = taskRepository.findById(taskDTO.getId());
        Task task;
        Task data = dtoToEntity(taskDTO);
        if(result.isPresent()){
            task = result.get();
            task.changeTitle(data.getTitle());
            task.changeDescription(data.getDescription());
            task.changeLocation(data.getLocation());
            task.changePriority(data.getPriority());
            task.changeAllDay(data.getAllDay());
            task.changeStart(data.getStart());
            task.changeEnd(data.getEnd());
            task.changeBackgroundColor(data.getBackgroundColor());
            task.changeBorderColor(data.getBorderColor());
            task.changeTextColor(data.getTextColor());

            taskRepository.save(task);
            return entityToDTO(task);
        }else {
            return null;
        }

    }

    @Override
    public Long remove(Long id) {
        taskRepository.deleteById(id);
        return id;
    }
}
