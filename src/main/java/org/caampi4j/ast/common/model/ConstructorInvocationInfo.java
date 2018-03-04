package org.caampi4j.ast.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Stores method information of java class
 * @author Deepa Sobhana, Seema Richard
 */
public class ConstructorInvocationInfo {

    private TypeInfo clazz;
    private List<TypeInfo> parameters = new ArrayList<TypeInfo>();

    public List<TypeInfo> getParameters() {
        return parameters;
    }

    public void setParameters(List<TypeInfo> parameters) {
        this.parameters = parameters;
    }

    public TypeInfo getClazz() {
        return clazz;
    }

    public void setClazz(TypeInfo clazz) {
        this.clazz = clazz;
    }

   


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClazz().toString());
        sb.append("(");
        for (int i = 0; i < getParameters().size(); i++) {
            sb.append(getParameters().get(i));
            if (i < getParameters().size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
