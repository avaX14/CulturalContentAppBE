package com.kts.cultural_content.service.email;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import com.kts.cultural_content.constants.EmailConstants;
import com.kts.cultural_content.model.ConfirmationToken;
import com.kts.cultural_content.model.User;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailSenderServiceUnitTest {


    @Autowired
    private MailSenderService mailSenderService;

    @MockBean
    private JavaMailSender mailSender;

    @Before
    public void setUp(){

    }


    @Test
    public void whenSendValidMessage() throws InterruptedException, ExecutionException {
        User u = new User();
        ConfirmationToken ct = new ConfirmationToken();

        u.setEmail(EmailConstants.EXISTING_EMAIL);

        ct.setToken("118b0557-becf-4e5f-83e1-4496d7065256");
        ct.setUser(u);
        Future<SimpleMailMessage> messageFuture = mailSenderService.sendRegistrationMail(ct);

        String text = messageFuture.get().getText();
        String[] emails = messageFuture.get().getTo();

        //there is at least one receiver
        assertNotNull(emails);
        assertNotNull(emails[0]);
        String email = emails[0];

        //text contains token
        assertNotNull(text);
        assertTrue(email.contains(EmailConstants.EXISTING_EMAIL));
        assertTrue(text.contains("118b0557-becf-4e5f-83e1-4496d7065256"));

        Mockito.verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    public void whenSendToNullMail() throws ExecutionException, InterruptedException {
        User u = new User();
        ConfirmationToken ct = new ConfirmationToken();

        u.setEmail(null);
        ct.setToken("118b0557-becf-4e5f-83e1-4496d7065256");
        ct.setUser(u);

        Future<SimpleMailMessage> messageFuture = mailSenderService.sendRegistrationMail(ct);

        assertEquals("Error. Email cannot be null.", messageFuture.get().getText());

        Mockito.verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    public void whenSendNullToken() throws ExecutionException, InterruptedException {
        User u = new User();
        ConfirmationToken ct = new ConfirmationToken();

        u.setEmail(EmailConstants.EXISTING_EMAIL);
        ct.setToken(null);
        ct.setUser(u);

        Future<SimpleMailMessage> messageFuture = mailSenderService.sendRegistrationMail(ct);

        assertEquals("Error. Token cannot be null.", messageFuture.get().getText());

        Mockito.verify(mailSender, never()).send(any(SimpleMailMessage.class));

    }
}
