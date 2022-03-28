package kijin.bang.keygenie.controller;

import kijin.bang.keygenie.dto.PlanDTO;
import kijin.bang.keygenie.service.PlanService;
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
public class CalendarController {
    private final PlanService planService;

    @GetMapping("/list")
    public ResponseEntity<List<PlanDTO>> getlist() {
        List<PlanDTO> plans = planService.getOnlyPlanList();
        log.info("plans: " + plans);
        return new ResponseEntity<List<PlanDTO>>(plans, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Long> registerPlan(@RequestBody PlanDTO planDTO) {
        log.info("here is CalendarController. planDTO: " + planDTO);
        Long result = planService.register(planDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<PlanDTO> modifyPlan(@RequestBody PlanDTO calendarDTO) {
        log.info("let\'s modify plan. calendarDTO: " + calendarDTO);
        PlanDTO result = planService.modifyPlan(calendarDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Long> removePlan(@PathVariable Long id) {
        log.info("let\' delete plan. id: " + id);
        planService.removePlan(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
