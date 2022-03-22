package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.MemberDTO;
import kijin.bang.keygenie.entity.Member;

public interface MemberService {

    Long register(MemberDTO memberDTO);

    void modify(MemberDTO memberDTO);

    Member dtoToEntity(MemberDTO memberDTO);
}
