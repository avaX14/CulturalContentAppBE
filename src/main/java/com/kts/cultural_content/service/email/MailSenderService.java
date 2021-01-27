package com.kts.cultural_content.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import com.kts.cultural_content.model.ConfirmationToken;

import java.util.concurrent.Future;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;


    @Async
    public Future<SimpleMailMessage> sendRegistrationMail(ConfirmationToken token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Verify you'r account");
        message.setFrom("CulturalContentApp");
        message.setTo(token.getUser().getEmail());

        message.setText("You can activate you'r account on this link http://localhost:4200/verify?token=" + token.getToken());


        // verifications
        if(token.getUser().getEmail() == null){
            message.setText("Error. Email cannot be null.");
            return new AsyncResult<>(message);
        }

        if(token.getToken() == null){
            message.setText("Error. Token cannot be null.");
            return new AsyncResult<>(message);
        }

        mailSender.send(message);
        return new AsyncResult<>(message);
    }
}
