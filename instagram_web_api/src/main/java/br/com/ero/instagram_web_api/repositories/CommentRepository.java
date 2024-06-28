package br.com.ero.instagram_web_api.repositories;

import br.com.ero.instagram_web_api.modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
