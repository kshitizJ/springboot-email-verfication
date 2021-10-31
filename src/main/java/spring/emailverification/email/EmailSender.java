package spring.emailverification.email;

public interface EmailSender {
   void send(String to, String email);
}
