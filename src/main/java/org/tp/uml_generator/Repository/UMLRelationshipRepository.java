package org.tp.uml_generator.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tp.uml_generator.Bean.UMLClass;
import org.tp.uml_generator.Bean.UMLRelationship;

import java.util.List;

public interface UMLRelationshipRepository extends JpaRepository<UMLRelationship, Long> {

    @Query("SELECT r FROM UMLRelationship r WHERE r.sourceClass = :umlClass")
    List<UMLRelationship> findAllRelationshipsByClass(UMLClass umlClass);
}
