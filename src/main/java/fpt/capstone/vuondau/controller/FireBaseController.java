package fpt.capstone.vuondau.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import fpt.capstone.vuondau.entity.request.PushNotificationRequest;
import fpt.capstone.vuondau.entity.response.PushNotificationResponse;
import fpt.capstone.vuondau.service.Impl.FCMInitializer;
import fpt.capstone.vuondau.service.Impl.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/firebase")
public class FireBaseController {

    private PushNotificationService pushNotificationService;

    public FireBaseController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }


}
