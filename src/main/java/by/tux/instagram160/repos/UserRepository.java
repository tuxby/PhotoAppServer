package by.tux.instagram160.repos;

import by.tux.instagram160.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findUserModelByLogin(String login);
    UserModel findUserModelByLoginAndPassword(String login, String password);
    UserModel findById(long id);
}
