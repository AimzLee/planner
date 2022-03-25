package kr.co.team.planner.service;


import kr.co.team.planner.dto.QuestionDTO;
import kr.co.team.planner.entity.Question;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public interface QuestionService {
    default Question dtoToEntity(QuestionDTO dto){
        Question question = Question.builder()
                .qno(dto.getQno())
                .context(dto.getContext())
                .build();
        return question;
    }

    default QuestionDTO entityToDto(Question entity){
        QuestionDTO dto = QuestionDTO.builder()
                .qno(entity.getQno())
                .context(entity.getContext())
                .build();
        return dto;
    }

    public default Map<String, String> qvalidateHandling(Errors errors){
        Map<String, String> qvalidatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("qvalid_%s", error.getField());
            qvalidatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return qvalidatorResult;


    }
    public Question insertQuestionTableTest(QuestionDTO dto);
}
