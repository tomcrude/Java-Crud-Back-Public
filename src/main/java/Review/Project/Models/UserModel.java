package Review.Project.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "userReviews")
public class UserModel {
  @Getter @Setter @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private Long id;
  @Column @Getter @Setter
  private String username;
  @Column @Getter @Setter
  private String password;
  @Column @Getter @Setter
  private String email;
  @Column @Getter @Setter
  private String token;
}
