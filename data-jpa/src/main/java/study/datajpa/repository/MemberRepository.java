package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
    List<Member> findHelloBy();
    List<Member> findTop3HelloBy();

    //@Query(name = "Member.findByUsername") JpaRepository<Entity, pkType> 에서 선언된 entity로 Member.메소드명 네임드 쿼리를 우선순위를 먼저 실행하고 없다면 메소드명으로 쿼리를 생성하여 실행
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
