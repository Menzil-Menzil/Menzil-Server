package seoultech.capstone.menjil.domain.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seoultech.capstone.menjil.domain.chat.domain.ChatMessage;
import seoultech.capstone.menjil.domain.chat.domain.MessageType;
import seoultech.capstone.menjil.domain.chat.domain.SenderType;
import seoultech.capstone.menjil.domain.chat.dto.request.MessageRequest;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageResponse {
    /**
     * 기존의 채팅 내역이 존재하는 경우, 채팅 내역들을 클라이언트로 보낼 때 사용하는 Response DTO
     */

    @JsonIgnore // ignore id
    private String _id;
    private Integer order;

    @JsonIgnore // ignore room id
    private String roomId;
    private SenderType senderType;
    private String senderNickname;
    private String message;
    private Object messageList;
    private MessageType messageType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime time;

    @Builder
    private MessageResponse(String _id, Integer order, String roomId, SenderType senderType,
                            String senderNickname, String message, Object messageList,
                            MessageType messageType, LocalDateTime time) {
        this._id = _id;
        this.order = order;
        this.roomId = roomId;
        this.senderType = senderType;
        this.senderNickname = senderNickname;
        this.message = message;
        this.messageList = messageList;
        this.messageType = messageType;
        this.time = time;
    }

    public static MessageResponse fromChatMessageEntity(ChatMessage chatMessage, Integer order) {
        return MessageResponse.builder()
                ._id(chatMessage.get_id())
                .order(order)
                .roomId(chatMessage.getRoomId())
                .senderType(chatMessage.getSenderType())
                .senderNickname(chatMessage.getSenderNickname())
                .message(chatMessage.getMessage())
                .messageList(chatMessage.getMessageList())
                .messageType(chatMessage.getMessageType())
                .time(chatMessage.getTime())
                .build();
    }

}
