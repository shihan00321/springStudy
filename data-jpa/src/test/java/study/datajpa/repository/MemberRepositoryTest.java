package study.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

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
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

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
}