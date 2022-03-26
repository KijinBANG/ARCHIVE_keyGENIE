package kijin.bang.keygenie.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
    private String email;
    private String password;
    private String nickname;
    private boolean fromSocial;
    private LocalDate birthday;
    @OneToMany
    private List<Board> board;

    //권한을 여러개 가질 수 있도록 설정
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roleSet = new HashSet<>();

    //권한을 추가하는 메소드
    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }

    //회원정보를 수정하는 메소드
    public void changePassword(String password) { this.password = password; }
    public void changeNickname(String nickname) { this.nickname = nickname; }

}