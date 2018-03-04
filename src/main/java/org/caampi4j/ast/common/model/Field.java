package org.caampi4j.ast.common.model;

import java.util.Collection;

/**
 * Stores details of fields in the java code
 * @author Deepa Sobhana, Seema Richard
 */
public interface Field extends BaseJavaClassModel{

    void setFieldType(TypeInfo fieldType);
    
    /**
     * @return the {@link ClassFile} this method belongs to.
     */
    ClassFile getOwningClass();    

    TypeInfo getFieldType();
    

}
