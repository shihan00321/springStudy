package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTestMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            TestMember testMember = new TestMember();
            testMember.setUsername("user1");
            testMember.setHomeAddress(new TestAddress("city1", "street", "101"));

            testMember.getFavoriteFoods().add("치킨");
            testMember.getFavoriteFoods().add("피자");
            testMember.getFavoriteFoods().add("족발");
            testMember.getFavoriteFoods().add("삼겹살");

            testMember.getAddressHistory().add(new AddressEntity("cityHistory", "streetHistory", "103"));
            testMember.getAddressHistory().add(new AddressEntity("cityHistory", "streetHistory", "104"));
            testMember.getAddressHistory().add(new AddressEntity("cityHistory", "streetHistory", "105"));

            em.persist(testMember);
            
            em.flush();
            em.clear();

            TestMember findMember = em.find(TestMember.class, testMember.getId());
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void findMembers(EntityManager em) {
//        List<Member> result = em.createQuery("select m from Member m", Member.class)
//                .setFirstResult(0 )
//                .setMaxResults(5)
//                .getResultList();
//        for (Member member : result) {
//            System.out.println("member = " + member.getName());
//        }
    }

//    private static Member createMember() {
//        //비영속 객체 생성
//        Member member = new Member();
//        member.setId(2L);
//        member.setName("hello");
//        // em.persist -> 영속 객체
//        return member;
//    }
}
