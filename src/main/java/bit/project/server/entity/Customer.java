package bit.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private LocalDateTime tocreation;

    @Lob
    private String description;

    private String name;

    private String contact1;

    private String contact2;

    @Lob
    private String address;

    private String email;

    private String fax;

    @ManyToOne
    @JsonIgnoreProperties({"creator","status","tocreation","roleList"})
    private User creator;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Campaign> campaignList;

//    @JsonIgnore
//    @OneToMany
//    private List<Sale> saleList;

    public Customer(Integer id) {
        this.id = id;
    }

    public Customer(String code, Integer id, String name) {
        this.code = code;
        this.id = id;
        this.name = name;
    }
}
