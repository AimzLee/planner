package kr.co.team.planner.service;

import kr.co.team.planner.dto.ReplyDTO;
import kr.co.team.planner.entity.Board;
import kr.co.team.planner.entity.Reply;

import java.util.List;

public interface ReplyService {
    //데이터 삽입을 위한 메서드
    public Long register(ReplyDTO replyDTO);
    //데이터 수정을 위한 메서드
    public void modify(ReplyDTO replyDTO);
    //데이터 삭제를 위한 메서드
    public void remove(Long rno);
    //댓글 목록을 가져오기
    public List<ReplyDTO> getList(Long bno);

    //ReplyDTO 를 Reply Entity로 변환해주는 메서드
    default Reply dtoToEntity(ReplyDTO replyDTO){
        Board borad = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyWriter(replyDTO.getReplyer())
                .board(borad)
                .build();
        return reply;
    }

    //Reply Entity를 ReplyDTO로 변환해주는 메서드
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyWriter())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return replyDTO;
    }
}
