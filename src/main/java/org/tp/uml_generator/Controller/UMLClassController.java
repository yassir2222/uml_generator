package org.tp.uml_generator.Controller;

import org.tp.uml_generator.Bean.UMLClass;
import org.tp.uml_generator.Service.UMLClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uml/classes")
public class UMLClassController {

    @Autowired
    private UMLClassService umlClassService;

    @GetMapping
    public List<UMLClass> getAllClasses() {

        return umlClassService.getAllClasses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UMLClass> getClassById(@PathVariable Long id) {
        UMLClass umlClass = umlClassService.getClassById(id);
        return (umlClass != null) ? ResponseEntity.ok(umlClass) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public UMLClass createClass(@RequestBody UMLClass umlClass) {
        umlClassService.CreatClassFile(umlClass);
        return umlClassService.createOrUpdateClass(umlClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UMLClass> updateClass(@PathVariable Long id, @RequestBody UMLClass umlClass) {
        UMLClass existingClass = umlClassService.getClassById(id);
        if (existingClass != null) {
            umlClass.setId(id);
            return ResponseEntity.ok(umlClassService.createOrUpdateClass(umlClass));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        umlClassService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
