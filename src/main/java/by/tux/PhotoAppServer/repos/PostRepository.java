package by.tux.PhotoAppServer.repos;

import by.tux.PhotoAppServer.models.PostModel;
import by.tux.PhotoAppServer.models.PostResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {

    List<PostModel> findByAuthorId(long id);
}