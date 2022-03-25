package kr.co.team.planner.repository;

import kr.co.team.planner.entity.Board;
import kr.co.team.planner.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //글번호를 가지고 댓글을 삭제하는 메서드
    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno")
    public void deleteByBno(Long bno);

    //게시글에 해당하는 모든 댓글을 가져오는 메서드
    public List<Reply> getRepliesByBoardOrderByRno(Board board);

}
