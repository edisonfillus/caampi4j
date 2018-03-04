package org.caampi4j.ast.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stores details of fields in the java code
 * @author Deepa Sobhana, Seema Richard
 */
public class FieldInfo extends BaseJavaClassModelInfo implements Field {

    private ClassFile owningClass;
    private TypeInfo fieldType = new TypeInfo();

    /**
     * @return the {@link ClassFile} this method belongs to.
     */
    public ClassFile getOwningClass() {
        return owningClass;
    }

    public void setOwningClass(ClassFile owningClass) {
        this.owningClass = owningClass;
    }

    public TypeInfo getFieldType() {
        return fieldType;
    }

    public void setFieldType(TypeInfo fieldType) {
        this.fieldType = fieldType;
    }
    
    
    
    
}
