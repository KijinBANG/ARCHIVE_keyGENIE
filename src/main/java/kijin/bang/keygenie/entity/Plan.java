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
public class Plan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String title;
    private String description;
    private String location;

    public void changeTitle(String title) { this.title = title; }
    public void changeDescription(String description) { this.description = description; }
    public void changeLocation(String location) { this.location = location; }

}