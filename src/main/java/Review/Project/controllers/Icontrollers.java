package Review.Project.controllers;

import Review.Project.Models.ReviewModel;
import Review.Project.Models.UserModel;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface Icontrollers {

    HashMap register(@RequestBody UserModel data);

    HashMap logIn(@RequestBody UserModel data);

    HashMap createReview(@RequestParam("image") MultipartFile img,@RequestParam("title") String title,@RequestParam("description") String description,@RequestParam("user") String user);

    ResponseEntity<Resource> loadImage(@PathVariable String name) throws IOException;

    ArrayList<Map> getAllReviews();

    Optional<ReviewModel> getReview(@PathVariable Long id);

    Optional<ArrayList<ReviewModel>> getUserReviews(@PathVariable Long id,@PathVariable String review);

    HashMap deleteReview(@PathVariable Long id,@RequestBody UserModel data);

    HashMap editReview(@PathVariable Long id,@RequestParam("image") MultipartFile img,@RequestParam("title") String title,@RequestParam("description") String description,@RequestParam("user") String user,@RequestParam("token") String token,@RequestParam("userId") String userId);

}

