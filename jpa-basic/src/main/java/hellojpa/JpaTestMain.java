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
            Member member = new Member();
            member.setUsername("c");
            System.out.println("============");
            em.persist(member);
            System.out.println("id : " + member.getId());
            System.out.println("============");
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
