package org.caampi4j.ast.helper;

import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import org.caampi4j.ast.common.model.AnnotationInfo;
import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.common.model.FieldInfo;
import org.caampi4j.ast.common.model.LocationInfo;
import org.caampi4j.ast.common.model.TypeInfo;
import org.caampi4j.ast.common.util.CodeAnalyzerUtil;
import org.caampi4j.ast.processor.CodeAnalyzerTreeVisitor;

import com.sun.tools.javac.code.Symbol.ClassSymbol;
import com.sun.tools.javac.code.Symbol.TypeSymbol;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import com.sun.tools.javac.tree.JCTree.JCTypeApply;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

/**
 * Helper class to set the properties of 
 * fields to the java class model
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class FieldInfoDataSetter {

    public static void populateFieldInfo(ClassInfo clazzInfo,
            VariableTree variableTree, Element e, TreePath path, Trees trees) {

        if (e == null) {
            return;
        }

        FieldInfo fieldInfo = new FieldInfo();
        String fieldName = variableTree.getName().toString();
        fieldInfo.setOwningClass(clazzInfo);
        //Set modifier details
        for (Modifier modifier : e.getModifiers()) {
            DataSetterUtil.setModifiers(modifier.toString(), fieldInfo);
        }
        
        JCVariableDecl jcvd = (JCVariableDecl) variableTree;
        
        //Captura o tipo do campo
        //fieldInfo.setFieldType(CodeAnalyzerUtil.getVariableDeclTypeInfo(jcvd.getType(),null,clazzInfo));
        fieldInfo.setFieldType(CodeAnalyzerUtil.getTypeFromJCExpression(jcvd.vartype,null,clazzInfo));
        
        List<? extends AnnotationMirror> annotations = e.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotations) {
            String qualifiedName = annotationMirror.toString().substring(1);
            AnnotationInfo annotationInfo = new AnnotationInfo();
            annotationInfo.setName(qualifiedName);
            fieldInfo.addAnnotation(annotationInfo);
        }
        fieldInfo.setName(fieldName);
        clazzInfo.addField(fieldInfo);
        //Set Temp LocationInfo
        LocationInfo locationInfo = DataSetterUtil.getLocationInfo(trees, path, variableTree);
        fieldInfo.setLocationInfo(locationInfo);
    }
}
