/*
 * Generated By Spring Data JPA Entity Generator
 * @author Niroshan Mendis
 */

package bit.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Systemmodule{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Size(min=0, max=255, message="Maximum character count is 255")
    private String name;

    @OneToMany(mappedBy="systemmodule")
    @JsonIgnoreProperties({"systemmodule"})
    private List<Usecase> usecaseList;

    public Systemmodule(Integer id){
        this.id = id;
    }


}
