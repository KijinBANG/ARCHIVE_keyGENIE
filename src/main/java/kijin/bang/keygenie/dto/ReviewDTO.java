package kijin.bang.keygenie.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long reviewNum;
    private Long id;
    private Long mno;
    private String nickname;
    private String email;
    private int grade;
    private String text;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}