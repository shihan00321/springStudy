package hellojpa.jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

            JpqlMember member = new JpqlMember();
            member.setUsername("hello");
            member.setAge(20);
            member.changeTeam(team);
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);

            List<JpqlMember> resultList = em.createQuery("select m from JpqlMember m where m.memberType = hellojpa.jpql.MemberType.ADMIN", JpqlMember.class)
                    .getResultList();
            System.out.println("resultList = " + resultList.size());

//            List<JpqlMemberDto> findMembers = em.createQuery("select new hellojpa.jpql.JpqlMemberDto(m.username, m.age) from JpqlMember m", JpqlMemberDto.class)
//                    .getResultList();
//            List<JpqlMember> findMembers = em.createQuery("select m from JpqlMember m where m.username = :username", JpqlMember.class)
//                    .setParameter("username", "hello")
//                    .getResultList();

//            List<JpqlMember> pagingResult = em.createQuery("select m from JpqlMember m order by m.age desc", JpqlMember.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();

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
