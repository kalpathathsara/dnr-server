package bit.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private LocalDateTime tocreation;

    @Lob
    private String description;

    private String name;

    private String location;

    private String address;

    private String facing_from;

    private String towards_to;

    private String photo;

    private double width;

    private double height;

    private double area;

    private BigDecimal price;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Itemstatus itemstatus;

    @ManyToOne
    private District district;

    @ManyToOne
    @JsonIgnoreProperties({"creator","status","tocreation","roleList"})
    private User creator;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Itemprice> itempriceList;
//    @ManyToMany
//    @JoinTable(
//            name="itemsupplier",
//            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name="supplier_id", referencedColumnName = "id")
//    )
//
//    private List<Supplier> supplierlist;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "item")
//    private List<Purchaseitem> puchaseitemList;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "item")
//    private List<Saleitem> saleitemList;


    public Item(Integer id) {
        this.id = id;
    }
}
