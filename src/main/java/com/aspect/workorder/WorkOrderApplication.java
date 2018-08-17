package com.aspect.workorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.aspect.workorder" })
public class WorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOrderApplication.class, args);
	}
}
