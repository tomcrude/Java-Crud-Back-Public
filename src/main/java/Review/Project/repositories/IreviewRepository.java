package Review.Project.repositories;

import Review.Project.Models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface IreviewRepository extends JpaRepository<ReviewModel, Long> {
    @Query("From ReviewModel where user = :id and id != :review")
    Optional<ArrayList<ReviewModel>> findByUserId(String id,String review);



}
