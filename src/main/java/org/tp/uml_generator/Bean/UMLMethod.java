package org.tp.uml_generator.Bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UMLMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String returnType;
    private String visibility;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UMLParameter> parameters = new ArrayList<>();

}
