package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {
    @Test
    void reflection0() {
        Hello target = new Hello();
        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA(); // 전체 코드 흐름이 완전히 같지만 호출하는 메서드가 다름
        log.info("result={}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB(); //
        log.info("result={}", result2);
        //공통 로직1 종료
    }

    @Test
    void reflection1() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello"); // 클래스 정보
        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA"); //callA 메서드 정보
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        Method methodCallB = classHello.getMethod("callB"); //callA 메서드 정보
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello"); // 클래스 정보
        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA"); //callA 메서드 정보
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB"); //callA 메서드 정보
        methodCallB.invoke(target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target); //
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
