package hellojpa.jpql;

import javax.persistence.*;

@Entity
public class JpqlOrder {
    @Id @GeneratedValue
    private Long id;
    private int orderAmount;
    @Embedded
    private JpqlAddress address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private JpqlMember member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private JpqlProduct product;

    public JpqlProduct getProduct() {
        return product;
    }

    public void setProduct(JpqlProduct product) {
        this.product = product;
    }

    public JpqlMember getMember() {
        return member;
    }

    public void setMember(JpqlMember member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public JpqlAddress getAddress() {
        return address;
    }

    public void setAddress(JpqlAddress address) {
        this.address = address;
    }
}
