package Review.Project.controllers;

import Review.Project.Models.ReviewModel;
import Review.Project.Models.UserModel;
import Review.Project.services.ReviewServices;
import Review.Project.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class Controllers implements Icontrollers {

  @Autowired
  private UserServices service;
  @Autowired
  private ReviewServices reviewService;

  @Override
  @PostMapping("/auth/register")
  public HashMap register(@RequestBody UserModel data) {
    return service.register(data);
  }

  @Override
  @PostMapping("/auth/logIn")
  public HashMap logIn(@RequestBody UserModel data){
    return service.logIn(data);
  }

  @Override
  @PostMapping("/auth/createReview")
  public HashMap createReview(@RequestParam("image") MultipartFile img,@RequestParam("title") String title,@RequestParam("description") String description,@RequestParam("user") String user){
    return reviewService.createReview(img,title,description,user);
  }

  @Override
  @GetMapping("/images/{name:.+}")
  public ResponseEntity<Resource> loadImage(@PathVariable String name) throws IOException {

    Resource file = reviewService.loadImg(name);

    String content = Files.probeContentType(file.getFile().toPath());

    return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_TYPE, content)
            .body(file);
  }

  @Override
  @GetMapping("/auth/getAllReviews")
  public ArrayList<Map> getAllReviews() {
    return reviewService.getAllReviews();
  }

  @Override
  @GetMapping("/auth/getReview/{id}")
  public Optional<ReviewModel> getReview(@PathVariable Long id) {
    return reviewService.getReview(id);
  }


  @Override
  @GetMapping("/auth/getUserReviews/{id}/{review}")
  public Optional<ArrayList<ReviewModel>> getUserReviews(@PathVariable Long id,@PathVariable String review) {

    return reviewService.getUserReviews(id,review);
  }

  @Override
  @DeleteMapping("/auth/deleteReview/{id}")
  public HashMap deleteReview(@PathVariable Long id, @RequestBody UserModel data) {

    return reviewService.deleteReview(id,data.getToken(),data.getId());
  }

  @Override
  @PutMapping("/auth/editReview/{id}")
  public HashMap editReview(@PathVariable Long id,@RequestParam("image") MultipartFile img,@RequestParam("title") String title,@RequestParam("description") String description,@RequestParam("user") String user,@RequestParam("token") String token,@RequestParam("userId") String userId) {
    return reviewService.editReviews(title,description,user,img,id,token,userId);
  }


}
