package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;
    private String name;

    //일대다 양방향
    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<TestMember> testMembers = new ArrayList<>();

//    public List<Member> getMembers() {
//        return members;
//    }

//    public void setMembers(List<Member> members) {
//        this.members = members;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //연관관계 메서드//
//    public void addMember(Member member) {
//        member.setTeam(this);
//        members.add(member);
//    }
}
