package hello.proxy.pureproxy.concreateproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteLogic {
    public String operatioan() {
        log.info("ConcreateLogic 실행");
        return "data";
    }
}
