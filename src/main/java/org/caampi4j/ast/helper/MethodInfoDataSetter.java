package org.caampi4j.ast.helper;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.Type.TypeVar;
import com.sun.tools.javac.code.Type.ClassType;
import com.sun.tools.javac.code.Type.ArrayType;
import com.sun.tools.javac.code.Symbol.TypeSymbol;
import com.sun.tools.javac.code.Symbol.VarSymbol;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import static org.caampi4j.ast.common.util.CodeAnalyzerConstants.DEFAULT_CONSTRUCTOR_NAME;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor6;

import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.common.model.LocationInfo;
import org.caampi4j.ast.common.model.MethodInfo;
import org.caampi4j.ast.common.model.TypeInfo;
import org.caampi4j.ast.common.util.CodeAnalyzerUtil;

import com.sun.tools.javac.code.Symbol.ClassSymbol;

/**
 * Helper class to set the properties of a method
 * to the java class model
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class MethodInfoDataSetter {

    /**
     * Set the attributes of the currently visiting method 
     * to the java class model
     * @param clazzInfo The java class model
     * @param methodTree Curently visiting method tree
     * @param path tree path
     * @param trees trees
     */
    public static void populateMethodInfo(MethodInfo methodInfo, ClassInfo clazzInfo,
            MethodTree methodTree, TreePath path, Trees trees) {

        String methodName = methodTree.getName().toString();
        methodInfo.setOwningClass(clazzInfo);
        //Set modifier details
        Element e = trees.getElement(path);
        //Set the param type and return path
        visitExecutable(e, methodInfo);

        //set modifiers
        for (Modifier modifier : e.getModifiers()) {
            DataSetterUtil.setModifiers(modifier.toString(), methodInfo);
        }
        
        //Check if the method is a default constructor
        if (methodName.equals(DEFAULT_CONSTRUCTOR_NAME)) {
            methodInfo.setName(CodeAnalyzerUtil.getSimpleNameFromQualifiedName
                                                    (clazzInfo.getName()));
            //clazzInfo.addConstructor(methodInfo);
        } else {
            //clazzInfo.addMethod(methodInfo);
            methodInfo.setName(methodName);
        }
        

       LocationInfo locationInfo = DataSetterUtil.getLocationInfo(trees, path, methodTree);
        methodInfo.setLocationInfo(locationInfo);       
    }

    /**
     * Visit the element passed to this method to extract the parameter types 
     * and return type of the method
     * @param e Element being visited
     * @param methodInfo Model which holds method-level attributes
     */
    private static void visitExecutable(Element e, 
                                        final MethodInfo methodInfo) {
        e.accept(new SimpleElementVisitor6<Object, MethodInfo>() {

                        
            @Override
            public Object visitExecutable(ExecutableElement element, 
                                          MethodInfo mInfo) {
                for (VariableElement var : element.getParameters()) {
                    VarSymbol vars = (VarSymbol) var;
                    if(CodeAnalyzerUtil.getTypeInfoFromTypeSymbol(vars.type).getTypeClass().equals("T")){
                        int i = 0;
                    }
                    methodInfo.getParameters().put(vars.name.toString(), CodeAnalyzerUtil.getTypeInfoFromTypeSymbol(vars.type));
                }
                TypeInfo returnType = new TypeInfo();
                returnType.setTypeClass(element.getReturnType().toString());
                methodInfo.setReturnType(returnType);
                return super.visitExecutable(element, methodInfo);
            }
        }, null);
    }
}
