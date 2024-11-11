package org.tp.uml_generator.Bean;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UMLParameter {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String type;


}
