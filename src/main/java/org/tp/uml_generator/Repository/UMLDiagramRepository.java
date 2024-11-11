// UMLDiagramRepository.java
package org.tp.uml_generator.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tp.uml_generator.Bean.UMLDiagram;

@Repository
public interface UMLDiagramRepository extends JpaRepository<UMLDiagram, Long> {
    // You can add custom queries here if needed
}