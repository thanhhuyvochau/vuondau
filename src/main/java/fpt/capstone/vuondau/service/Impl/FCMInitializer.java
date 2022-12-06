package fpt.capstone.vuondau.service.Impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import fpt.capstone.vuondau.entity.request.PushNotificationRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class FCMInitializer {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    //    Logger logger = LoggerFactory.getLogger(FCMInitializer.class);
    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
//                logger.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}