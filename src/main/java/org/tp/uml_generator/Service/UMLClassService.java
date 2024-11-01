package org.tp.uml_generator.Service;

import org.tp.uml_generator.Bean.UMLAttribut;
import org.tp.uml_generator.Bean.UMLClass;
import org.tp.uml_generator.Bean.UMLMethod;
import org.tp.uml_generator.Bean.UMLParameter;
import org.tp.uml_generator.Repository.UMLClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tp.uml_generator.utilites.StringUtility;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

@Service
public class UMLClassService {

    @Autowired
    private UMLClassRepository umlClassRepository;

    public List<UMLClass> getAllClasses() {
        return umlClassRepository.findAll();
    }

    public UMLClass getClassById(Long id) {
        return umlClassRepository.findById(id).orElse(null);
    }

    public UMLClass createOrUpdateClass(UMLClass umlClass) {
        return umlClassRepository.save(umlClass);
    }

    public void deleteClass(Long id) {
        umlClassRepository.deleteById(id);
    }

    public void CreatClassFile(UMLClass umlClass){
        String FileName="D:/"+umlClass.getClassName()+".java";
        File file = new File(FileName);

        try {

            // create a new file with name specified
            // by the file object
            boolean value = file.createNewFile();
            if (value) {
                System.out.println("New Java File is created.");
            }
            else {
                System.out.println("The file already exists.");
            }
        }
        catch(Exception e) {
            e.getStackTrace();
        }

        try(FileWriter fileWriter = new FileWriter(FileName)) {
            StringWriter writer = new StringWriter();
            writer.append("public class ")
                    .append(umlClass.getClassName())
                    .append(" { \n");
            // creation dyal les attributs
            //sous format suivant : Visibility Type Name ;
            for(UMLAttribut attr:umlClass.getAttributes()){

                writer.append("\t")
                        .append(attr.getVisibility())
                        .append(" ")
                        .append(attr.getType()).
                        append(" ").
                        append(attr.getName()).
                        append(" ;").
                        append("\n");
            }
            //Create le constructor
            writer.append("\n\t")
                    .append(umlClass.getClassName())
                    .append(" (");
            for(UMLAttribut attr:umlClass.getAttributes()){
                if(!umlClass.getAttributes().get(umlClass.getAttributes().size() - 1).equals(attr)){
                    writer.append(" ")
                            .append(attr.getType()).
                            append(" ").
                            append(attr.getName()).
                            append(", ");
                }else{
                    writer.append(attr.getType()).
                            append(" ")
                            .append(attr.getName())
                            .append(") { \n");
                }

            }
            for(UMLAttribut attr:umlClass.getAttributes()){
                writer.append("\t\t")
                .append("this.")
                        .append(attr.getName())
                        .append(" = ")
                        .append(attr.getName())
                        .append(" ;")
                        .append("\n");
            }
            writer.append("\t}\n");

            for(UMLAttribut attr:umlClass.getAttributes()){
                //Creat getters
                writer.append("\t")
                        .append("public ")
                        .append(" ")
                        .append(attr.getType())
                        .append(" get")
                        .append(StringUtility.toUpperFirstLetter(attr.getName()))
                        .append("() {")
                        .append("\n")
                        .append("\t\treturn ")
                        .append(attr.getName())
                        .append(";\n\t}\n");
                //Creat setters
                writer.append("\t")
                        .append("public ")
                        .append("void")
                        .append(" set")
                        .append(StringUtility.toUpperFirstLetter(attr.getName()))
                        .append("( ")
                        .append(attr.getType())
                        .append(" ")
                        .append(attr.getName())
                        .append(" ) { \n")
                        .append("\t\tthis.")
                        .append(attr.getName())
                        .append(" = ")
                        .append(attr.getName())
                        .append(" ;")
                        .append("\n\t}\n");
            }
            //Cree les méthodes
            for(UMLMethod m:umlClass.getMethods()){
                writer.append("\n\t")
                        .append(m.getVisibility())
                        .append(" ")
                        .append(m.getReturnType())
                        .append(" ")
                        .append(m.getName())
                        .append(" (");
                for (UMLParameter param:m.getParameters()){
                    if(!(m.getParameters().get(m.getParameters().size()-1).equals(param))) {
                        writer.append(param.getType())
                                .append(" ")
                                .append(param.getName())
                                .append(", ");
                    }else{
                        writer.append(param.getType())
                                .append(" ")
                                .append(param.getName());

                    }
                }
                writer.append(" ) {\n");
                if(!(m.getReturnType().equals("void"))){
                    writer.append("\t\treturn ")
                            .append(DefaultReturnValue(m.getReturnType()))
                            .append(" ;");
                }
                writer.append("\n\t}\n");
            }
            writer.append("}");
            String resultat = writer.toString();
            fileWriter.write(resultat);
            //fileWriter.append('C');
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static String DefaultReturnValue(String returnType) {
        switch (returnType) {
            case "int":
            case "byte":
            case "short":
            case "long":
                return "0";
            case "float":
            case "double":
                return "0.0";
            case "boolean":
                return "false";
            case "char":
                return "'\\0'";
            case "String":
                return "\"\""; // chaine vide
            default:
                return "null"; // Objet,List,...
        }
    }
}
