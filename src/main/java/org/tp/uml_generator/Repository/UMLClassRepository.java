package org.tp.uml_generator.Repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.tp.uml_generator.Bean.UMLClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UMLClassRepository extends JpaRepository<UMLClass, Long> {

}
