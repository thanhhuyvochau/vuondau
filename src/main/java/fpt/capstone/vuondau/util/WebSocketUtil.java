package fpt.capstone.vuondau.util;


import fpt.capstone.vuondau.entity.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class WebSocketUtil {

  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public WebSocketUtil(SimpMessagingTemplate messagingTemplate){
    this.messagingTemplate = messagingTemplate;
  }

  public void sendMessage(final String message){
    ResponseMessage res = new ResponseMessage(message);
    messagingTemplate.convertAndSend("/topic/message",res);
  }
  public void sendPrivateMessage(final String message, final String id){
    ResponseMessage res = new ResponseMessage(message);
    messagingTemplate.convertAndSendToUser(id,"/queue/private-message", res);
   }
   public void sendNotification(final String message) {
     ResponseMessage res = new ResponseMessage(message);
     messagingTemplate.convertAndSend("/topic/global-notification",res);
   }
  public void sendPrivateNotification(final String message,final String id) {
    ResponseMessage res = new ResponseMessage(message);
    messagingTemplate.convertAndSendToUser(id,"/queue/private-notification",res);
  }

}
