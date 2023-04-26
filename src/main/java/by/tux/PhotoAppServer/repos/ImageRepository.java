package by.tux.PhotoAppServer.repos;

import by.tux.PhotoAppServer.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findById(Long id);
    Optional<ImageModel> findByName(String filename);
}
