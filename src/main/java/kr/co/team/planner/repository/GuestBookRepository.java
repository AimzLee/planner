package kr.co.team.planner.repository;

import kr.co.team.planner.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> , QuerydslPredicateExecutor<GuestBook> {
}
