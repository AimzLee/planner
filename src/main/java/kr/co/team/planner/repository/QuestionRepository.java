package kr.co.team.planner.repository;


import kr.co.team.planner.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query(value = "select q.qno from Question q where q.context =:context")
    List<Long> findByContext(@Param("context")String context);

    @Query(value = "select q.context from Question q where q.qno =:qno")
    String getContextForUserInfo(@Param("qno")Long qno);



}
