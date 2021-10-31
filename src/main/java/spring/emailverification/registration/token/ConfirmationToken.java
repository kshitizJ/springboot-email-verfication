package spring.emailverification.registration.token;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.emailverification.appuser.AppUser;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

   @Id
   @SequenceGenerator(name = "confirmation_token_sequence", sequenceName = "confirmation_token_sequence", allocationSize = 1)
   @GeneratedValue(strategy = SEQUENCE, generator = "confirmation_token_sequence")
   private Long id;

   @Column(nullable = false)
   private String token;

   @Column(nullable = false)
   private LocalDateTime createdAt;

   @Column(nullable = false)
   private LocalDateTime expiresAt;
   
   private LocalDateTime confirmedAt;

   @ManyToOne
   @JoinColumn(nullable = false, name = "app_user_id")
   private AppUser appUser;

   public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, AppUser appUser) {
      this.token = token;
      this.createdAt = createdAt;
      this.expiresAt = expiresAt;
      this.appUser = appUser;
   }

}
