package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.PageRequestDTO;
import kijin.bang.keygenie.dto.PageResponseDTO;
import kijin.bang.keygenie.dto.PlanDTO;
import kijin.bang.keygenie.dto.PlanImageDTO;
import kijin.bang.keygenie.entity.Plan;
import kijin.bang.keygenie.entity.PlanImage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PlanService {

    Long register(PlanDTO planDTO);

    PageResponseDTO<PlanDTO, Object[]> getList(PageRequestDTO requestDTO);

    List<PlanDTO> getOnlyPlanList();

    PlanDTO modifyPlan(PlanDTO planDTO);

    Long removePlan(Long pno);

    PlanDTO getPlan(Long pno);

    default Map<String, Object> dtoToEntity(PlanDTO planDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Plan plan = Plan.builder()
                .pno(planDTO.getId())
                .title(planDTO.getTitle())
                .description(planDTO.getDescription())
                .location(planDTO.getLocation())
                .build();
        entityMap.put("plan", plan);

        List<PlanImageDTO> imageDTOList = planDTO.getImageDTOList();

        //PlanImageDTO 처리
        if (imageDTOList != null && imageDTOList.size() > 0) {
            List<PlanImage> planImageList = imageDTOList.stream(). map(planImageDTO ->{
                PlanImage planimage = PlanImage.builder()
                        .path(planImageDTO.getPath())
                        .imgName(planImageDTO.getImgName())
                        .uuid(planImageDTO.getUuid())
                        .plan(plan)
                        .build();
                return planimage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", planImageList);
        }
        return entityMap;
    }

    default PlanDTO entitiesToDTO(Plan plan, List<PlanImage> planImages, double avg, long reviewCnt) {
        PlanDTO planDTO = PlanDTO.builder()
                .id(plan.getPno())
                .title(plan.getTitle())
                .description(plan.getDescription())
                .location(plan.getLocation())
                .regDate(plan.getRegDate())
                .modDate(plan.getModDate())
                .build();

        List<PlanImageDTO> planImageDTOList = planImages.stream().map(planImage -> {
            System.out.println("이것이 null 이란 말이냐?" + planImage);
            if(planImage == null) return null;
            else return PlanImageDTO.builder()
                    .imgName(planImage.getImgName())
                    .path(planImage.getPath())
                    .uuid(planImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        planDTO.setImageDTOList(planImageDTOList);

        planDTO.setAvg(avg);
        planDTO.setReviewCnt(reviewCnt);

        return planDTO;
    }

    default PlanDTO entityToDTO(Plan plan) {

        PlanDTO planDTO = PlanDTO.builder()
                .id(plan.getPno())
                .title(plan.getTitle())
                .description(plan.getDescription())
                .location(plan.getLocation())
                .regDate(plan.getRegDate())
                .modDate(plan.getModDate())
                .build();

        return planDTO;
    }
}