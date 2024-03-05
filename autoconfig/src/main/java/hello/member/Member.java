package hello.member;

import lombok.Data;

@Data
public class Member {

    private String memberId;
    private String name;

    protected Member() {

    }

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }
}
