package com.kh.youtube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YoutubeApplication {

	public static void main(String[] args) {
		try {
		SpringApplication.run(YoutubeApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
