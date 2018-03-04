package org.caampi4j.ast.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Stores method information of java class
 * @author Deepa Sobhana, Seema Richard
 */
public class MethodInfo extends BaseJavaClassModelInfo implements Method {

    private int idMethod;
    private ClassFile owningClass;
    private Map<String, TypeInfo> parameters = new LinkedHashMap<String, TypeInfo>();
    private Map<String, TypeInfo> localVariables = new LinkedHashMap<String, TypeInfo>();
    private TypeInfo returnType;
    Collection<String> exceptions = new ArrayList<String>();
    List<MethodInvocationInfo> invocations = new ArrayList<MethodInvocationInfo>();

    public List<MethodInvocationInfo> getInvocations() {
        return invocations;
    }

    public void addInvocation(MethodInvocationInfo mif){
        invocations.add(mif);
    }
    
    public void setInvocations(List<MethodInvocationInfo> invocations) {
        this.invocations = invocations;
    }

    public int getIdMethod() {
        return idMethod;
    }

    public void setIdMethod(int idMethod) {
        this.idMethod = idMethod;
    }

    
    
    @Override
    public ClassFile getOwningClass() {
        return owningClass;
    }

    public void setOwningClass(ClassFile owningClass) {
        this.owningClass = owningClass;
    }

    public Map<String, TypeInfo> getLocalVariables() {
        return localVariables;
    }

    public void setLocalVariables(Map<String, TypeInfo> localVariables) {
        this.localVariables = localVariables;
    }

    public Map<String, TypeInfo> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, TypeInfo> parameters) {
        this.parameters = parameters;
    }

    @Override
    public TypeInfo getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeInfo returnType) {
        this.returnType = returnType;
    }

    public Collection<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Collection<String> exceptions) {
        this.exceptions = exceptions;
    }

    public void addException(String exception) {
        this.exceptions.add(exception);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(returnType.toString());
        sb.append(" ");
        sb.append(owningClass.toString());
        sb.append(".");
        sb.append(getName());
        sb.append("(");
        int i = 0;
        for (Entry<String, TypeInfo> param : parameters.entrySet()) {
            sb.append(param.getValue().toString());
            if (i < parameters.size() - 2){
                sb.append(",");
            }
            i++;
        }
        sb.append(")");
        return sb.toString();
    }
}
