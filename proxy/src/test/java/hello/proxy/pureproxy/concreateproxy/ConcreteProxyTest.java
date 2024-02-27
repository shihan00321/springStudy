package hello.proxy.pureproxy.concreateproxy;

import hello.proxy.pureproxy.concreateproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreateproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreateproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {
    @Test
    void noProxy() {
        ConcreteLogic concreateLogic = new ConcreteLogic();
        ConcreteClient concreateClient = new ConcreteClient(concreateLogic);
        concreateClient.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreateLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreateLogic);
        ConcreteClient concreateClient = new ConcreteClient(timeProxy);
        concreateClient.execute();
    }
}
