//package kijin.bang.keygenie.dto;
//
//import kijin.bang.keygenie.entity.Member;
//import kijin.bang.keygenie.entity.MemberRole;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//public class OAuthAttributes {
//    private Map<String, Object> attributes; // OAuth2 반환하는 회원 정보 Map
//    private String nameAttributeKey;
//    private String email;
//    private String nickname;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email, String nickname) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.email = email;
//        this.nickname = nickname;
//    }
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        return ofGoogle(userNameAttributeName, attributes);
//    }
//    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .email((String) attributes.get("email"))
//                .nickname((String) attributes.get("nickname"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public Member toEntity() {
//        Member member = Member.builder()
//                .email(email)
//                .nickname(nickname)
//                .fromSocial(true)
//                .build();
//        member.addMemberRole(MemberRole.GUEST);
//        return member;
//    }
//}
