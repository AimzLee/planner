package kr.co.team.planner.service;


import kr.co.team.planner.dto.QuestionDTO;
import kr.co.team.planner.entity.Question;
import kr.co.team.planner.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuestionServiceImpl implements QuestionService{
    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public Question insertQuestionTableTest(QuestionDTO dto) {
        Question entity = dtoToEntity(dto);
        questionRepository.save(entity);
        dto.setQno(entity.getQno());
        return entity;
    }

}
