package org.tp.uml_generator.Bean;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UMLAttribut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String visibility;

}
