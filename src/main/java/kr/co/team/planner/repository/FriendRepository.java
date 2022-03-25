package kr.co.team.planner.repository;

import kr.co.team.planner.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT f FROM User u LEFT JOIN Friend f ON f.response = u WHERE u.code= :code")
    List<Object[]> getFriendByUser(@Param("code") Long code);

    //임시 (친구요청 리스트 상세보기)
    @Query(value="select f, rq, rp from Friend f left join f.request rq left join f.response rp")
    Page<Object[]> getSendWithRequest(Pageable pageable);

}
