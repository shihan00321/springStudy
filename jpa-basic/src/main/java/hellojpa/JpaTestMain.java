package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaTestMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            em.persist(member);

            team.getMembers().add(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

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
