package com.ime.lockmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ComponentScan
@SpringBootTest
class LockmanagerApplicationTests {

	@Test
	void contextLoads() {
	}

}
