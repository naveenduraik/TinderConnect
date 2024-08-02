package com.tinder.tinder_ai_backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.tinder.tinder_ai_backend.conversations.ConversationRepository;
import com.tinder.tinder_ai_backend.matches.MatchRepository;
import com.tinder.tinder_ai_backend.profiles.ProfileCreationService;
import com.tinder.tinder_ai_backend.profiles.ProfileRepository;

@SpringBootApplication
@ComponentScan(basePackages = { "com.tinder.tinder_ai_backend" , "com.tinder.tinder_ai_backend.profiles", "com.tinder.tinder_ai_backend.matches", "com.tinder.tinder_ai_backend.conversations"})
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private ProfileCreationService profileCreationService;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args) {
		clearAllData();
		profileCreationService.saveProfilesToDB();

	}

	private void clearAllData() {
		conversationRepository.deleteAll();
		matchRepository.deleteAll();
		profileRepository.deleteAll();
	}

}