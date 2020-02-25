package com.example.centralizedconfigurationclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@SpringBootApplication
public class CentralizedConfigurationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralizedConfigurationClientApplication.class, args);
	}
    @Value("${spring.cloud.config.uri}")
    String uri;

	@Bean
	public  ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource
				= new ReloadableResourceBundleMessageSource();

		//messageSource.setBasename("classpath:messages");
		//messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:messages",uri+"/messages");
//		messageSource.setCacheSeconds(5);
		return messageSource;
	}
}

@RefreshScope
@RestController
class MessageRestController {

    @Autowired
    MessageSource messageSource;

	@Value("${message:Hello default}")
	private String message;

	@RequestMapping("/message")
	String getMessage() {
		//return this.message;

        String mm = messageSource.getMessage(
                "email.notempty",
                new String[]{"первый", "Второй-lastDateInPreviousMonth", "concatenateAutopaymentsName"},
                Locale.getDefault());
        return mm;
	}
}
