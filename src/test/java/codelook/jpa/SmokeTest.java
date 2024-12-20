package codelook.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import codelook.jpa.view.MainViewController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private MainViewController mainViewController;

    @Test
    void contextLoads() throws Exception {
        assertThat(mainViewController).isNotNull();
    }
}
