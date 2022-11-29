package by.tux.instagram160.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import by.tux.instagram160.models.PhotoModel;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoModel, Long> {
    PhotoModel findPhotoModelById(Long id);
    List<PhotoModel> findPhotoModelByAuthorId(Long authorId);
}
