package bit.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Campaignitem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer duration;

    private BigDecimal price;

    private String design;

    private Date startdate;

    private Date enddate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Campaign campaign;

    @ManyToOne
    private Item item;

    public Campaignitem(Integer id) {
        this.id = id;
    }
}
