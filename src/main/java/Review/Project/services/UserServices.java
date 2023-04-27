package Review.Project.services;
import Review.Project.Models.UserModel;
import Review.Project.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import Review.Project.repositories.IuserRepository;


import java.util.HashMap;


@Service
public class UserServices {
  @Autowired
  IuserRepository repository;

  HashMap response = new HashMap();

  public Utils util;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public HashMap register(UserModel data){

    String user = data.getUsername();
    String password = data.getPassword();
    String email = data.getEmail();

    Boolean search2 = email.contains("@");
    Boolean com = email.contains(".com");

    String verify = new Utils().verify(user,password,email);

    if (verify != "none"){
      response.put("status", verify);
      return response;
    }
    if (search2 != true || com != true )
    {
      response.put("status", "Wrong email.");
      return response;
    }

    try{
      UserModel search = repository.verify(user);
      if(search == null){

        data.setPassword(passwordEncoder.encode(data.getPassword()));

        String token = user + "-" + Math.random()*100000;
        data.setToken(token);
        repository.save(data);
        response.put("status","201");
      }
      else{
        response.put("status","The username is already in use.");
      }
     }
     catch (Exception e) {
        this.response.put("status", "An error has occurred try again.");
      }
     return response;
    }

  public HashMap logIn(UserModel data){

      String user = data.getUsername();
      String password = data.getPassword();

      try{
        UserModel search = repository.verify(user);

        if (search != null){

            if (!passwordEncoder.matches(password,search.getPassword())){response.put("status","Username and password do not match.");}
            else response.put("status","200");response.put("info",search);

        }
        else {response.put("status","Username and password do not match.");}
      }
      catch (Exception e) {
        this.response.put("status", "An error has occurred try again.");
      }

    return response;
  }




}
