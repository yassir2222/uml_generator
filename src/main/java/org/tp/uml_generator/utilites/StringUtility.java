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
}
