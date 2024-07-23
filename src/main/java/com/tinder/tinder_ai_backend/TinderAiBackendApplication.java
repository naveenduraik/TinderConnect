package com.tinder.tinder_ai_backend;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;

import com.tinder.tinder_ai_backend.conversations.ChatMessage;
import com.tinder.tinder_ai_backend.conversations.Conversation;
import com.tinder.tinder_ai_backend.conversations.ConversationRepository;
import com.tinder.tinder_ai_backend.profiles.Gender;
import com.tinder.tinder_ai_backend.profiles.Profile;
import com.tinder.tinder_ai_backend.profiles.ProfileRepository;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner{
	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args){
		Profile profile = new Profile("1", "NaveenDurai", 
		"Kanagasabai", 38, "Indian", 
		Gender.MALE, "Software programmer","foo.jpg", "INTP"
	
		);
	
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
			"1",profile.id(),
			List.of(
				new ChatMessage(
				"hello", 
				profile.id(),
				LocalDateTime.now()
				)
		));

		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out :: println);
	}

}
