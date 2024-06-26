package br.com.ero.instagram_web_api.repositories;

import br.com.ero.instagram_web_api.modal.Post;
import org.hibernate.annotations.RowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE p.user.id=?1")
    List<Post> findByUserId(Integer userId);
}
