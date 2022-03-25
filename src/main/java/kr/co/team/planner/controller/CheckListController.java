package kr.co.team.planner.controller;

import kr.co.team.planner.dto.CheckListDTO;
import kr.co.team.planner.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Check;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checklist")
@RequiredArgsConstructor
@Log4j2
public class CheckListController {
    private final CheckListService checkListService;

    @GetMapping("/{pno}/list")
    public ResponseEntity<List<CheckListDTO>> list(@PathVariable("pno") Long pno){
        List<CheckListDTO> checkListDTOList = checkListService.getList(pno);
        return new ResponseEntity<>(checkListDTOList, HttpStatus.OK);
    }

    @PostMapping("/{pno}")//'후기 작성을 위한 모달창'으로 부터 전달받은 데이터를 DB로 보내는 method
    public ResponseEntity<Long> addCheckList(@RequestBody CheckListDTO checkListDTO){
        log.info("----------------------add MovieReview------------------------");
        log.info("reviewDTO: " + checkListDTO);
        Long cno = checkListService.register(checkListDTO);
        log.info("reviewnum: " + cno);
        return new ResponseEntity<>(cno, HttpStatus.OK);
    }

    @PutMapping("/{pno}/{cno}")
    public ResponseEntity<Long> modifyCheckList(@PathVariable Long cno, @RequestBody CheckListDTO checkListDTO){
        log.info("------------------------modify MovieReview-----------------------" + cno);
        log.info("reviewDTO: " + checkListDTO);
        checkListService.modify(checkListDTO);
        return new ResponseEntity<>(cno, HttpStatus.OK);
    }

    @DeleteMapping("/{pno}/{cno}")
    public ResponseEntity<Long> removeCheckList( @PathVariable Long cno){
        log.info("-----------------modify removeReview-----------------------");
        log.info("reviewnum: " + cno);
        checkListService.remove(cno);
        return new ResponseEntity<>(cno, HttpStatus.OK);
    }

}
