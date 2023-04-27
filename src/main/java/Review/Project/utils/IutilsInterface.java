package Review.Project.utils;
import org.springframework.web.multipart.MultipartFile;

public interface IutilsInterface {

    String verify(String user, String password, String email);

    String reviewVerify(MultipartFile img, String title, String description, String user,String type);

}
