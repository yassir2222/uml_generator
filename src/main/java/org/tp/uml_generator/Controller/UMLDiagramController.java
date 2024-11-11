package org.tp.uml_generator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tp.uml_generator.Bean.UMLDiagram;
import org.tp.uml_generator.Service.UMLDiagramService;
import java.util.List;

@RestController
@RequestMapping("/api/diagrams")
public class UMLDiagramController {

    @Autowired
    private UMLDiagramService diagramService;

    @PostMapping
    public ResponseEntity<UMLDiagram> createDiagram(@RequestBody UMLDiagram diagram) {
        UMLDiagram createdDiagram = diagramService.createDiagram(diagram);
        return new ResponseEntity<>(createdDiagram, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UMLDiagram>> getAllDiagrams() {
        List<UMLDiagram> diagrams = diagramService.getAllDiagrams();
        return new ResponseEntity<>(diagrams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UMLDiagram> getDiagramById(@PathVariable Long id) {
        return diagramService.getDiagramById(id)
                .map(diagram -> new ResponseEntity<>(diagram, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UMLDiagram> updateDiagram(@PathVariable Long id, @RequestBody UMLDiagram diagram) {
        UMLDiagram updatedDiagram = diagramService.updateDiagram(id, diagram);
        return updatedDiagram != null ? new ResponseEntity<>(updatedDiagram, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagram(@PathVariable Long id) {
        boolean deleted = diagramService.deleteDiagram(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/{id}/generate-classes")
    public ResponseEntity<String> generateClasses(@PathVariable Long id) {
        try {
            UMLDiagram D= getDiagramById(id).getBody();

            diagramService.createClassesFromDiagram(D);
            return ResponseEntity.ok("Classes generated successfully!");
        } catch (Exception e) { // Catch any exceptions during generation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating classes: " + e.getMessage()); // Return a more informative error message
        }


    }


}