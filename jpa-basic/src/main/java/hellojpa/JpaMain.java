package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //Member findMember = em.find(Member.class, 1L);
            //findMember.setName("hello2");
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(0 )
                    .setMaxResults(5)
                    .getResultList();
            for (Member member : result) {
                System.out.println("member = " + member.getName());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static Member createMember() {
        Member member = new Member();
        member.setId(2L);
        member.setName("hello");
        return member;
    }
}
