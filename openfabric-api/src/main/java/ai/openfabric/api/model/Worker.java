package ai.openfabric.api.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "workers")
public class Worker extends Datable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    // @GenericGenerator(name = "of-uuid", strategy = "ai.openfabric.api.model.IDGenerator")
    private Long id;

    @NotBlank
    private String name;

    public Worker() {}

    public Worker(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
