package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 상품주문() {
        Member member = createMember();
        Item book = createBook(10000, 10, "spring");

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER); //상품 주문 시 상태 ORDER
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1); //주문한 상품 종류 수 = 1
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount); //주문 가격은 가격 * 수량
        assertThat(book.getStockQuantity()).isEqualTo(8); //주문 수량만큼 재고가 줄어야 함

    }

    @Test
    void 주문취소() {
        Member member = createMember();
        Book book = createBook(10000, 10, "spring");
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    @Test
    void 상품주문_재고수량초과() {
        Member member = createMember();
        Book book = createBook(10000, 10, "spring");

        int orderCount = 13;
        assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount)).isInstanceOf(NotEnoughStockException.class);

    }

    private Book createBook(int orderPrice, int orderStockQuantity, String orderName) {
        Book book = new Book();
        book.setName(orderName);
        book.setPrice(orderPrice);
        book.setStockQuantity(orderStockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "한강", "12-12"));
        em.persist(member);
        return member;
    }
}