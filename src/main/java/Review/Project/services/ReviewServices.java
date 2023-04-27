package Review.Project.services;

import Review.Project.Models.ReviewModel;
import Review.Project.Models.UserModel;
import Review.Project.repositories.IreviewRepository;
import Review.Project.repositories.IuserRepository;
import Review.Project.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ReviewServices {

    @Value("${media.location}")
    private String mediaLocation;

    private Path rootLocation;
    private Path imgDirectory = Paths.get("src//main//resources//static//images");
    private String absoluteRoute = imgDirectory.toFile().getAbsolutePath();

    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);
    }

    @Autowired
    IreviewRepository repository;

    @Autowired
    IuserRepository userRepository;

    // CREATE REVIEW.

    public HashMap createReview(MultipartFile img, String title, String description, String user) {

        HashMap res = new HashMap();

        String type = img.getContentType();

        String reviewVerify = new Utils().reviewVerify(img,title,description,user,type);

        if(reviewVerify != "none"){res.put("status",reviewVerify);return res;}

        ReviewModel data = new ReviewModel();

        try {

            data.setTitle(title);
            data.setDescription(description);
            data.setUser(user);
            data.setImg(img.getBytes());

            Long reviewId = repository.save(data).getId();

            Path destination = rootLocation.resolve(Paths.get(reviewId + "-image.png"))
                    .normalize()
                    .toAbsolutePath();


            try (InputStream input = img.getInputStream()) {
                Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            res.put("id", reviewId);
            res.put("status", "201");

            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // LOAD STATIC FILES.
    public Resource loadImg(String name){


        try {
            Path file = rootLocation.resolve(name);
            Resource resource = new UrlResource((file.toUri()));

            if (resource.exists() || resource.isReadable()){
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file " + name);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file " + name);
        }
    }

    // GET ALL REVIEWS.
    public ArrayList getAllReviews() {
        try {
            return (ArrayList) repository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    // GET REVIEW.
    public Optional<ReviewModel> getReview(Long id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    // GET USER REVIEWS.
    public Optional<ArrayList<ReviewModel>> getUserReviews(Long id,String review) {

        try {
            Optional<ArrayList<ReviewModel>> data = repository.findByUserId(String.valueOf(id),review);
            return data;

        } catch (Exception e) {
            return null;
        }
    }

    // DELETE REVIEW.

    public HashMap deleteReview(Long id,String token,Long userId) {

        HashMap res = new HashMap();

        try {

         Optional<UserModel> search = Optional.ofNullable(userRepository.findByToken(token, String.valueOf(userId)));

         if(search.isEmpty()){
             res.put("status","You are not authorized.");
             return res;
         }

         repository.deleteById(id);

         Path route = Paths.get(absoluteRoute + "//" + id + "-" + "image.png");

         Files.delete(route);

         res.put("status","200");
         return res;

        } catch (Exception e) {
            return null;
        }}

    // EDIT REVIEW.

    public HashMap editReviews(String title, String description, String user, MultipartFile img, Long id, String token,String userId) {

        HashMap res = new HashMap();

        String type = img.getContentType();

        String reviewVerify = new Utils().reviewVerify(img,title,description,user,type);

        if(reviewVerify != "none"){res.put("status",reviewVerify);return res;}

        try {

            Optional<UserModel> search = Optional.ofNullable(userRepository.findByToken(token, userId));

            if(search.isEmpty()){
                res.put("status","You are not authorized.");
                return res;
            }

            ReviewModel data = repository.findById(id).get();

            if (data == null){res.put("status","403");return res;}

            data.setTitle(title);
            data.setDescription(description);
            data.setUser(user);
            data.setImg(img.getBytes());

            Long reviewId = repository.save(data).getId();

            Path destination = rootLocation.resolve(Paths.get(reviewId + "-image.png"))
                    .normalize()
                    .toAbsolutePath();


            try (InputStream input = img.getInputStream()) {
                Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            res.put("status","200");
            return res;

        } catch (Exception e) {
            return null;
        }}

}
