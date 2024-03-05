package hello.config;

import memory.Memory;
import memory.MemoryCondition;
import memory.MemoryController;
import memory.MemoryFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@Conditional(MemoryCondition.class) // 해당 클래스에서 matches 반환 값이 true면 빈 등록
//@ConditionalOnProperty(name = "memory", havingValue = "on") // 해당 property가 만족하면 실행
public class MemoryConfig {

  //  @Bean
  //  public MemoryController memoryController() {
  //      return new MemoryController(memoryFinder());
  //  }

  //  @Bean
  //  public MemoryFinder memoryFinder() {
  //      return new MemoryFinder();
  //  }
}