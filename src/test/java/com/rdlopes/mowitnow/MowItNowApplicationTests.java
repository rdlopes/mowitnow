package com.rdlopes.mowitnow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MowItNowApplicationTests {

    @Autowired
    private MowItNowApplication mowItNowApplication;

    @Test
    public void contextLoads() {
        assertThat(mowItNowApplication).isNotNull();
    }

}
