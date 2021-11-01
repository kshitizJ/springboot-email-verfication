package spring.emailverification.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

   private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

   private final JavaMailSender mailSender;

   /**
    * 
    * 
    * @Async: Spring @Async annotation allows us to create asynchronous methods in
    *         spring. For a brief, when we annotate a method of a bean @Async
    *         annotation, Spring will execute it in a separate thread and the
    *         caller of the method will not wait till the method is completed
    *         execution.
    *
    *
    */
   @Override
   @Async
   public void send(String to, String email) {
      try {
         MimeMessage mimeMessage = mailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
         helper.setText(email, true);
         helper.setTo(to);
         helper.setSubject("Confirm Your Email Address!");
         helper.setFrom("7865432198a@gmail.com");
         mailSender.send(mimeMessage);
      } catch (MessagingException e) {
         LOGGER.error("failder to send email", e);
         throw new IllegalStateException("failed to send email");
      }
   }

}
