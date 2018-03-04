/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.ast.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edison
 */
public class TypeInfo {
    private String typeClass;
    private boolean generic = false;
    private boolean primitive = false;
    private boolean array = false;
    private List<TypeInfo> genericClassArguments = new ArrayList<TypeInfo>();

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean isArray) {
        this.array = isArray;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public List<TypeInfo> getGenericClassArguments() {
        return genericClassArguments;
    }

    public void setGenericClassArguments(List<TypeInfo> genericClassArguments) {
        this.genericClassArguments = genericClassArguments;
    }

   

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean isGeneric) {
        this.generic = isGeneric;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean isPrimitive) {
        this.primitive = isPrimitive;
    }
    
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(typeClass);
        if(array){
            sb.append("[]");
        }
        
        if(genericClassArguments.size() > 0){
            sb.append("<");
            for(int i = 0; i < genericClassArguments.size(); i++){
                sb.append(genericClassArguments.get(i).toString());
                if(i < genericClassArguments.size() -1){
                    sb.append(",");
                }
            }
            sb.append(">");
        }
        return sb.toString();
    }


    
    
    
}
