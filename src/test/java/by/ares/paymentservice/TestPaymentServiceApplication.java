package by.ares.paymentservice;

import by.ares.paymentservice.config.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PaymentServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
