package spring.emailverification.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @Repository: The Spring @Repository annotation is a specialization of
 *              the @Component annotation which indicates that an annotated
 *              class is a “Repository”, which can be used as a mechanism for
 *              encapsulating storage, retrieval, and search behavior which
 *              emulates a collection of objects
 * 
 * @Transactional: @Transactional annotation is used when you want the certain
 *                 method/class(=all methods inside) to be executed in a
 *                 transaction.
 * 
 *                 Let's assume user A wants to transfer 100$ to user B. What
 *                 happens is:
 * 
 *                 We decrease A's account by 100$ We add 100$ to B's account
 *                 Let's assume the exception is thrown after succeeding 1) and
 *                 before executing 2). Now we would have some kind of
 *                 inconsistency because A lost 100$ while B got nothing.
 *                 Transactions means all or nothing. If there is an exception
 *                 thrown somewhere in the method, changes are not persisted in
 *                 the database. Something called rollback happens.
 * 
 * @Modifying: Adding @Modifying annotation indicates the query is not for a
 *             SELECT query.
 * 
 */

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
   Optional<AppUser> findByEmail(String email);

   @Transactional
   @Modifying
   @Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.email = ?1")
   int enableAppUser(String email);
}
