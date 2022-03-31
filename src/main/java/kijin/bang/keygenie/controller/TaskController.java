package kijin.bang.keygenie.controller;

import kijin.bang.keygenie.dto.TaskDTO;
import kijin.bang.keygenie.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring/calendar")
@Log4j2
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/list")
    public ResponseEntity<List<TaskDTO>> getlist() {
        List<TaskDTO> tasks = taskService.getAll();
        log.info("tasks: " + tasks);
        return new ResponseEntity<List<TaskDTO>>(tasks, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<TaskDTO> registerPlan(@RequestBody TaskDTO taskDTO) {
        log.info("here is TaskController. taskDTO: " + taskDTO);
        TaskDTO result = taskService.register(taskDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<TaskDTO> modifyPlan(@RequestBody TaskDTO taskDTO) {
        log.info("let\'s modify task. taskDTO: " + taskDTO);
        TaskDTO result = taskService.modify(taskDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Long> removePlan(@PathVariable Long id) {
        log.info("let\' delete plan. id: " + id);
        taskService.remove(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
