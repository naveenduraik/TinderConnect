package com.tinder.tinder_ai_backend.conversations;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tinder.tinder_ai_backend.profiles.Profile;
import com.tinder.tinder_ai_backend.profiles.ProfileRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ConversationController {

    private ConversationRepository conversationRepository;
    private ProfileRepository profileRepository;

    public ConversationController(ConversationRepository conversationRepository,ProfileRepository profileRepository){
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/conversations")
    public Conversation createConversation(@RequestBody CreateConversationRequest request){

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
        return conversation;

    }

    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId,@RequestBody ChatMessage chatMessage){

        Conversation conversation = conversationRepository.findById(conversationId)
        .orElseThrow(()->
        new ResponseStatusException(
            HttpStatus.NOT_FOUND,"Unable to fetch a conversation with the ID "+conversationId
        )
        );

        
        profileRepository.findById(chatMessage.authorId())
        .orElseThrow(()->
        new ResponseStatusException(
            HttpStatus.NOT_FOUND,"Unable to fetch the profile ID with "+chatMessage.authorId()
        )
        );
        

        chatMessage.messageTime();
        ChatMessage messageWithTime = new ChatMessage(
            chatMessage.messageText(),
            chatMessage.authorId(),
            LocalDateTime.now()

            
        );
        conversation.messages().add(messageWithTime);
        conversationRepository.save(conversation);



        return conversation;

    }

    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@PathVariable String conversationId){

        return conversationRepository.findById(conversationId)
        .orElseThrow(()->
        new ResponseStatusException(
            HttpStatus.NOT_FOUND,"Unable to fetch a conversation with the ID "+conversationId
        )
        );

    }
    
    public record CreateConversationRequest(

        String profileId
    ){}
}
