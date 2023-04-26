package by.tux.PhotoAppServer.auth.repository;

import by.tux.PhotoAppServer.auth.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByLogin(String login);
    Optional<UserModel> findById(Long id);
}
