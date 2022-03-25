package kr.co.team.planner.service;

import com.querydsl.core.BooleanBuilder;
import kr.co.team.planner.dto.GuestBookDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.entity.GuestBook;
import kr.co.team.planner.entity.QGuestBook;
import kr.co.team.planner.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor//inject dependency automatically
public class GuestBookServiceImpl implements GuestBookService {
    //to be injected automatically, it should be declared by 'final'
    private final GuestBookRepository guestBookRepository;

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;

        if(type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        //t c w 는 검색 화면에서 select 의 option 들의 value 가 되어야 함!
        if(type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        //'else if' 가 아님에 주의!
        if(type.contains("c")){
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }
        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

    @Override
    public Long register(GuestBookDTO dto) {
        log.info(dto);
        //dto 를 entity 로 변환
        GuestBook entity = dtoToEntity(dto);
        log.info(entity);
        //insert data
        guestBookRepository.save(entity);
        //return gno of inserted data
        return entity.getGno();
    }

    @Override
    public PageResponseDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        //Pageable 객체 생성
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        //결과를 가져오기 -> 아래와 같이 변경
        //Page<GuestBook> result = repository.findAll(pageable);
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<GuestBook> result = guestBookRepository.findAll(booleanBuilder, pageable);
        //Function 생성
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDTO(entity));
        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> guestBook = guestBookRepository.findById(gno);
        return guestBook.isPresent() ? entityToDTO(guestBook.get()) : null;
    }

    @Override
    public void modify(GuestBookDTO dto) {
        //수정할 데이터를 찾아오기
        Optional<GuestBook> result = guestBookRepository.findById(dto.getGno());
        if(result.isPresent()) {
            GuestBook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            guestBookRepository.save(entity);
            //method for debugging
            System.out.println(entity);
        }
    }

    @Override
    public void delete(Long gno) {
        //그냥 삭제해도 되지만, 일단 삭제할 데이터를 찾아오는 것이 좋다.
        //∵여럿이 사용하는 웹페이지의 경우, 작업 전 항상 데이터를 찾아오는 습관을 들이자!
        //삭제하려고 할 때 이미 삭제되었을 가능성이 있다!
        Optional<GuestBook> result = guestBookRepository.findById(gno);
        if(result.isPresent()) guestBookRepository.deleteById(gno);
    }

}