package Review.Project.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "review")
public class ReviewModel {
    @Getter @Setter @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Lob @Getter @Setter
    @Column(columnDefinition = "LONGBLOB")
    private byte[] img;
    @Column @Getter @Setter
    private String title;
    @Column @Getter @Setter
    private String description;
    @Column @Getter @Setter
    private String user;
}

