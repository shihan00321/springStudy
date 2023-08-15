package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void testMember() {
        Member member = new Member("member2");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @DisplayName("CRUD 검증")
    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        findMember1.setUsername("member!!!");

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        createMember();
        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("member2", 15);
        assertThat(members.get(0).getUsername()).isEqualTo("member2");
        assertThat(members.get(0).getAge()).isEqualTo(20);
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    void findHelloBy() {
        List<Member> byHelloBy = memberRepository.findTop3HelloBy();
    }

    @Test
    void namedQuery() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsername("member1");
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    void testQuery() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("member1", 10);
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    void findUsernameList() {
        createMember();
        List<String> result = memberRepository.findUsernameList();
        for (String username : result) {
            System.out.println(username);
        }
    }

    @Test
    void findMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        member1.setTeam(team);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    void findByNames() {
        createMember();
        List<Member> result = memberRepository.findByNames(Arrays.asList("member2", "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void returnType() {
        createMember();
        List<Member> member1 = memberRepository.findListByUsername("member2"); //결과 값이 없으면 null이 아닌 사이즈가 0인 컬렉션 반환
        Member member2 = memberRepository.findMemberByUsername("member2"); // 결과 값이 없으면 null 반환
        Optional<Member> member3 = memberRepository.findOptionalByUsername("member2");
    }

    private void createMember() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 20));
        memberRepository.save(new Member("member6", 10));
        memberRepository.save(new Member("member7", 10));
        memberRepository.save(new Member("member8", 10));
        memberRepository.save(new Member("member9", 20));
        memberRepository.save(new Member("member10", 10));

        int age = 20;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username")); //0부터 시작

        Page<Member> page = memberRepository.findByAge(age, pageRequest); //엔티티를 반환하면 X
        Page<MemberDto> memberDto = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null)); //DTO 변환

        //Slice<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        assertThat(totalElements).isEqualTo(4);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);
    }

    @Test
    void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 21));
        memberRepository.save(new Member("member4", 22));
        memberRepository.save(new Member("member5", 23));

        int resultCount = memberRepository.bulkAgePlus(21);
        //em.flush();
        //em.clear();

        Member member3 = memberRepository.findByUsername("member3").get(0);
        System.out.println("member3 = " + member3);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void findMemberLazy() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member1", 10, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        //Member 1, 결과 N -> N + 1
        //List<Member> members = memberRepository.findAll();

        //List<Member> members = memberRepository.findMemberFetchJoin();
        //List<Member> members = memberRepository.findAll();
        List<Member> members = memberRepository.findEntityGraphByUsername("member1");

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.team.class = " + member.getTeam().getClass());
            System.out.println("member.team.name = " + member.getTeam().getName());
        }
    }

    @Test
    void queryHint() {
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");

        em.flush();
    }

    @Test
    void lock() {
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        List<Member> result = memberRepository.findLockByUsername("member1");
    }

    @Test
    void callCustom() {
        List<Member> result = memberRepository.findMemberCustom();
    }

    @Test
    void specBasic() {
        Team team = new Team("teamA");
        em.persist(team);

        Member m1 = new Member("m1", 10, team);
        Member m2 = new Member("m2", 20, team);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

        Specification<Member> spec = MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
        List<Member> result = memberRepository.findAll(spec);

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void queryByExample() {
        Team team = new Team("teamA");
        em.persist(team);

        Member m1 = new Member("m1", 10, team);
        Member m2 = new Member("m2", 20, team);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

        Member member = new Member("m1");
        Team team1 = new Team("teamA");
        member.setTeam(team1);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age");
        Example<Member> example = Example.of(member, matcher);

        List<Member> result = memberRepository.findAll(example);
        assertThat(result.get(0).getUsername()).isEqualTo("m1");
    }

    @Test
    void projections() {
        Team team = new Team("teamA");
        em.persist(team);

        Member m1 = new Member("m1", 10, team);
        Member m2 = new Member("m2", 20, team);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

        //List<UsernameOnlyDto> result = memberRepository.findProjectionsByUsername("m1", UsernameOnlyDto.class);
        List<NestedClosedProjections> result = memberRepository.findProjectionsByUsername("m1", NestedClosedProjections.class);
        for (NestedClosedProjections nestedClosedProjections : result) {
            System.out.println("nestedClosedProjections = " + nestedClosedProjections.getUsername());
            System.out.println("nestedClosedProjections team = " + nestedClosedProjections.getTeam().getName());
        }
    }

    @Test
    void nativeQuery() {
        Team team = new Team("teamA");
        em.persist(team);

        Member m1 = new Member("m1", 10, team);
        Member m2 = new Member("m2", 20, team);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

//        Member result = memberRepository.findByNativeQuery("m1");
//        System.out.println("result = " + result);
        Page<MemberProjection> result = memberRepository.findByNativeProjection(PageRequest.of(0, 10));
        List<MemberProjection> content = result.getContent();
        for (MemberProjection memberProjection : content) {
            System.out.println("memberProjection userName = " + memberProjection.getUsername());
            System.out.println("memberProjection teamName = " + memberProjection.getTeamName());
        }
    }
}