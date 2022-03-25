package kr.co.team.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor
public class PageRequestDTO {
    //현재 페이지 번호
    private int page;
    //페이지 당 출력 개수
    private int size;

    //매개변수가 없는 생성자 - default constructor
    public PageRequestDTO(){
        page = 1;
        size = 10;
    }

    //page 와 size를 이용해서 Pageable 객체를 생성해서 리턴하는 메서드
    public Pageable getPageable(Sort sort){
        //페이지 번호는 -1을 해서 생성
        return PageRequest.of(page-1, size, sort);
    }

    private String type;
    private String keyword;

}