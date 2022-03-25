package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.BoardDTO;
import kijin.bang.keygenie.dto.PageRequestDTO;
import kijin.bang.keygenie.dto.PageResponseDTO;
import kijin.bang.keygenie.entity.Board;
import kijin.bang.keygenie.entity.Member;

public interface BoardService {

    //declare a method for submitting a content on BOARD
    //get BoardDTO type parameter and return 'bno'
    Long register(BoardDTO boardDTO);

    //게시물 목록 요청을 처리하기 위한 메서드 선언
    PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    //게시물 상세 보기를 처리하기 위한 메서드 선언
    public BoardDTO get(Long bno);

    //글 번호를 이용해서 게시글을 삭제하는 메서드를 선언
    void removeWithReplies(Long bno);

    //수정을 위한 메소드 선언
    void modify(BoardDTO boardDTO);


    //아래의 두 메소드는 class 에 private 형태로 만들어도 O.K.
    //ServiceImpl 에는 Business logic 에만 관련된 코드만을 담기위해!!!
    //이러한 메소드를 별도의 클래스에 static 메소드로 만들어 두어도 O.K.(이 경우, 클래스 이름에 Wrapper 를 붙이는 것을 권장!)

    //BoardDTO 를 BoardENTITY 로 변환해주는 메소드
    default Board dtoToEntity(BoardDTO boardDTO) {
        Member member = Member.builder().email(boardDTO.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(member)
                .build();
        return board;
    }

    //Board Entity 를 BoardDTO 클래스로 변환하는 메소드
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getNickname())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();
        return boardDTO;
    }

}