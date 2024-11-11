package org.tp.uml_generator.utilites;

import lombok.experimental.UtilityClass;

@UtilityClass
//il déclare la class comme ettant final
//tous les méthode sont static
//maymkench n cree un objet de type StringUtility
public class StringUtility {

    public String toUpperFirstLetter(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase();
    }

    public String toLowerFirstLetter(String className) {
        return className.substring(0,1).toLowerCase()+className.substring(1);
    }
    public String RemoveLastComma(String str){
        if(str==null){
            return "";
        }else {
            return str.replaceAll(",$","");
        }

    }
}
