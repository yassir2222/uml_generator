package org.tp.uml_generator.Repository;

import org.tp.uml_generator.Bean.UMLClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UMLClassRepository extends JpaRepository<UMLClass, Long> {
}
