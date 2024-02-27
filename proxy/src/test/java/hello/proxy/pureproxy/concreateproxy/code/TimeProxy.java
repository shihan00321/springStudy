package hello.proxy.pureproxy.concreateproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {
    private ConcreteLogic concreateLogic;

    public TimeProxy(ConcreteLogic concreateLogic) {
        this.concreateLogic = concreateLogic;
    }

    @Override
    public String operatioan() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();
        String result = concreateLogic.operatioan();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime={}ms", resultTime);
        return result;
    }

}
