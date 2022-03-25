package kr.co.team.planner.service;

import kr.co.team.planner.dto.BoardDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.entity.Board;
import kr.co.team.planner.entity.Member;
import kr.co.team.planner.repository.BoardRepository;
import kr.co.team.planner.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        //등록을 위해서 Entity 객체로 변환
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        //Repository 메서드를 호출해서 결과 가져오기
        /*
        Page<Object []> result = boardRepository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending()));
         */
        Page<Object []> result = boardRepository.searchPage(
                pageRequestDTO.getType(), pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );
        Function<Object[], BoardDTO> fn = (
                en -> entityToDTO((Board)en[0],
                        (Member)en[1],
                        (Long)en[2]));
        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result= boardRepository.getBoardByBno(bno);
        Object [] ar = (Object []) result;
        return entityToDTO((Board)ar[0], (Member)ar[1], (Long)ar[2]);
    }

    @Override
    @Transactional
    public void removeWithReplies(Long bno) {
        //댓글 부터 삭제
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        //데이터를 조회해서 있으면 수정
        Optional<Board> board =
                boardRepository.findById(boardDTO.getBno());
        if(board.isPresent()) {
            board.get().changeTitle(boardDTO.getTitle());
            board.get().changeContent(boardDTO.getContent());

            boardRepository.save(board.get());
        }
    }
}