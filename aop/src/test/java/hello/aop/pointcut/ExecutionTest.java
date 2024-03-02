package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMatch() {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        /**
         * 접근제어자? : public
         * 반환타입 : String
         * 선업타입? : hello.aop.member.MemberServiceImpl
         * 메서드이름 : hello
         * 파라미터 : (String)
         * 예외? : 생략
         */
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        //반환타입*, 메서드이름*, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        //반환타입*, 메서드이름 hello, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        //반환타입*, 메서드이름 hel*, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *el*(..))");
        //반환타입*, 메서드이름 *el*, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        //반환타입*, 메서드이름 nono, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        //반환타입*, 선업타입? : hello.aop.member.MemberServiceImpl,
        //메서드이름 hello, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        //반환타입*, 선업타입? : hello.aop.member.*,
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        pointcut.setExpression("execution(* hello.aop.*.*(..))"); // hello.aop 패키지에 정확이 있어야 함
        //반환타입*, 선업타입? : hello.aop.*,
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))"); // hello.aop.member와 그 하위 패키지
        //반환타입*, 선업타입? : hello.aop.member..*,
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* hello.aop..*.*(..))"); // hello.aop와 그 하위 패키지
        //반환타입*, 선업타입? : hello.aop..*,
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))"); // hello.aop와 그 하위 패키지
        //반환타입*, 선업타입? : hello.aop.member.MemberServiceImpl,
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))"); // hello.aop와 그 하위 패키지
        //반환타입*, 선업타입? : hello.aop.member.MemberService - 부모 타입이어도 가능,
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))"); // hello.aop와 그 하위 패키지
        //반환타입*, 선업타입? : hello.aop.member.MemberServiceImpl
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))"); // hello.aop와 그 하위 패키지
        //반환타입*, 선업타입? : hello.aop.member.MemberService - 부모 타입의 인터널은 매칭이 안됨,
        //메서드이름 *, 파라미터(..) - 타입과 파라미터 수가 상관없다는 뜻으로 ('0..*')와 같음, 나머지 생략
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void argsMatch(){
        pointcut.setExpression("execution(* *(String))"); // 모든 패키지
        //반환타입*, 메서드이름 *, 파라미터(String) - 파라미터 타입 String, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchNoArgs(){
        pointcut.setExpression("execution(* *())"); // 모든 패키지
        //반환타입*, 메서드이름 *, 파라미터 없음 - 파라미터 x, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void argsMatchStar(){
        pointcut.setExpression("execution(* *(*))"); // 모든 패키지
        //반환타입*, 메서드이름 *, 파라미터 없음 - 파라미터 타입 * - 하나의 파라미터 모든 타입 허용, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchAll(){
        pointcut.setExpression("execution(* *(..))"); // 모든 패키지
        //반환타입*, 메서드이름 *, 파라미터 숫자와 무관하게 모든 파라미터, 모든 타입 허용, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchComplex(){
        pointcut.setExpression("execution(* *(String, ..))"); // 모든 패키지
        //반환타입*, 메서드이름 *, 파라미터 첫 시작은 String 타입, 그 뒤는 숫자와 무관하게 모든 파라미터, 모든 타입 허용, 나머지 생략
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
