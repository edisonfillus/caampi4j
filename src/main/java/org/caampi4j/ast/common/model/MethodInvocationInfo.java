package org.caampi4j.ast.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Stores method information of java class
 * @author Deepa Sobhana, Seema Richard
 */
public class MethodInvocationInfo {

    private String name;
    private TypeInfo clazz;
    private List<TypeInfo> parameters = new ArrayList<TypeInfo>();
    private TypeInfo returnType;
    private int idMethodInvocation;

    public List<TypeInfo> getParameters() {
        return parameters;
    }

    public int getIdMethodInvocation() {
        return idMethodInvocation;
    }

    public void setIdMethodInvocation(int idMethodInvocation) {
        this.idMethodInvocation = idMethodInvocation;
    }

    public void setParameters(List<TypeInfo> parameters) {
        this.parameters = parameters;
    }

    public TypeInfo getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeInfo returnType) {
        this.returnType = returnType;
    }

    public TypeInfo getClazz() {
        return clazz;
    }

    public void setClazz(TypeInfo clazz) {
        this.clazz = clazz;
    }

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getReturnType().toString());
        sb.append(" ");
        sb.append(getClazz().toString());
        sb.append(".");
        sb.append(getName());
        sb.append("(");
        for (int i = 0; i < getParameters().size(); i++) {
            sb.append(getParameters().get(i).toString());
            if (i < getParameters().size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
