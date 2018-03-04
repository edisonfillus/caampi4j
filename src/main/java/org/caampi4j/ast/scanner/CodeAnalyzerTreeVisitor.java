package org.caampi4j.ast.scanner;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import javax.lang.model.element.Element;

import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.helper.ClassInfoDataSetter;
import org.caampi4j.ast.helper.FieldInfoDataSetter;
import org.caampi4j.ast.helper.MethodInfoDataSetter;

/**
 * Visitor class which visits different nodes of the input source file, 
 * extracts the required atribute of the visiting class, its mehods, 
 * fields, annotations etc and set it in the java class model.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees> {

    //Model class stores the details of the visiting class
    ClassInfo clazzInfo = new ClassInfo();

    /**
     * Visits the class
     * @param classTree
     * @param trees
     * @return
     */
    @Override
    public Object visitClass(ClassTree classTree, Trees trees) {

        TreePath path = getCurrentPath();
        //populate required class information to model
        ClassInfoDataSetter.populateClassInfo(clazzInfo, classTree, 
                                              path, trees);
        return super.visitClass(classTree, trees);
    }

    /**
     * Visits all methods of the input java source file
     * @param methodTree
     * @param trees
     * @return
     */
    @Override
    public Object visitMethod(MethodTree methodTree, Trees trees) {
        TreePath path = getCurrentPath();
        
        System.out.println(clazzInfo.getName() + methodTree.getName());
        
        //populate required method information to model
       // MethodInfoDataSetter.populateMethodInfo(clazzInfo, methodTree, 
       //                                         path, trees);
        return super.visitMethod(methodTree, trees);
    }

    /**
     * Visits all variables of the java source file
     * @param variableTree
     * @param trees
     * @return
     */
    @Override
    public Object visitVariable(VariableTree variableTree, Trees trees) {
        TreePath path = getCurrentPath();
        Element e = trees.getElement(path);

        //populate required method information to model
        FieldInfoDataSetter.populateFieldInfo(clazzInfo, variableTree, e, 
                                              path, trees);
        return super.visitVariable(variableTree, trees);
    }

    /**
     * Returns the Java class model which holds the details of java source
     * @return clazzInfo Java class model 
     */
    public ClassInfo getClassInfo() {
        return clazzInfo;
    }
}

