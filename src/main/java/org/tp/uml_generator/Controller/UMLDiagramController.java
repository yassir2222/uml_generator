package org.tp.uml_generator.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/uml")
public class UMLDiagramController {

    private String savedDiagram;

    @PostMapping("/save")
    public ResponseEntity<String> saveDiagram(@RequestBody String diagramJson) {
        savedDiagram = diagramJson;
        return ResponseEntity.ok("{\"message\": \"Diagram saved successfully.\"}");
    }

    // Endpoint to load the diagram (GET request)
    @GetMapping("/load")
    public ResponseEntity<String> loadDiagram() {
        if (savedDiagram != null) {
            return ResponseEntity.ok(savedDiagram);
        }
        return ResponseEntity.ok("{\"message\": \"No diagram found.\"}");
    }
}
//REST
// @GetMapping : Gère les requêtes GET
// @PostMapping : Gère les requêtes POST
// @PutMapping : Gère les requêtes PUT (mise a jour)
// @DeleteMapping : Gère les requêtes DELETE