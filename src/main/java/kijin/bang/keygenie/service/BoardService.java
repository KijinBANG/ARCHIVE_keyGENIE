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

}