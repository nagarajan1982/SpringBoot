package com.bskyb.internettv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommanLineRunner implements CommandLineRunner {

	@Autowired
	public UserRepository userRepository;

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println(arg0.toString());
		userRepository.save(new User("Nagarajan", "Shanmugam", "nagarajan1982",
				"dracula82", "admin,ACTUATOR"));
		userRepository.save(new User("Mathangi", "Nagarajan", "mathangi1985",
				"dracula82", "admin"));
		userRepository.save(new User("Sherwin", "Nagarajan", "sherwin2012",
				"dracula82", "ACTUATOR"));
	}

}
