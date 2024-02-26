package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {
    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();
        //log.info("비즈니스 로직1 실행");
        strategy.call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
