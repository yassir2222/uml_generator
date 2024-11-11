package org.tp.uml_generator.Service;

import org.tp.uml_generator.Bean.*;
import org.tp.uml_generator.Bean.enums.ClassType;
import org.tp.uml_generator.Bean.enums.RelationshipType;
import org.tp.uml_generator.Repository.UMLClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tp.uml_generator.utilites.StringUtility;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.file.Path;
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

    public void CreatClassFile(UMLClass umlClass) {
        String FileName = "D:/" + umlClass.getClassName() + ".java";
        File file = new File(FileName);

        try {

            // create a new file with name specified
            // by the file object
            boolean value = file.createNewFile();
            if (value) {
                System.out.println("New Java File is created.");
            } else {
                System.out.println("The file already exists.");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter(FileName)) {
            StringWriter writer = new StringWriter();
            writer.append("public class ")
                    .append(umlClass.getClassName())
                    .append(" { \n");
            // creation dyal les attributs
            //sous format suivant : Visibility Type Name ;
            for (UMLAttribut attr : umlClass.getAttributes()) {

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
            for (UMLAttribut attr : umlClass.getAttributes()) {
                if (!umlClass.getAttributes().get(umlClass.getAttributes().size() - 1).equals(attr)) {
                    writer.append(" ")
                            .append(attr.getType()).
                            append(" ").
                            append(attr.getName()).
                            append(", ");
                } else {
                    writer.append(attr.getType()).
                            append(" ")
                            .append(attr.getName())
                            .append(") { \n");
                }

            }
            for (UMLAttribut attr : umlClass.getAttributes()) {
                writer.append("\t\t")
                        .append("this.")
                        .append(attr.getName())
                        .append(" = ")
                        .append(attr.getName())
                        .append(" ;")
                        .append("\n");
            }
            writer.append("\t}\n");

            for (UMLAttribut attr : umlClass.getAttributes()) {
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
            for (UMLMethod m : umlClass.getMethods()) {
                writer.append("\n\t")
                        .append(m.getVisibility())
                        .append(" ")
                        .append(m.getReturnType())
                        .append(" ")
                        .append(m.getName())
                        .append(" (");
                for (UMLParameter param : m.getParameters()) {
                    if (!(m.getParameters().get(m.getParameters().size() - 1).equals(param))) {
                        writer.append(param.getType())
                                .append(" ")
                                .append(param.getName())
                                .append(", ");
                    } else {
                        writer.append(param.getType())
                                .append(" ")
                                .append(param.getName());

                    }
                }
                writer.append(" ) {\n");
                if (!(m.getReturnType().equals("void"))) {
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

    public void CreatClassFile(Path dir, UMLClass umlClass, UMLDiagram diagram, List<UMLRelationship> relationships) {
        String FileName = dir + "/" + umlClass.getClassName() + ".java";
        File file = new File(FileName);

        try {

            boolean value = file.createNewFile();
            if (value) {
                System.out.println("New Java File is created.");
            } else {
                System.out.println("The file already exists.");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(FileName)) {
            StringBuilder str = new StringBuilder();
            str.append(CreatClassHeader(umlClass, diagram, relationships));
            str.append(CreatClassAttribute(umlClass, diagram, relationships));
            str.append(CreatClassConstructeur(umlClass, diagram, relationships));
            str.append(CreatClassGettersSetters(umlClass, diagram, relationships));
            str.append(CreatClassMethods(umlClass, diagram, relationships));
            str.append("}\n");
            fileWriter.write(str.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String CreatClassHeader(UMLClass umlClass, UMLDiagram diagram, List<UMLRelationship> relationships) {
        StringBuilder str = new StringBuilder();
        int firstTimeImplementation = 1;
        int nbrTimeInheritance = 0;


        if (umlClass.getClassType().equals(ClassType.ENUM)) {
            str.append(umlClass.getModifier())
                    .append(" ")
                    .append(umlClass.getClassType())
                    .append(" ").append(umlClass.getClassName())
                    .append(" ");
            for (UMLRelationship relationship : relationships) {
                if (relationship.getType().equals(RelationshipType.REALIZATION)) {
                    if (firstTimeImplementation == 1) {
                        //implment plusieurs fois
                        // mmera loula kaykoun keyword "implements"
                        str.append("implements")
                                .append(relationship.getTargetClass().getClassName())
                                .append(" ");
                        firstTimeImplementation = 0;
                    } else {
                        // mmerat la5rin ghanb9aou ndiro hir ","
                        str.append(",")
                                .append(relationship.getTargetClass().getClassName())
                                .append(" ");
                    }
                }
            }

        }

        if (umlClass.getClassType().equals(ClassType.CLASS) || umlClass.getClassType().equals(ClassType.ABSTRACT)) {
            // e.g  public Class Client
            if (umlClass.getClassType().equals(ClassType.ABSTRACT)) {
                str.append(umlClass.getModifier())
                        .append(" ")
                        .append(umlClass.getClassType())
                        .append(" ")
                        .append("class")
                        .append(" ")
                        .append(umlClass.getClassName())
                        .append(" ");
            } else {
                str.append(umlClass.getModifier())
                        .append(" ")
                        .append(umlClass.getClassType())
                        .append(" ")
                        .append(umlClass.getClassName())
                        .append(" ");
            }

            firstTimeImplementation = 1;
            for (UMLRelationship relationship : relationships) {
                //héritage 1 seule fois
                if (nbrTimeInheritance == 0 && relationship.getType().equals(RelationshipType.INHERITANCE)) {
                    str.append("extends ")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" ");

                    nbrTimeInheritance++;
                }
                // réalisation hhhhhhhh

                if (relationship.getType().equals(RelationshipType.REALIZATION)) {
                    if (firstTimeImplementation == 1) {
                        //implment plusieurs fois
                        // mmera loula kaykoun keyword "implements"
                        str.append("implements")
                                .append(relationship.getTargetClass().getClassName())
                                .append(" ");
                        firstTimeImplementation = 0;
                    } else {
                        // mmerat la5rin ghanb9aou ndiro hir ","
                        str.append(",")
                                .append(relationship.getTargetClass().getClassName())
                                .append(" ");
                    }
                }
            }
        }

        if (umlClass.getClassType().equals(ClassType.INTERFACE)) {
            // e.g  public interface Animal
            str.append(umlClass.getModifier())
                    .append(" ")
                    .append(umlClass.getClassType())
                    .append(" ").append(umlClass.getClassName())
                    .append(" ");
            nbrTimeInheritance = 0;
            for (UMLRelationship relationship : relationships) {
                //héritage 1 seule fois
                if (nbrTimeInheritance == 0 && relationship.getType().equals(RelationshipType.INHERITANCE)) {
                    str.append("extends ")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" ");

                    nbrTimeInheritance++;
                }
            }
        }
        str.append(" { \n");
        return str.toString();
    }

    public String CreatClassAttribute(UMLClass umlClass, UMLDiagram diagram, List<UMLRelationship> relationships) {
        StringBuilder str = new StringBuilder();
        for (UMLAttribut attr : umlClass.getAttributes()) {
            str.append("\t")
                    .append(attr.getVisibility())
                    .append(" ")
                    .append(attr.getType())
                    .append(" ")
                    .append(attr.getName())
                    .append(";\n");
        }

        for (UMLRelationship relationship : relationships) {
            if (relationship.isNavigable()) {
                if (relationship.getMultiplicityTarget().equals("0..1") || relationship.getMultiplicityTarget().equals("1..1") || relationship.getMultiplicityTarget().equals("1")) {
                    str.append("\t")
                            .append("private ")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append(";\n");

                } else if (relationship.getMultiplicityTarget().equals("0..*") || relationship.getMultiplicityTarget().equals("1..*") || relationship.getMultiplicityTarget().equals("*")) {
                    str.append("\t")
                            .append("private List<")
                            .append(relationship.getTargetClass().getClassName())
                            .append("> ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("s ")
                            .append(";\n");
                }
            }
        }
        str.append("\n");
        return str.toString();
    }

    public String CreatClassConstructeur(UMLClass umlClass, UMLDiagram diagram, List<UMLRelationship> relationships) {
        StringBuilder str_Constr_body = new StringBuilder();
        StringBuilder str_Constr_pram = new StringBuilder();
        StringBuilder str_Constr = new StringBuilder();
        for (UMLAttribut attr : umlClass.getAttributes()) {

            //constucteur param
            str_Constr_pram.append(" ")
                    .append(attr.getType())
                    .append(" ")
                    .append(attr.getName())
                    .append(",");
            str_Constr_body.append("\t\t")
                    .append("this.")
                    .append(attr.getName())
                    .append("=")
                    .append(attr.getName())
                    .append(";")
                    .append("\n");

        }

        for (UMLRelationship relationship : relationships) {
            if (relationship.isNavigable()) {
                if (relationship.getMultiplicityTarget().equals("1..1") || relationship.getMultiplicityTarget().equals("1")) {
                    str_Constr_pram.append(" ")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append(",");
                    //Body this.attr = attr;
                    str_Constr_body.append("\t\t")
                            .append("this.")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("=")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append(" ;\n");

                } else if (relationship.getMultiplicityTarget().equals("0..*") || relationship.getMultiplicityTarget().equals("*")) {
                    // e.g : List<Student> students
                    str_Constr_body.append("\t\t")
                            .append("this.")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("=")
                            .append(" new ArrayList<>();")
                            .append("\n");

                } else if (relationship.getMultiplicityTarget().equals("1..*")) {
                    // e.g : List<Student> students
                    str_Constr_pram.append(" ")
                            .append("List<")
                            .append(relationship.getTargetClass().getClassName())
                            .append("> ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("s,");
                    //body e.g : this.student=students;
                    str_Constr_body.append("\t\t")
                            .append("this.")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("s ")
                            .append("=")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("s ;")
                            .append("\n");
                }

            }

        }

        str_Constr.append("\t")
                .append(umlClass.getClassName())
                .append(" (")
                .append(StringUtility.RemoveLastComma(str_Constr_pram.toString()))
                .append(" ){\n")
                .append(str_Constr_body)
                .append("\n\t}")
                .append("\n");
        return str_Constr.toString();
    }

    public String CreatClassGettersSetters(UMLClass umlClass, UMLDiagram diagram, List<UMLRelationship> relationships) {
        StringBuilder str_getter_setter = new StringBuilder();
        for (UMLAttribut attr : umlClass.getAttributes()) {
//            void settId(int id){
//                this.id = id;
//            }
            str_getter_setter.append("\n")
                    .append("\tvoid set")
                    .append(StringUtility.toUpperFirstLetter(attr.getName()))
                    .append(" (")
                    .append(attr.getType())
                    .append(" ")
                    .append(attr.getName())
                    .append("){\n")
                    .append("\t\t")
                    .append("this.")
                    .append(attr.getName())
                    .append(" = ")
                    .append(attr.getName())
                    .append(";\n\t}");

//            int getId(){
//                return this.id;
//            }
            str_getter_setter.append("\n")
                    .append("\t")
                    .append(attr.getType())
                    .append(" get")
                    .append(StringUtility.toUpperFirstLetter(attr.getName()))
                    .append("(){\n")
                    .append("\t\t")
                    .append("return ")
                    .append(attr.getName())
                    .append(";\n\t}");
        }

        for (UMLRelationship relationship : relationships) {
            if (relationship.isNavigable()) {
                if (relationship.getMultiplicityTarget().equals("1..1") || relationship.getMultiplicityTarget().equals("1")) {
//                    void setUser(User user){
//                      this.user = user;
//                  }
                    str_getter_setter.append("\n")
                            .append("\t public void set")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" (")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("){\n")
                            .append("\t\t")
                            .append("this.")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append(" = ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append(";\n\t}");

//            int getId(){
//                return this.id;
//            }
                    str_getter_setter.append("\n")
                            .append("\tpublic")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" get")
                            .append(relationship.getTargetClass().getClassName())
                            .append("(){\n")
                            .append("\t\t")
                            .append("return ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append(";\n\t}");

                } else {
                    // e.g : List<Student> students
//                      public void addStudent(Student student) {
//                          this.students.add(student);
//                      }
                    str_getter_setter.append("\n")
                            .append("\t public void add")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" (")
                            .append(relationship.getTargetClass().getClassName())
                            .append(" ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("){\n")
                            .append("\t\t")
                            .append("this.")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("s.add(")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append(")")
                            .append(";\n\t}");
//                    public List<Student> getStudents() {
//                        return students;
//                    }
                    str_getter_setter.append("\n")
                            .append("\tpublic List<")
                            .append(relationship.getTargetClass().getClassName())
                            .append("> get")
                            .append(relationship.getTargetClass().getClassName())
                            .append("s(){\n")
                            .append("\t\t")
                            .append("return ")
                            .append(StringUtility.toLowerFirstLetter(relationship.getTargetClass().getClassName()))
                            .append("s ;\n\t}");

                }

            }

        }

        return str_getter_setter.toString();
    }

    public String CreatClassMethods(UMLClass umlClass, UMLDiagram diagram, List<UMLRelationship> relationships) {
        StringBuilder str_methods = new StringBuilder();
        if (umlClass.getClassType().equals(ClassType.CLASS)) {
            for (UMLRelationship relationship : relationships) {
                if (relationship.getType().equals(RelationshipType.REALIZATION) || (relationship.getTargetClass().getClassType().equals(ClassType.ABSTRACT))){
                    for (UMLMethod method:relationship.getTargetClass().getMethods()){
                        if(!method.getVisibility().equals("private")){
                            str_methods.append("\t@Override\n")
                                    .append(CreatMethod(method));
                        }
                    }
                }
            }
        }
        for (UMLMethod method:umlClass.getMethods()){
            str_methods.append(CreatMethod(method));
        }

        return str_methods.toString();
    }

    public String CreatMethod(UMLMethod method) {
        StringBuilder str = new StringBuilder();
        str.append("\n\t")
                .append(method.getVisibility())
                .append(" ")
                .append(method.getReturnType())
                .append(" ")
                .append(method.getName())
                .append(" (");
        for (UMLParameter param : method.getParameters()) {
            if (!(method.getParameters().get(method.getParameters().size() - 1).equals(param))) {
                str.append(param.getType())
                        .append(" ")
                        .append(param.getName())
                        .append(",");
            } else {
                str.append(param.getType())
                        .append(" ")
                        .append(param.getName());

            }
        }
        str.append(" ) {\n");
        if (!(method.getReturnType().equals("void"))) {
            str.append("\t\treturn ")
                    .append(DefaultReturnValue(method.getReturnType()))
                    .append(" ;");
        }
        str.append("\n\t}\n");

        return str.toString();
    }
}
