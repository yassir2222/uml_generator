package org.tp.uml_generator.Bean;

import jakarta.persistence.*;
import lombok.Data;
import org.tp.uml_generator.Bean.enums.ClassType;
import org.tp.uml_generator.Bean.enums.Modifier;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UMLClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Modifier modifier;
    private String className;
    private ClassType classType;

    @OneToMany( cascade = CascadeType.ALL)
    private List<UMLAttribut> attributes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<UMLMethod> methods = new ArrayList<>();

}
