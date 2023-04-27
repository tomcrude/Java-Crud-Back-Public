package Review.Project.repositories;

import Review.Project.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IuserRepository extends JpaRepository<UserModel, Long> {

    @Query("From UserModel where username = :username")
    UserModel verify(String username);;

    @Query("From UserModel where token = :token and id = :id")
    UserModel findByToken(String token,String id);
}
