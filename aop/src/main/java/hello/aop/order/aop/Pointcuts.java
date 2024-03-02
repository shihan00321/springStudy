package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
        //order 패키지와 하위 패키징
        @Pointcut("execution(* hello.aop.order..*(..))")
        public void allOrder() {

        }

        //클래스 이름 패턴으로
        @Pointcut("execution(* *..*Service.*(..))")
        public void allService() {

        }

        @Pointcut("allOrder() && allService()")
        public void orderAndService() {}
}
