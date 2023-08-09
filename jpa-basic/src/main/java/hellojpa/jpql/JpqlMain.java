package hellojpa.jpql;

import hellojpa.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JpqlTeam team = new JpqlTeam();
            team.setName("teamA");
            em.persist(team);

            JpqlTeam team2 = new JpqlTeam();
            team2.setName("teamB");
            em.persist(team2);

            JpqlMember member = new JpqlMember();
            member.setUsername("hello");
            member.setTeam(team);
            em.persist(member);

            JpqlMember member2 = new JpqlMember();
            member2.setUsername("hello2");
            member2.setTeam(team);
            em.persist(member2);

            JpqlMember member3 = new JpqlMember();
            member3.setUsername("hello3");
            member3.setTeam(team2);
            em.persist(member3);

            em.flush();
            em.clear();

//            List<Collection> resultList = em.createQuery("select m from Team t join t.members m", Collection.class)
//                    .getResultList();

//            List<JpqlMember> resultList = em.createQuery("select distinct m from JpqlMember m join fetch m.team", JpqlMember.class)
//                    .getResultList();
            List<JpqlMember> resultList = em.createNamedQuery("JpqlMember.findByUserName", JpqlMember.class)
                    .setParameter("username", "hello2")
                    .getResultList();

            for (JpqlMember member1 : resultList) {
                System.out.println("member1.getId() = " + member1.getId());
            }
            tx.commit();

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
