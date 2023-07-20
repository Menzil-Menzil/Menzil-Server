package seoultech.capstone.menjil.domain.chat.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import seoultech.capstone.menjil.domain.chat.application.ChatGptService;
import seoultech.capstone.menjil.domain.chat.application.MessageService;
import seoultech.capstone.menjil.domain.chat.domain.MessageType;
import seoultech.capstone.menjil.domain.chat.dto.Message;
import seoultech.capstone.menjil.domain.chat.dto.MessageDto;
import seoultech.capstone.menjil.global.exception.CustomException;
import seoultech.capstone.menjil.global.exception.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;
    private final ChatGptService chatGptService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/room/{roomId}") // 실제론 메세지 매핑으로 pub/chat/room/{roomId} 임
//    @SendTo("/queue/chat/{roomId}")
    public void enter(@DestinationVariable("roomId") String roomId,
                      MessageDto messageDto) {

        if (MessageType.QUESTION.equals(messageDto.getMessageType())) {
            // 질문인 경우
            // 1. 채팅 메시지 저장
            boolean b = messageService.saveChatMessage(messageDto);
            if (!b) {
                throw new CustomException(ErrorCode.SERVER_ERROR);
            }

            // 2. ChatGPT에게 질문 데이터 전달하여 세줄 요약 Message Dto를 받아온다.
            Message message = chatGptService.sendRequestToGpt(messageDto.getMessage());


            // 3.

        }


        // /queue/chat/room/{room id}로 메세지 보냄
        simpMessagingTemplate.convertAndSend("/queue/chat/room/" + messageDto.getRoomId(), messageDto);
    }

}
