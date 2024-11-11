package org.tp.uml_generator.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tp.uml_generator.Bean.UMLClass;
import org.tp.uml_generator.Bean.UMLRelationship;
import org.tp.uml_generator.Repository.UMLRelationshipRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UMLRelationshipService {

    @Autowired
    private UMLRelationshipRepository umlRelationshipRepository;


    public UMLRelationship createRelationship(UMLRelationship relationship) {
        return umlRelationshipRepository.save(relationship);
    }

    public Optional<UMLRelationship> getRelationshipById(Long id) {
        return umlRelationshipRepository.findById(id);
    }

    public List<UMLRelationship> getAllRelationships() {
        return umlRelationshipRepository.findAll();
    }

    public UMLRelationship updateRelationship(Long id, UMLRelationship updatedRelationship) {
        Optional<UMLRelationship> existingRelationship = umlRelationshipRepository.findById(id);
        if (existingRelationship.isPresent()) {
            updatedRelationship.setId(id);
            return umlRelationshipRepository.save(updatedRelationship);
        } else {
            throw new RuntimeException("Relationship not found with id: " + id);
        }
    }

    public boolean deleteRelationship(Long id) {
        umlRelationshipRepository.deleteById(id);
        return false;
    }

    public List<UMLRelationship> getAllRelationshipsForClass(UMLClass umlClass) {
        return umlRelationshipRepository.findAllRelationshipsByClass(umlClass);
    }



}
