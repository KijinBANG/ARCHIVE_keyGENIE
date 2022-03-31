package kijin.bang.keygenie.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Task extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    private String title;
    private String description;
    private String location;
    private int priority;
    private int allDay;
    private LocalDateTime start;
    private LocalDateTime end;
    private String backgroundColor;
    private String borderColor;
    private String textColor;

    public void changeTitle(String title) { this.title = title; }
    public void changeDescription(String description) { this.description = description; }
    public void changeLocation(String location) { this.location = location; }
    public void changePriority(int priority) { this.priority = priority; }
    public void changeAllDay(int allDay) { this.allDay = allDay; }
    public void changeStart(LocalDateTime start) { this.start = start; }
    public void changeEnd(LocalDateTime end) { this.end = end; }
    public void changeBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    public void changeBorderColor(String borderColor) { this.borderColor = borderColor; }
    public void changeTextColor(String textColor) { this.textColor = textColor; }
}
