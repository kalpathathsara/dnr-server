package bit.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Campaign {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private LocalDateTime tocreation;

    private String name;

    @Lob
    private String description;

    private Date startdate;

    private Date enddate;

    private BigDecimal total;

    private Integer duration;

    @ManyToOne
    @JsonIgnoreProperties({"creator","status","tocreation","roleList"})
    private User creator;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Campaignstatus campaignstatus;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Campaignitem> campaignitemList;

    public Campaign(Integer id){
        this.id = id;
    }

}
