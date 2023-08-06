package hellojpa;

import hellojpa.jpashop.domain.Member;

import javax.persistence.*;

//@Entity
public class MemberProduct {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEST_MEMBER_ID")
    private TestMember testMember;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
