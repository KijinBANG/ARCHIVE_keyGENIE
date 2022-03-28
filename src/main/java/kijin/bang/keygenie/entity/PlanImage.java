package kijin.bang.keygenie.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "plan") //연관 관계시 항상 주의
public class PlanImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY) //무조건 lazy로(일단 이렇게 알고있고, 필요할 때 더 공부!)
    private Plan plan;

}
