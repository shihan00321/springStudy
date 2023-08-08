package hellojpa.jpql;

import javax.persistence.*;
import java.util.List;

@Entity
public class JpqlMember {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private JpqlTeam team;

    @OneToMany(mappedBy = "member")
    private List<JpqlOrder> orders;

    private MemberType memberType;

    public void changeTeam(JpqlTeam team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public JpqlTeam getTeam() {
        return team;
    }

    public void setTeam(JpqlTeam team) {
        this.team = team;
    }

    public List<JpqlOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<JpqlOrder> orders) {
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
