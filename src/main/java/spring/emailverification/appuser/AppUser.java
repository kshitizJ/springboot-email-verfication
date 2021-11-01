package spring.emailverification.appuser;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * AppUser class contains the details of the user.
 * 
 * @EqualsAndHashCode: When we declare a class with @EqualsAndHashCode, Lombok
 *                     generates implementations for the equals and hashCode
 *                     methods.
 * 
 * @Id: Every JPA entity is required to have a field which maps to primary key
 *      of the database table. Such field must be annotated with @Id.
 * 
 * 
 * @SequenceGenerator: The @SequenceGenerator annotation defines a primary key
 *                     generator that may be referenced by name when a generator
 *                     element is specified for the GeneratedValue annotation.A
 *                     sequence generator may be specified on the entity class
 *                     or on the primary key field or property.
 * 
 *                     Argument:
 * 
 *                     1. name (Required): A unique generator name that can be
 *                     referenced by one or more classes to be the generator for
 *                     primary key values.
 * 
 *                     2. sequenceName (Optional): The name of the database
 *                     sequence object from which to obtain primary key values.
 * 
 *                     3. initialValue (Optional): The value from which the
 *                     sequence object is to start generating.
 * 
 *                     4. allocationSize (Optional): The amount to increment by
 *                     when allocating sequence numbers from the sequence.
 * 
 * 
 * @GeneratedValue: It provides the specification of generation strategies for
 *                  the values of primary keys. The GeneratedValue annotation
 *                  may be applied to a primary key property of field of an
 *                  entity or mapped superclass in a conjunction with the Id
 *                  annotation. The values that can be used with
 *                  the @GeneratedValue are those values defined inside the enum
 *                  GenerationType. GenerationType.java
 * 
 * @Enumerated: The most common option to map an enum value to and from its
 *              database representation in JPA before 2.1. is to use
 *              the @Enumerated annotation. This way, we can instruct a JPA
 *              provider to convert an enum to its ordinal or String value.
 * 
 * 
 * 
 * 
 */

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

   @Id
   @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
   @GeneratedValue(strategy = SEQUENCE, generator = "student_sequence")
   private Long id;

   private String firstName;

   private String lastName;

   private String email;

   private String password;

   @Enumerated(STRING)
   private AppUserRole appUserRole;

   private Boolean locked = false;

   private Boolean enabled = false;

   public AppUser(String firstName, String lastName, String email, String password, AppUserRole appUserRole) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.appUserRole = appUserRole;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
      return Collections.singletonList(authority);
   }

   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return email;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return !locked;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return enabled;
   }

}
