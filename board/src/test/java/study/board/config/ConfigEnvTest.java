package study.board.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ConfigEnvTest {

    @Value("${spring.config.activate.on-profile}")
    String envValue;

    @Test
    @DisplayName("환경 변수 확인")
    void configEnvTest() {
        assertThat(envValue).isEqualTo("local");
    }
}
