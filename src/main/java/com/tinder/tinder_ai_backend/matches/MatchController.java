package com.tinder.tinder_ai_backend.matches;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tinder.tinder_ai_backend.conversations.Conversation;
import com.tinder.tinder_ai_backend.conversations.ConversationRepository;
import com.tinder.tinder_ai_backend.conversations.ConversationController.CreateConversationRequest;
import com.tinder.tinder_ai_backend.profiles.Profile;
import com.tinder.tinder_ai_backend.profiles.ProfileRepository;

@RestController
public class MatchController {

    

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final MatchRepository matchRepository;

    public record CreateMatchRequest(String profileId){}

    public MatchController(ConversationRepository conversationRepository, ProfileRepository profileRepository, MatchRepository matchRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.matchRepository = matchRepository;
 }
      @PostMapping("/conversations")
    public Match createNewMatch (@RequestBody CreateMatchRequest request){

         profileRepository.findById(request.profileId())
            .orElseThrow(()->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Unable to a profile with the ID" + request.profileId()
                )
            );
		Conversation conversation = new Conversation(
			UUID.randomUUID().toString(),
            request.profileId(),
            new ArrayList<>()

		);

        conversationRepository.save(conversation);
        Match match = new Match(
            UUID.randomUUID().toString(),
            profileRepository.findById(request.profileId()).get(),
            conversation.id());

		matchRepository.save(match);
        return match;

    }

}
