package org.caampi4j.ast.common.model;

import java.util.Collection;
import java.util.Map;

/**
 * Stores method information of java class
 * @author Deepa Sobhana, Seema Richard
 */
public interface Method extends BaseJavaClassModel{

    /**
     * @return the {@link ClassFile} this method belongs to.
     */
    ClassFile getOwningClass();
    
    /**
     * @return the internal names of the method's exception classes. May be null.
     */
    Collection<String> getExceptions();
 
    public Map<String, TypeInfo> getParameters();
    
    TypeInfo getReturnType();
    
}
