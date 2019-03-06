package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class HelloClientApplication {
	@Autowired
	HelloClient client;

	@RequestMapping("/")
	public String hello() {
		return client.hello();
	}

	@RequestMapping("/phone")
	public String helloPhone() {
		return client.helloPhone();
	}

	@RequestMapping("/phoneplusencoded")
	public String helloPhonePlusEncoded() {
		return client.helloPhonePlusEncoded();
	}

	@RequestMapping("/phone/{number}")
	public String helloPhone(@PathVariable String number) {
		return client.helloPhoneNumber(number);
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloClientApplication.class, args);
	}

	@FeignClient("HelloServer")
	interface HelloClient {
		@RequestMapping(value = "/", method = GET)
		String hello();

		@RequestMapping(value = "/phone/+123", method = GET)
		String helloPhone();

		@RequestMapping(value = "/phone/%2B123", method = GET)
		String helloPhonePlusEncoded();

		@RequestMapping(value = "/phone/{number}", method = GET)
		String helloPhoneNumber(@PathVariable("number") String number);
	}
}
