package br.com.ero.instagram_web_api.repositories;

import br.com.ero.instagram_web_api.modal.Post;
import org.hibernate.annotations.RowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
