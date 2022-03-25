package kr.co.team.planner.repository;
//초기화
import kr.co.team.planner.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query(value="select p, w, count(c), count(s) from Plan p left join p.writer w " +
            "left join Share s on s.plan = p " +
            "left join CheckList c on c.plan = p group by p",
            countQuery = "select count(p) from Plan p")
    Page<Object[]> getPlanWithCheckListCount(Pageable pageable);

    @Query("select p, w, count(c), count(s) " +
            "from Plan p left join p.writer w left outer join CheckList c on c.plan = p " +
            "left outer join Share s on s.plan = p " +
            "where p.pno = :pno")
    List<Object[]> getPlanWithAll(Long pno);
}
