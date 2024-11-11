package org.tp.uml_generator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tp.uml_generator.Bean.UMLRelationship;
import org.tp.uml_generator.Service.UMLRelationshipService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/relationships")
public class UMLRelationshipController {

    @Autowired
    private UMLRelationshipService umlRelationshipService;

    @PostMapping
    public ResponseEntity<UMLRelationship> createRelationship(@RequestBody UMLRelationship relationship) {
        UMLRelationship createdRelationship = umlRelationshipService.createRelationship(relationship);
        return ResponseEntity.ok(createdRelationship);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UMLRelationship> getRelationshipById(@PathVariable Long id) {
        Optional<UMLRelationship> relationship = umlRelationshipService.getRelationshipById(id);
        return relationship.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UMLRelationship>> getAllRelationships() {
        List<UMLRelationship> relationships = umlRelationshipService.getAllRelationships();
        return ResponseEntity.ok(relationships);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UMLRelationship> updateRelationship(@PathVariable Long id, @RequestBody UMLRelationship updatedRelationship) {
        Optional<UMLRelationship> updated = Optional.ofNullable(umlRelationshipService.updateRelationship(id, updatedRelationship));
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        if (umlRelationshipService.deleteRelationship(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

