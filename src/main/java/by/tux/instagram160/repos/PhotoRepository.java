package by.tux.instagram160.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tux.instagram160.models.PhotoModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PhotoRepository extends JpaRepository<PhotoModel, Long> {
    Optional<PhotoModel> findById(Long id);

    List<PhotoModel> findByAuthorId(Long authorId);
}
