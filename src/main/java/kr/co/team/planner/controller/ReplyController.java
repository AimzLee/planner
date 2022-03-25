package kr.co.team.planner.controller;

import kr.co.team.planner.dto.ReplyDTO;
import kr.co.team.planner.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies/")//공통주소
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;//자동주입을 위해 final

    //댓글 요청을 처리하는 method
    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    //Path를 이용하여 받은 data 를 전달(param 으로 받았을 때는 requestParam)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno ){
        log.info("bno: " + bno);
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);//HttpStatus.OK 는 잘 받았다는 '상태'를 담고 있음!
    }

    //댓글 작성 요청을 처리하는 메소드
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {
        Long rno = replyService.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    //댓글 수정 요청을 처리하는 메소드
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {
        replyService.modify(replyDTO);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    //댓글 삭제요청을 처리하기 위한 메소드
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("RNO:" + rno );
        replyService.remove(rno);
        return new ResponseEntity("success", HttpStatus.OK);
    }
}