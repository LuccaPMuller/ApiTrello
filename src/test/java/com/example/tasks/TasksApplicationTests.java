package com.example.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("@SpringBootTest: TasksApplicationTests")
class TasksApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	@DisplayName("contextLoads")
	void contextLoads() {
	}

	@Test
	@DisplayName("verifyApplicationContext")
	void verifyApplicationContext() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	@DisplayName("verifyMainBeansAreLoaded")
	void verifyMainBeansAreLoaded() {
		assertThat(applicationContext.containsBean("taskController")).isTrue();
		assertThat(applicationContext.containsBean("taskService")).isTrue();
		assertThat(applicationContext.containsBean("boardController")).isTrue();
		assertThat(applicationContext.containsBean("taskGroupsController")).isTrue();
	}

	@Test
	@DisplayName("verifyApplicationStarts")
	void verifyApplicationStarts() {
		TasksApplication.main(new String[] {});
	}
}