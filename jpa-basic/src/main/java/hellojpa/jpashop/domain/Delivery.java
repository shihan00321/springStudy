package hellojpa.jpashop.domain;

import hellojpa.BaseEntity;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;
    @OneToOne(mappedBy = "delivery")
    private Order order;
}
