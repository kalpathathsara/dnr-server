/*
 * Generated By Spring Data JPA Entity Generator
 * @author Niroshan Mendis
 */

package bit.project.server.entity;

import lombok.Data;
import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Servicelog{

    @Id
    @NotNull(message="Id is required")
    @Size(min=36, max=36, message="Character count should be 36")
    private String id;

    @Size(min=0, max=10, message="Maximum character count is 10")
    private String method;

    @Max(value=999999999, message="Maximum value is 999999999")
    private Integer responsecode;

    @Size(min=0, max=100, message="Maximum character count is 100")
    private String ip;

    private LocalDateTime torequest;

    @Lob
    @Size(min=0, max=65535, message="Maximum character count is 65535")
    private String url;

    @Size(min=0, max=255, message="Maximum character count is 255")
    private String handler;

    @ManyToOne
    private Token token;


    public Servicelog(String id){
        this.id = id;
    }


}
