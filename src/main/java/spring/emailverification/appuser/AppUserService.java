package spring.emailverification.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import spring.emailverification.registration.token.ConfirmationToken;
import spring.emailverification.registration.token.ConfirmationTokenService;

/**
 * 
 * @Service: Spring @Service annotation is used with classes that provide some
 *           business functionalities. Spring context will autodetect these
 *           classes when annotation-based configuration and classpath scanning
 *           is used.
 * 
 * 
 * 
 */

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

   private final static String USER_NOT_FOUND_MSG = "User with email %s not found!";
   private final AppUserRepository appUserRepository;
   private final BCryptPasswordEncoder bCryptPasswordEncoder;
   private final ConfirmationTokenService confirmationTokenService;

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      return appUserRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
   }

   public String signUpUser(AppUser appUser) {

      // If email exist
      boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
      if (userExist) {
         // TODO: if email not confirmed send confirmation email again.
         throw new IllegalStateException("Email already exist!!");
      }

      // if email doesnot exist then encode the password first
      String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
      appUser.setPassword(encodedPassword);

      appUserRepository.save(appUser);

      // new class of ConfirmationToken to create a token using UUID class
      String token = UUID.randomUUID().toString();
      ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15), appUser);

      confirmationTokenService.saveConfirmationToken(confirmationToken);

      return token;
   }

   public int enableAppUser(String email) {
      return appUserRepository.enableAppUser(email);
   }

}
