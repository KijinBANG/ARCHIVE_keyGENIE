package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.BoardDTO;
import kijin.bang.keygenie.dto.PageRequestDTO;
import kijin.bang.keygenie.dto.PageResponseDTO;
import kijin.bang.keygenie.entity.Board;
import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.repository.BoardRepository;
import kijin.bang.keygenie.repository.MemberRepository;
import kijin.bang.keygenie.repository.ReplyRepository;
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
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public Long register(BoardDTO boardDTO) {
        //등록을 위해서 Entity 객체로 변환
        Board board = dtoToEntity(boardDTO);
        log.info("board: " + board);
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

    //아래의 두 메소드는 class 에 private 형태로 만들어도 O.K.
    //ServiceImpl 에는 Business logic 에만 관련된 코드만을 담기위해!!!
    //이러한 메소드를 별도의 클래스에 static 메소드로 만들어 두어도 O.K.(이 경우, 클래스 이름에 Wrapper 를 붙이는 것을 권장!)

    //BoardDTO 를 BoardENTITY 로 변환해주는 메소드
    Board dtoToEntity(BoardDTO boardDTO) {
        String email = boardDTO.getWriterEmail();
        Long mno = memberRepository.findByEmail(email).get().getMno();
        Member member = Member.builder().mno(mno).build();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(member)
                .build();
        return board;
    }

    //Board Entity 를 BoardDTO 클래스로 변환하는 메소드
    BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerNickname(member.getNickname())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();
        return boardDTO;
    }

}
