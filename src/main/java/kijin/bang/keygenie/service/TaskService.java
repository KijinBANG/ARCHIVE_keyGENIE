package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.TaskDTO;
import kijin.bang.keygenie.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    List<TaskDTO> getAll();

    TaskDTO register(TaskDTO taskDTO);

    TaskDTO modify(TaskDTO taskDTO);

    Long remove(Long id);

    default Task dtoToEntity(TaskDTO taskDTO) {
        String[] colors = {"#dcced3", "#d1bec7", "#c7b0bc", "#876479", "#674559", "#4c3041"};
        int allDay = taskDTO.isAllDay() ? 1 : 0;
        int p = taskDTO.getPriority();
        String textColor = p > 2 ? "white" : "gray";
        Task task = Task.builder()
                .tno(taskDTO.getId())
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .location(taskDTO.getLocation())
                .priority(p)
                .allDay(allDay)
                .start(LocalDateTime.parse(taskDTO.getStart()))
                .end(LocalDateTime.parse(taskDTO.getEnd()))
                .backgroundColor(colors[p - 1])
                .borderColor(colors[p])
                .textColor(textColor)
                .build();
        return task;
    }

    default TaskDTO entityToDTO(Task task) {
        boolean allDay = task.getAllDay() == 0 ? false : true;
        TaskDTO dto = TaskDTO.builder()
                .id(task.getTno())
                .title(task.getTitle())
                .description(task.getDescription())
                .location(task.getLocation())
                .priority(task.getPriority())
                .allDay(allDay)
                .start(task.getStart().toString())
                .end(task.getEnd().toString())
                .backgroundColor(task.getBackgroundColor())
                .borderColor(task.getBorderColor())
                .textColor(task.getTextColor())
                .regDate(task.getRegDate())
                .modDate(task.getModDate())
                .build();
        return dto;
    }
}
