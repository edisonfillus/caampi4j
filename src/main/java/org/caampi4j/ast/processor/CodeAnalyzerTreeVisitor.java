package org.caampi4j.ast.processor;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import javax.lang.model.element.Element;

import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.common.model.ConstructorInvocationInfo;
import org.caampi4j.ast.common.model.MethodInfo;
import org.caampi4j.ast.common.model.MethodInvocationInfo;
import org.caampi4j.ast.common.model.TypeInfo;
import org.caampi4j.ast.common.util.CodeAnalyzerUtil;
import org.caampi4j.ast.helper.ClassInfoDataSetter;
import org.caampi4j.ast.helper.FieldInfoDataSetter;
import org.caampi4j.ast.helper.MethodInfoDataSetter;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
    ClassInfo currentClazzInfo = new ClassInfo();
    public static Map<String, ClassInfo> mapClassInfos = new LinkedHashMap<String, ClassInfo>();
    //Model method stores the details of the visiting method
    MethodInfo currentMethodInfo = new MethodInfo();
    Map<String, MethodInfo> mapMethodInfos = new HashMap<String, MethodInfo>();

    /**
     * Visits the class
     * @param classTree
     * @param trees
     * @return
     */
    @Override
    public Object visitClass(ClassTree classTree, Trees trees) {
        TreePath path = getCurrentPath();
        if (trees.getElement(path) != null) {
            //populate required class information to model
            currentClazzInfo = new ClassInfo();
            ClassInfoDataSetter.populateClassInfo(currentClazzInfo, classTree,
                    path, trees);
            currentClazzInfo.setIdClass(ASTAnalyzerController.getClassIdCouter());
            if(org.caampi4j.main.Main.SHOW_AST_TREE){
                System.out.println("#" + currentClazzInfo.getFlatName() + "[" + currentClazzInfo.getIdClass() + "]");
            }
            mapClassInfos.put(currentClazzInfo.getFlatName(), currentClazzInfo);
        } else {
            int i = 0;
        }
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
        final TreePath methodPath = getCurrentPath();
        if (trees.getElement(methodPath) != null) {
            JCMethodDecl jcmd = (JCMethodDecl) methodTree;
            if (!jcmd.sym.owner.type.tsym.flatName().toString().equals(currentClazzInfo.getFlatName())) {
                //Se não for a mesma classe (innerclass), retorna a classe principal
                currentClazzInfo = mapClassInfos.get(jcmd.sym.owner.type.tsym.flatName().toString());
            }
            currentMethodInfo = new MethodInfo();
            MethodInfoDataSetter.populateMethodInfo(currentMethodInfo, currentClazzInfo, methodTree, methodPath, trees);
            currentMethodInfo.setIdMethod(ASTAnalyzerController.getMethodIdCouter());
            mapMethodInfos.put(currentMethodInfo.toString(), currentMethodInfo);
            if(org.caampi4j.main.Main.SHOW_AST_TREE){
                System.out.print(" +" + currentMethodInfo.getOwningClass().getName() + "." + currentMethodInfo.getName() + "(");
            
                int nParams = 0;
                for (Map.Entry<String, TypeInfo> param : currentMethodInfo.getParameters().entrySet()) {
                    System.out.print(param.getValue().toString());
                    if (nParams < currentMethodInfo.getParameters().size() - 1) {
                        System.out.print(",");
                    }
                    nParams++;
                }
                System.out.println(") [" + currentMethodInfo.getIdMethod() + "]");
            }
            new TreePathScanner<Object, Trees>() {

                private String lastInvocation = "";

                @Override
                public Object visitVariable(VariableTree vt, Trees p) {
                    JCVariableDecl jcvd = (JCVariableDecl) vt;
                    if (jcvd.init != null) {
                        try{
                            CodeAnalyzerUtil.getTypeFromJCExpression(jcvd.init, currentMethodInfo, currentClazzInfo);
                        } catch(Exception e){}
                    }
                    if (!currentMethodInfo.getParameters().containsKey(jcvd.name.toString())) { //Se não for parâmetro, variavel local
                        //TypeInfo typeInfo = CodeAnalyzerUtil.getVariableDeclTypeInfo(jcvd.getType(), currentMethodInfo, currentClazzInfo);
                        try{
                            TypeInfo typeInfo = CodeAnalyzerUtil.getTypeFromJCExpression(jcvd.vartype, currentMethodInfo, currentClazzInfo);
                            currentMethodInfo.getLocalVariables().put(jcvd.name.toString(), typeInfo);
                        } catch(Exception e){}
                    }
                    return super.visitVariable(vt, p);
                }

                @Override
                public Object visitMethodInvocation(MethodInvocationTree mit, Trees p) {
                    if (lastInvocation.indexOf(mit.toString()) == -1) { //Verifica se não é subinvocação
                        lastInvocation = mit.toString();
                        if(org.caampi4j.main.Main.SHOW_AST_TREE){
                            System.out.println("  -" + mit.toString());
                        }
                        JCMethodInvocation jcmi = (JCMethodInvocation) mit;
                        if (jcmi.meth.toString().equals("this") || jcmi.meth.toString().equals("super")) {
                            //Se  for construtor, ignora
                        } else {
                            try {
                                MethodInvocationInfo methodInvocationInfo = CodeAnalyzerUtil.processMethodInvocation(jcmi, currentMethodInfo, currentClazzInfo);
                            } catch (Exception ex) {
                                int i = 0;
                            }

                        }
                    }
                    return super.visitMethodInvocation(mit, p);
                }

                @Override
                public Object visitClass(ClassTree ct, Trees p) {
                    new TreePathScanner<Object, Trees>() {
                        String lastInvocationAnon = "";
                        @Override
                        public Object visitVariable(VariableTree vt, Trees p) {
                            JCVariableDecl jcvd = (JCVariableDecl) vt;
                            if (jcvd.init != null) {
                                try{
                                    CodeAnalyzerUtil.getTypeFromJCExpression(jcvd.init, currentMethodInfo, currentClazzInfo);
                                } catch(Exception e){}
                            }
                            TypeInfo typeInfo = CodeAnalyzerUtil.getTypeFromJCExpression(jcvd.vartype, currentMethodInfo, currentClazzInfo);
                            currentMethodInfo.getLocalVariables().put(jcvd.name.toString(), typeInfo);
                            return super.visitVariable(vt, p);
                        }

                        @Override
                        public Object visitMethodInvocation(MethodInvocationTree mit, Trees p) {
                            if (lastInvocationAnon.indexOf(mit.toString()) == -1) { //Verifica se não é subinvocação
                                lastInvocationAnon = mit.toString();
                                JCMethodInvocation jcmi = (JCMethodInvocation) mit;
                                if (jcmi.meth.toString().equals("this") || jcmi.meth.toString().equals("super")) {
                                    //Se  for construtor, ignora
                                } else {
                                    try {
                                        MethodInvocationInfo methodInvocationInfo = CodeAnalyzerUtil.processMethodInvocation(jcmi, currentMethodInfo, currentClazzInfo);
                                    } catch (Exception ex) {
                                        int i = 0;
                                    }

                                }
                            }
                            return super.visitMethodInvocation(mit, p);
                        }

                    }.scan(getCurrentPath(), p);
                    return super.visitClass(ct, p);
                }

                @Override
                public Object visitNewClass(NewClassTree nct, Trees p) {
                    if (lastInvocation.indexOf(nct.toString()) == -1) { //Verifica se não é subinvocação
                        lastInvocation = nct.toString();
                        if(org.caampi4j.main.Main.SHOW_AST_TREE){
                            System.out.println("  -" + nct.toString());
                        }
                        JCNewClass jcnc = (JCNewClass) nct;
                        try {
                            ConstructorInvocationInfo ci = CodeAnalyzerUtil.processConstructorInvocation(jcnc, currentMethodInfo, currentClazzInfo);
                        } catch (Exception ex) {
                            int i = 0;
                        }
                    }
                    return super.visitNewClass(nct, p);
                }
            }.scan(getCurrentPath(), trees);

        }
        currentClazzInfo.addMethod(currentMethodInfo);
        //populate required method information to model
        //MethodInfoDataSetter.populateMethodInfo(clazzInfo, methodTree, methodPath, trees);
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
        JCVariableDecl jcvd = (JCVariableDecl) variableTree;
        if (jcvd.sym != null && !jcvd.sym.enclClass().flatName().toString().equals(currentClazzInfo.getFlatName())) {
            //Se não for a mesma classe (innerclass), retorna a classe principal
            currentClazzInfo = mapClassInfos.get(jcvd.sym.enclClass().flatName().toString());
        }
        //if(jcvd.init != null){
        //    CodeAnalyzerUtil.getTypeFromJCExpression(jcvd.init, currentMethodInfo, currentClazzInfo); //Processa inicialização
        //}
        //populate required method information to model
        FieldInfoDataSetter.populateFieldInfo(currentClazzInfo, variableTree, e,
                path, trees);
        return super.visitVariable(variableTree, trees);
    }

    /**
     * Returns the Java class model which holds the details of java source
     * @return clazzInfo Java class model 
     */
    public ClassInfo getClassInfo() {
        return currentClazzInfo;
    }

    public Map<String, ClassInfo> getMapClassInfos() {
        return mapClassInfos;
    }
}
