package org.tp.uml_generator.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tp.uml_generator.Bean.enums.RelationshipType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UMLRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RelationshipType type; // khdmt enum "association", "aggregation", "composition", "inheritance"

    private boolean navigable; // true if navigable from source to target

    @NotNull
    private String multiplicitySource; // e.g., "1", "0..*", "1..*"

    @NotNull
    private String multiplicityTarget; // e.g., "1", "0..*", "1..*"


    @ManyToOne
    @JoinColumn(name = "source_class_id")

    private UMLClass sourceClass;

    @ManyToOne
    @JoinColumn(name = "target_class_id")

    private UMLClass targetClass;


}
