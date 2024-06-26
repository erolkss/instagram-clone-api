package br.com.ero.instagram_web_api.repositories;

import br.com.ero.instagram_web_api.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id IN :users")
    List<User> findAllUsersByUserIds(@Param("users") List<Integer> userIds);

    @Query("SELECT DISTINCT u FROM User u WHERE u.username LIKE %:query% OR u.email LIKE %:query%")
    List<User> findByQuery(@Param("query") String query);
}
