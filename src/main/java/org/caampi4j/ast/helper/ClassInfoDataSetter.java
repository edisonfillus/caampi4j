package org.caampi4j.ast.helper;

import com.sun.tools.javac.code.Type.ClassType;
import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import static org.caampi4j.ast.common.util.CodeAnalyzerConstants.SERIALIZABLE_PKG;

import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import org.caampi4j.ast.common.model.AnnotationInfo;
import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.common.model.JavaSourceTreeInfo;
import org.caampi4j.ast.common.model.LocationInfo;

import com.sun.tools.javac.code.Symbol.ClassSymbol;
import javax.lang.model.type.TypeKind;

/**
 * Helper class to set the properties of a java class
 * to the java class model
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class ClassInfoDataSetter {

    /**
     * Set the attributes of the currently visiting class 
     * to the java class model
     * @param clazzInfo The java class model
     * @param classTree Curently visiting class tree
     * @param path tree path
     * @param trees trees
     */
    public static void populateClassInfo(ClassInfo clazzInfo,
            ClassTree classTree, TreePath path, Trees trees) {

        TypeElement e = (TypeElement) trees.getElement(path);
        ClassSymbol cs = (ClassSymbol) e;
        //Set qualified class name
        clazzInfo.setName(e.getQualifiedName().toString());
        clazzInfo.setFlatName(cs.flatname.toString());
        clazzInfo.setSimpleName(cs.getSimpleName().toString());
        clazzInfo.setPackageName(cs.packge().flatName().toString());

        //Set Nesting kind
        clazzInfo.setNestingKind(e.getNestingKind().toString());

        //Set modifier details
        for (Modifier modifier : e.getModifiers()) {
            DataSetterUtil.setModifiers(modifier.toString(), clazzInfo);
        }

        //Set extending class info
        if(e.getSuperclass().getKind() != TypeKind.NONE){
            ClassType ct = (ClassType) e.getSuperclass();
            clazzInfo.setNameOfSuperClass(ct.tsym.flatName().toString());
        }

        //Set implementing interface details
        for (TypeMirror mirror : e.getInterfaces()) {
            clazzInfo.addNameOfInterface(mirror.toString());
        }
        //Set serializable property
        try {
            Class serializable = Class.forName(SERIALIZABLE_PKG);
            Class thisClass = Class.forName(e.getQualifiedName().toString());
            if (serializable.isAssignableFrom(thisClass)) {
                clazzInfo.setSerializable(true);
            } else {
                clazzInfo.setSerializable(false);
            }

        } catch (ClassNotFoundException ex) {
            clazzInfo.setSerializable(false);
        }
        
          List<? extends AnnotationMirror> annotations = e.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotations) {
            String qualifiedName = annotationMirror.toString().substring(1);
            AnnotationInfo annotationInfo = new AnnotationInfo();
            annotationInfo.setName(qualifiedName);
            clazzInfo.addAnnotation(annotationInfo);
        }
        
 
        
        LocationInfo locationInfo = DataSetterUtil.getLocationInfo(trees, path, classTree);
        clazzInfo.setLocationInfo(locationInfo);

        //setJavaTreeDetails
        JavaSourceTreeInfo treeInfo = new JavaSourceTreeInfo();
        TreePath tp = trees.getPath(e);
        treeInfo.setCompileTree(tp.getCompilationUnit());
        treeInfo.setSourcePos(trees.getSourcePositions());
        clazzInfo.setSourceTreeInfo(treeInfo);
         
    }
}
