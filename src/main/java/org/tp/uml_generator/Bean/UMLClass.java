package org.tp.uml_generator.Bean;

import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UMLClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UMLAttribut> attributes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UMLMethod> methods = new ArrayList<>();


}
