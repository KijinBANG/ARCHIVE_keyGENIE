package kijin.bang.keygenie.repository;

import kijin.bang.keygenie.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    //method for Testing
    Board search();
    //목록 보기를 위한 메소드
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
