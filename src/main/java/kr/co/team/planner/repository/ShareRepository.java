package kr.co.team.planner.repository;


import kr.co.team.planner.entity.Plan;
import kr.co.team.planner.entity.Share;
import kr.co.team.planner.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.List;

public interface ShareRepository extends JpaRepository<Share,Long> {
    @Query("select p,max(c),count(distinct s) from Plan p " +
            "left outer join CheckList c on c.plan = p " +
            "left outer join Share s on s.plan = p group by p")
    Page<Object[]>getListPage(Pageable pageable);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Share> findByPlan(Plan plan);

    @Modifying
    @Query("delete from Share s where s.user=:user")
    void deleteByUser(User user);
}

