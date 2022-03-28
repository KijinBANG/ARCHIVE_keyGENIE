package kijin.bang.keygenie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private int grade;
    private String start;
    private String end;

    @Builder.Default
    private List<PlanImageDTO> imageDTOList = new ArrayList<>();

    //Plan 의 평균 평점
    private double avg;
    //리뷰 수 jpa의 count()
    private long reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
