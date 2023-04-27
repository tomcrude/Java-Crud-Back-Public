package Review.Project.utils;
import org.springframework.web.multipart.MultipartFile;

public class Utils implements IutilsInterface {

    @Override
    public String verify(String user, String password, String email){

        if(user == null || password == null || email == null){
            return "You must complete all the data to continue.";
        }
        if (user.length() < 5 || user.length() > 13 || password.length() < 5 || password.length() > 13)
        {
            return "The username and password must be no less than 5 characters and no more than 13 characters.";
        }
        if (email.length() < 5 || password.length() > 254 )
        {
            return "Wrong email.";
        }
        return "none";}

    @Override
    public String reviewVerify(MultipartFile img, String title, String description, String user,String type){

        if (title == null || description == null) {
            return "You must enter data.";
        }
        if (title.length() > 21 || title.length() < 5) {
            return "The title cannot be less than 4 nor more than 20 characters.";
        }
        if (description.length() > 101 || description.length() < 11) {
            return "The description cannot be less than 10 nor more than 100 characters.";
        }
        if (img.isEmpty() || img.getOriginalFilename().equals("null.jpg")) {
            return "You must upload an image.";
        } else if (img.getSize() > 600000) {
            return "The image must not weigh more than 6MB.";
        } else if (!type.equals("image/png") && !type.equals("image/jpeg") && !type.equals("image/jpg") && !type.equals("image/gif")) {
            return "The image must be PNG - JPG - JPEG - GIF.";
        }
        return "none";
    }





}
