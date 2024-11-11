// UMLDiagramService.java
package org.tp.uml_generator.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tp.uml_generator.Bean.UMLClass;
import org.tp.uml_generator.Bean.UMLDiagram;
import org.tp.uml_generator.Bean.UMLRelationship;
import org.tp.uml_generator.Repository.UMLDiagramRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UMLDiagramService {
    private final String baseDirectory = "D:/"; // Customizable base directory

    @Autowired
    private UMLDiagramRepository umlDiagramRepository;
    @Autowired
    private UMLClassService umlClassService;
    @Autowired
    private UMLRelationshipService umlRelationshipService;

    public UMLDiagram createDiagram(UMLDiagram diagram) {
        return umlDiagramRepository.save(diagram);
    }

    public List<UMLDiagram> getAllDiagrams() {
        return umlDiagramRepository.findAll();
    }

    public Optional<UMLDiagram> getDiagramById(Long id) {
        return umlDiagramRepository.findById(id);
    }

    public UMLDiagram updateDiagram(Long id, UMLDiagram updatedDiagram) {
        if (umlDiagramRepository.existsById(id)) {
            updatedDiagram.setId(id); // Ensure the ID is set correctly
            return umlDiagramRepository.save(updatedDiagram);
        }
        return null; // Or throw an exception if appropriate
    }


    public boolean deleteDiagram(Long id) {
        if (umlDiagramRepository.existsById(id)) {
            umlDiagramRepository.deleteById(id);
            return true;
        }
        return false;

    }


    public void createClassesFromDiagram(UMLDiagram diagram) {
        Path directoryPath = Paths.get(baseDirectory + diagram.getDiagramName()); // Directory per diagram
        try {
            Files.createDirectories(directoryPath); // Create directory if it doesn't exist
            for (UMLClass umlClass : diagram.getClasses()) {
                List<UMLRelationship> u= umlRelationshipService.getAllRelationshipsForClass(umlClass);
                umlClassService.CreatClassFile(directoryPath,umlClass,diagram,u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
