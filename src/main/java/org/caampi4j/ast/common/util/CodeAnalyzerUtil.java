package org.caampi4j.ast.common.util;

import com.sun.source.tree.Tree.Kind;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.TypeElement;

import org.caampi4j.ast.common.model.Annotation;
import org.caampi4j.ast.common.model.ClassFile;
import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.common.model.ConstructorInvocationInfo;
import org.caampi4j.ast.common.model.MethodInfo;
import org.caampi4j.ast.common.model.MethodInvocationInfo;
import org.caampi4j.ast.common.model.TypeInfo;
import org.caampi4j.ast.common.rule.JavaCodeRule;
import org.caampi4j.ast.processor.ASTAnalyzerController;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Scope.Entry;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.Type.ArrayType;
import com.sun.tools.javac.code.Type.ClassType;
import com.sun.tools.javac.code.Type.TypeVar;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCArrayAccess;
import com.sun.tools.javac.tree.JCTree.JCArrayTypeTree;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCBinary;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCConditional;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCNewArray;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCParens;
import com.sun.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import com.sun.tools.javac.tree.JCTree.JCTypeApply;
import com.sun.tools.javac.tree.JCTree.JCTypeCast;
import com.sun.tools.javac.tree.JCTree.JCUnary;
import com.sun.tools.javac.util.Name;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encapsulates the utility methods of verification module
 *
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class CodeAnalyzerUtil {

    /**
     * Creates instances of Rule using rule class name
     *
     * @param ruleNameList
     *            List of rule class names
     * @return List of rule class instances
     */
    public static List<JavaCodeRule<TypeElement>> getRuleInstances(
            Set<String> ruleNameList) {

        List<JavaCodeRule<TypeElement>> classRules =
                new LinkedList<JavaCodeRule<TypeElement>>();
        Class classDefinition = null;
        Object object = null;
        try {
            for (String ruleName : ruleNameList) {
                classDefinition = Class.forName(ruleName);
                object = classDefinition.newInstance();
                classRules.add((JavaCodeRule<TypeElement>) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return classRules;
    }

    /**
     * Returns the simple name of an element from its fully qualified name
     * @param qualifiedName Fully qualified name of the element
     * @return simpleName Simple name of the element
     */
    public static String getSimpleNameFromQualifiedName(String qualifiedName) {

        String simpleName = null;
        if (qualifiedName != null && qualifiedName.length() > 0) {
            simpleName = qualifiedName.substring(1 + qualifiedName.lastIndexOf("."), qualifiedName.length());
        }
        return simpleName;
    }

    public static List getAnnotationAsStringList(ClassFile classFile) {
        List<String> annotationList = new ArrayList<String>();
        for (Annotation clazzAnnotation : classFile.getAnnotations()) {
            annotationList.add(clazzAnnotation.getName());
        }
        return annotationList;
    }

    public static Class getPrimitiveClassOfPrimitive(String primitive) {
        if (primitive.equals("int")) {
            return int.class;
        } else if (primitive.equals("double")) {
            return double.class;
        } else if (primitive.equals("float")) {
            return float.class;
        } else if (primitive.equals("boolean")) {
            return boolean.class;
        } else if (primitive.equals("long")) {
            return long.class;
        } else if (primitive.equals("short")) {
            return short.class;
        } else if (primitive.equals("byte")) {
            return byte.class;
        } else if (primitive.equals("char")) {
            return char.class;
        } else if (primitive.equals("void")) {
            return void.class;
        } else {
            return null;
        }
    }

    public static Class getPrimitiveArrayClassOfPrimitive(String primitive) {
        if (primitive.equals("int")) {
            return int[].class;
        } else if (primitive.equals("double")) {
            return double[].class;
        } else if (primitive.equals("float")) {
            return float[].class;
        } else if (primitive.equals("boolean")) {
            return boolean[].class;
        } else if (primitive.equals("long")) {
            return long[].class;
        } else if (primitive.equals("short")) {
            return short[].class;
        } else if (primitive.equals("byte")) {
            return byte[].class;
        } else if (primitive.equals("char")) {
            return char[].class;
        } else {
            return null;
        }
    }

    public static Class getClassFromPrimitive(String primitive) {
        if (primitive.equals("int")) {
            return Integer.class;
        } else if (primitive.equals("double")) {
            return Double.class;
        } else if (primitive.equals("float")) {
            return Float.class;
        } else if (primitive.equals("boolean")) {
            return Boolean.class;
        } else if (primitive.equals("long")) {
            return Long.class;
        } else if (primitive.equals("short")) {
            return Short.class;
        } else if (primitive.equals("byte")) {
            return Byte.class;
        } else if (primitive.equals("char")) {
            return Character.class;
        } else {
            return null;
        }
    }

    public static Class getArrayClassFromPrimitive(String primitive) {
        if (primitive.equals("int")) {
            return Integer[].class;
        } else if (primitive.equals("double")) {
            return Double[].class;
        } else if (primitive.equals("float")) {
            return Float[].class;
        } else if (primitive.equals("boolean")) {
            return Boolean[].class;
        } else if (primitive.equals("long")) {
            return Long[].class;
        } else if (primitive.equals("short")) {
            return Short[].class;
        } else if (primitive.equals("byte")) {
            return Byte[].class;
        } else if (primitive.equals("char")) {
            return Character[].class;
        } else {
            return null;
        }
    }

    public static String getJCFieldAccessClassType(JCTree jce, MethodInfo methodInfo, ClassInfo clazzInfo) {
        if (jce instanceof JCFieldAccess) {
            JCFieldAccess jcfa = (JCFieldAccess) jce;
            LinkedList<SimpleEntry<Object, Kind>> chain = getJCFieldAccessChain(jcfa, methodInfo, clazzInfo);
            //Recupera Identificador
            SimpleEntry<Object, Tree.Kind> elem = chain.pollLast();
            TypeInfo ti = (TypeInfo) elem.getKey();
            while (!chain.isEmpty()) {
                elem = chain.pollLast();
                if (elem.getValue() == Tree.Kind.OTHER) {
                    return ti.getTypeClass() + "$" + jcfa.name.toString();
                } else {
                    return null;
                }
            }
            return null;
//            if (jcfa.selected instanceof JCIdent) {
//                JCIdent jci = (JCIdent) jcfa.selected;
//                String className = getClassNameFromName(jci.name, clazzInfo);
//                return className + "$" + jcfa.name.toString();
//            } else {
//                return "Parâmetro TypeCast não identificado";
//            }
        } else {
            return null;
        }
    }

    public static TypeInfo getClassNameFromName(Name ident, ClassInfo clazzInfo) {
        if (ident.toString().equals("this")) {
            TypeInfo type = new TypeInfo();
            type.setTypeClass(clazzInfo.getFlatName());
            return type;
        }
        if (ident.toString().equals("super")) {
            TypeInfo type = new TypeInfo();
            type.setTypeClass(clazzInfo.getNameOfSuperClass());
            return type;
        }
        JCCompilationUnit jccu = (JCCompilationUnit) clazzInfo.getSourceTreeInfo().getCompileTree();
        //Busca na hierarquia
        if (jccu.defs.last() instanceof JCClassDecl) {
            JCClassDecl jccd = (JCClassDecl) jccu.defs.last();
            Entry e = jccd.sym.members_field.lookup(ident);
            if (e.sym != null) {
                return getTypeInfoFromTypeSymbol(e.sym.type);
            }
            if (jccd.extending != null) {
                Entry f = jccd.extending.type.tsym.members().lookup(ident);
                if (f.sym != null) {
                    return getTypeInfoFromTypeSymbol(f.sym.type);
                }
            }

            for (JCExpression jce : jccd.implementing) {
                Entry imp = jce.type.tsym.members().lookup(ident);
                if (imp.sym != null) {
                    return getTypeInfoFromTypeSymbol(imp.sym.type);
                }
            }
            int i = 0;
        }

        //Busca no pacote
        Entry ent = jccu.packge.members_field.lookup(ident);
        if (ent.sym != null) {
            return getTypeInfoFromTypeSymbol(ent.sym.type);
        }
        //Busca nos imports
        ent = jccu.namedImportScope.lookup(ident);
        if (ent.sym != null) {
            return getTypeInfoFromTypeSymbol(ent.sym.type);
        }
        ent = jccu.starImportScope.lookup(ident);
        if (ent.sym != null) {
            return getTypeInfoFromTypeSymbol(ent.sym.type);
        }
        if (ident.toString().equals("T")) {
            //Se genérico
            TypeInfo type = new TypeInfo();
            type.setTypeClass(Object.class.getName());
            return type;
        } else if (ident.toString().equals(clazzInfo.getName().substring(clazzInfo.getName().length() - ident.len, clazzInfo.getName().length()))) {
            TypeInfo type = new TypeInfo();
            type.setTypeClass(clazzInfo.getFlatName());
            return type;
        } else {

            //Verifica se é subclasse
            String subclassname = clazzInfo.getName() + "$" + ident.toString();
            Class subclass = null;
            try {
                subclass = Class.forName(subclassname, false, ClassLoader.getSystemClassLoader());
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (subclass != null) {
                TypeInfo type = new TypeInfo();
                type.setTypeClass(subclass.getName());
                return type;
            } else {
                return null;
            }
        }

    }

    public static MethodInvocationInfo processMethodInvocation(JCMethodInvocation jcmi, MethodInfo methodInfo, ClassInfo clazzInfo) {
        MethodInvocationInfo methodInvocationInfo = new MethodInvocationInfo();
        methodInvocationInfo.setIdMethodInvocation(ASTAnalyzerController.getInvocationIdCounter());
        //Idenfica parâmetros
        for (JCExpression jce : jcmi.args) {
            methodInvocationInfo.getParameters().add(getTypeFromJCExpression(jce, methodInfo, clazzInfo));
        }
        //Processa invocação
        if (jcmi.meth instanceof JCFieldAccess) {
            JCFieldAccess jcfa = (JCFieldAccess) jcmi.meth;
            methodInvocationInfo.setName(jcfa.getIdentifier().toString()); //Nome
            methodInvocationInfo.setClazz(getMethodTypeFromFieldAccessChain(getJCFieldAccessChain(jcfa, methodInfo, clazzInfo)));
            populateMethodInvocationInfo(methodInvocationInfo, clazzInfo);
        } else if (jcmi.meth instanceof JCIdent) { //Chamada a método intra classe
            JCIdent jci = (JCIdent) jcmi.meth;
            methodInvocationInfo.setName(jci.getName().toString());
            TypeInfo ti = new TypeInfo();
            ti.setTypeClass(clazzInfo.getFlatName());
            methodInvocationInfo.setClazz(ti);
            populateMethodInvocationInfo(methodInvocationInfo, clazzInfo);
        } else {
            methodInvocationInfo.setName("Classe de Identificador Não identificado");
        }
        if(org.caampi4j.main.Main.SHOW_AST_TREE){
            System.out.println("   > " + methodInvocationInfo.toString() + "[" + methodInvocationInfo.getIdMethodInvocation() + "]");
        }
        ASTAnalyzerController.incrementInvocationProcessedCounter();
        methodInfo.addInvocation(methodInvocationInfo);
        return methodInvocationInfo;
    }

    public static ConstructorInvocationInfo processConstructorInvocation(JCNewClass jcnc, MethodInfo methodInfo, ClassInfo clazzInfo) {
        ConstructorInvocationInfo constructorInvocationInfo = new ConstructorInvocationInfo();
        //Idenfica parâmetros
        for (JCExpression jce : jcnc.args) {
            constructorInvocationInfo.getParameters().add(getTypeFromJCExpression(jce, methodInfo, clazzInfo));
        }
        //Captura Classe
        TypeInfo type = getTypeFromJCExpression(jcnc.clazz, methodInfo, clazzInfo);
        constructorInvocationInfo.setClazz(type);
        if(org.caampi4j.main.Main.SHOW_AST_TREE){
            System.out.println("   > " + constructorInvocationInfo.toString());
        }
        return constructorInvocationInfo;
    }

    public static LinkedList<SimpleEntry<Object, Tree.Kind>> getJCFieldAccessChain(JCFieldAccess jcfa, MethodInfo methodInfo, ClassInfo clazzInfo) {
        LinkedList<SimpleEntry<Object, Tree.Kind>> chain = new LinkedList<SimpleEntry<Object, Tree.Kind>>();
        chain.add(new SimpleEntry<Object, Tree.Kind>(jcfa.name.toString(), Tree.Kind.OTHER));
        while (true) {
            if (jcfa.selected instanceof JCFieldAccess) {
                jcfa = (JCFieldAccess) jcfa.selected;
                Class type = null; //tenta verificar se o field access não se trata de uma declaração de class
                try {
                    type = Class.forName(jcfa.toString());
                } catch (ClassNotFoundException ex) {
                    //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (type != null) {
                    TypeInfo ti = new TypeInfo();
                    ti.setTypeClass(type.getName());
                    ti.setPrimitive(type.isPrimitive());
                    ti.setArray(type.isPrimitive());
                    chain.add(new SimpleEntry<Object, Tree.Kind>(ti, Tree.Kind.IDENTIFIER));
                    break;
                } else {
                    chain.add(new SimpleEntry<Object, Tree.Kind>(jcfa.name.toString(), Tree.Kind.MEMBER_SELECT));
                }
            } else if (jcfa.selected instanceof JCIdent) {
                //Recupera classe identificador
                JCIdent jci = (JCIdent) jcfa.selected;
                TypeInfo ti = getJCIdentType(jci, methodInfo, clazzInfo);
                chain.add(new SimpleEntry<Object, Tree.Kind>(ti, Tree.Kind.IDENTIFIER));
                break;
            } else if (jcfa.selected instanceof JCMethodInvocation) {
                JCMethodInvocation jcmi = (JCMethodInvocation) jcfa.selected;
                MethodInvocationInfo mif = processMethodInvocation(jcmi, methodInfo, clazzInfo);
                chain.add(new SimpleEntry<Object, Tree.Kind>(mif.getReturnType(), Tree.Kind.METHOD_INVOCATION));
                break;
            } else if (jcfa.selected instanceof JCParens) {
                JCParens jcp = (JCParens) jcfa.selected;
                chain.add(new SimpleEntry<Object, Tree.Kind>(getTypeFromJCExpression(jcp.expr, methodInfo, clazzInfo), Tree.Kind.PARENTHESIZED));
                break;
            } else if (jcfa.selected instanceof JCNewClass) {
                JCNewClass jcnc = (JCNewClass) jcfa.selected;
                ConstructorInvocationInfo cif = processConstructorInvocation(jcnc, methodInfo, clazzInfo);
                chain.add(new SimpleEntry<Object, Tree.Kind>(cif.getClazz(), Tree.Kind.METHOD_INVOCATION));
                break;
            } else if (jcfa.selected instanceof JCPrimitiveTypeTree) {
                JCPrimitiveTypeTree jctt = (JCPrimitiveTypeTree) jcfa.selected;
                TypeInfo type = new TypeInfo();
                type.setTypeClass(jctt.toString());
                type.setPrimitive(true);
                chain.add(new SimpleEntry<Object, Tree.Kind>(type, Tree.Kind.PRIMITIVE_TYPE));
                break;
            } else if (jcfa.selected instanceof JCArrayAccess) {
                JCArrayAccess jcaa = (JCArrayAccess) jcfa.selected;
                getTypeFromJCExpression(jcaa.index, methodInfo, clazzInfo); //Processa o índice, pode ser invocação método
                TypeInfo type = getTypeFromJCExpression(jcaa.indexed, methodInfo, clazzInfo);
                type.setArray(false);
                chain.add(new SimpleEntry<Object, Tree.Kind>(type, Tree.Kind.MEMBER_SELECT));
                break;
            } else {
                if(org.caampi4j.main.Main.SHOW_AST_TREE){
                    System.out.println("Chain não identificado");
                }
                return null;
            }
        }
        return chain;
    }

    public static TypeInfo getMethodTypeFromFieldAccessChain(LinkedList<SimpleEntry<Object, Tree.Kind>> chain) {
        //Recupera Identificador
        SimpleEntry<Object, Tree.Kind> elem = chain.pollLast();
        TypeInfo ti = null;
        if (elem.getKey() instanceof TypeInfo) {
            ti = (TypeInfo) elem.getKey();
        } else {
            ti = new TypeInfo();
            if (elem.getKey() == null) {
                int i = 0;
            }
            ti.setTypeClass(elem.getKey().toString());
        }
        while (!chain.isEmpty()) {
            elem = chain.pollLast();
            if (elem.getValue() == Tree.Kind.MEMBER_SELECT) {
                if (elem.getKey().toString().equals("class")) {
                    ti = new TypeInfo();
                    ti.setTypeClass(Class.class.getName());
                } else if (elem.getKey().toString().equals("this")) {
                } else {
                    Class workClass = null;
                    try {
                        workClass = Class.forName(ti.getTypeClass(), false, ClassLoader.getSystemClassLoader());
                    } catch (ClassNotFoundException ex) {
                        //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Field field = findFieldMatch(elem.getKey().toString(), workClass);
                    if (field == null) {
                        int i = 0;
                    }
                    workClass = field.getType();
                    ti = new TypeInfo();
                    if (workClass.isArray()) {
                        ti.setArray(true);
                        ti.setTypeClass(workClass.getComponentType().getName());
                    } else {
                        ti.setTypeClass(workClass.getName());
                    }
                    ti.setPrimitive(workClass.isPrimitive());
                }

            } else if (elem.getValue() == Tree.Kind.OTHER) { //Nome do método
                break;
            } else {
                return null;
            }
        }
        return ti;
    }

    public static TypeInfo getFieldTypeFromFieldAccessChain(LinkedList<SimpleEntry<Object, Tree.Kind>> chain) {
        //Recupera Identificador
        SimpleEntry<Object, Tree.Kind> elem = chain.pollLast();
        TypeInfo id = (TypeInfo) elem.getKey();
        Class workClass = null;
        if (id.isPrimitive()) {
            workClass = getPrimitiveClassOfPrimitive(id.getTypeClass());
        } else {
            try {
                workClass = Class.forName(id.getTypeClass(), false, ClassLoader.getSystemClassLoader());
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        whileChain:
        while (!chain.isEmpty()) {
            elem = chain.pollLast();
            if (elem.getValue() == Tree.Kind.MEMBER_SELECT) {
                //Verifica se é subclass
                for (Class sub : workClass.getDeclaredClasses()) {
                    if (sub.getSimpleName().equals(elem.getKey().toString())) {
                        workClass = sub;
                        continue whileChain;
                        //return sub.getName();
                    }
                }
                //Verifica se é campo
                Field field = null;
                field = findFieldMatch(elem.getKey().toString(), workClass);
                if (field == null) {
                    int i = 0;
                }
                workClass = field.getType();
            } else if (elem.getValue() == Tree.Kind.OTHER) { //Nome do campo
                if (elem.getKey().toString().equals("this")) {
                    break;
                    //return workClass.getName();
                }
                if (elem.getKey().toString().equals("class")) {
                    workClass = Class.class;
                    break;
                }
                if (id.isArray() && elem.getKey().toString().equals("length")) {
                    workClass = int.class;
                    break;
                }
                //Verifica se é subclass
                for (Class sub : workClass.getDeclaredClasses()) {
                    if (sub.getSimpleName().equals(elem.getKey().toString())) {
                        workClass = sub;
                        break whileChain;
                        //return sub.getName();
                    }
                }
                //Verifica se é campo
                Field field = findFieldMatch(elem.getKey().toString(), workClass);
                workClass = field.getType();
            } else {
                return null;
            }
        }
        TypeInfo type = new TypeInfo();
        type.setTypeClass(workClass.getName());
        type.setArray(workClass.isArray());
        type.setPrimitive(workClass.isPrimitive());
        //if (workClass.getTypeParameters().length > 0) {
        //    type.setGeneric(true);
        //    for (TypeVariable genType : workClass.getTypeParameters()) {
        //
        //    }
        //}
        return type;
    }

    public static Field findFieldMatch(String name, Class clazz) {
        //Localiza método
        //Se publico:
        Field field = null;
        try {
            field = clazz.getField(name);
        } catch (NoSuchFieldException ex) {
            //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (field == null) {
            try {
                //Se privado
                field = clazz.getDeclaredField(name);
            } catch (NoSuchFieldException ex) {
                //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (field == null && clazz.getSuperclass() != null) {
            //Se não encontrou, tenta encontrar na hierarquia de herança
            field = findFieldMatch(name, clazz.getSuperclass());
        }
        if (field == null && clazz.getEnclosingClass() != null) {
            //Se inner class, busca na classe principal
            field = findFieldMatch(name, clazz.getEnclosingClass());
        }
        return field;
    }

    public static Method findMethodMatch(String name, List<Class> parameters, Class clazz) {
        //Localiza método
        //Se publico:
        Method method = null;
        try {
            method = clazz.getMethod(name, parameters.toArray(new Class[0]));
        } catch (NoSuchMethodException ex) {
            //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (method == null) {
            try {
                method = clazz.getDeclaredMethod(name, parameters.toArray(new Class[0]));
            } catch (NoSuchMethodException ex) {
                //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (method == null) {
            method = getMethodMatch(name, parameters, clazz.getMethods());
        }
        if (method == null) {
            //Se privado
            method = getMethodMatch(name, parameters, clazz.getDeclaredMethods());

        }
        if (method == null && clazz.getSuperclass() != null) {
            //Se não encontrou, tenta encontrar na hierarquia de herança
            method = findMethodMatch(name, parameters, clazz.getSuperclass());
        }
        if (method == null && clazz.getEnclosingClass() != null) {
            //Se inner class, busca na classe principal
            method = findMethodMatch(name, parameters, clazz.getEnclosingClass());
        }
        if (method == null && clazz.isInterface()) {
            //Se interface, verifica se não é object
            method = getMethodMatch(name, parameters, Object.class.getMethods());
        }

        return method;
    }

    public static Method getMethodMatch(String name, List<Class> parameters, Method[] methods) {
        Method returnMethod = null;
        forMetodos:
        for (Method mt : methods) {
            if (mt.getName().equals(name)) {
                if (mt.getParameterTypes().length == 0) {
                    if (parameters.isEmpty()) {
                        returnMethod = mt;
                        break forMetodos;
                    } else {
                        continue forMetodos;
                    }
                }
                if (mt.getParameterTypes()[mt.getParameterTypes().length - 1].isArray()) {
                    //Se for varargs
                    int match = 0;
                    for (int i = 0; i < parameters.size(); i++) {
                        Class c1 = parameters.get(i);
                        if (c1 == null) {
                            match++;
                            continue;
                        } else {
                            if (c1.isPrimitive()) {
                                if (c1.isArray()) {
                                    c1 = getArrayClassFromPrimitive(c1.getName());
                                } else {
                                    c1 = getClassFromPrimitive(c1.getName());
                                }
                            }
                            Class c2 = mt.getParameterTypes()[i];
                            if (c2.isPrimitive()) {
                                if (c2.isArray()) {
                                    c2 = getArrayClassFromPrimitive(c2.getName());
                                } else {
                                    c2 = getClassFromPrimitive(c2.getName());
                                }
                            }
                            if (isAssignableFrom(c2, c1)){
                                match++;
                                continue;
                            } 
                        }
                        if (i == mt.getParameterTypes().length - 1) { //Se não ocorreu match, e, for o var args
                            Class c2 = mt.getParameterTypes()[i].getComponentType(); //Captura a classe do array
                            if (c2.isPrimitive()) {
                                c2 = getClassFromPrimitive(c2.getName());
                            }
                            if (isAssignableFrom(c2, c1)) {
                                match++;
                                if (parameters.size() > mt.getParameterTypes().length) {
                                    for (int x = i + 1; x < parameters.size(); x++) {
                                        Class cx = parameters.get(x);
                                        if (cx.isPrimitive()) {
                                            cx = getClassFromPrimitive(cx.getName());
                                        }
                                        if (isAssignableFrom(c2, cx)) {
                                            match++;
                                        }
                                    }
                                }
                                break;
                            }

                        }
                    }
                    if (parameters.size() == match && parameters.size() >= mt.getParameterTypes().length - 1) {
                        returnMethod = mt; //Verifica se fez match com os primeiros parametros
                        break forMetodos;
                    }
                }

                if (mt.getParameterTypes().length == parameters.size()) {
                    int match = 0;
                    for (int i = 0; i < mt.getParameterTypes().length; i++) {
                        Class c1 = parameters.get(i);
                        if (c1 == null) {
                            match++;
                        } else {
                            if (c1.isPrimitive()) {
                                if (c1.isArray()) {
                                    c1 = getArrayClassFromPrimitive(c1.getName());
                                } else {
                                    c1 = getClassFromPrimitive(c1.getName());
                                }
                            }
                            Class c2 = mt.getParameterTypes()[i];
                            if (c2.isPrimitive()) {
                                if (c2.isArray()) {
                                    c2 = getArrayClassFromPrimitive(c2.getName());
                                } else {
                                    c2 = getClassFromPrimitive(c2.getName());
                                }
                            }
                            if (isAssignableFrom(c2, c1)) {
                                match++;
                            }
                        }
                    }
                    if (parameters.size() == match) {
                        returnMethod = mt;
                        break forMetodos;
                    }
                }


            }
        }
        return returnMethod;
    }

    public static void populateMethodInvocationInfo(MethodInvocationInfo methodInvocationInfo, ClassInfo clazzInfo) {
        Class methodClass = null;
        java.lang.reflect.Method method = null;
        List<Class> parametersClass = new ArrayList<Class>();
        try {
            methodClass = Class.forName(methodInvocationInfo.getClazz().getTypeClass(), false, ClassLoader.getSystemClassLoader());
            for (TypeInfo param : methodInvocationInfo.getParameters()) {
                if (param == null || param.getTypeClass() == null) {
                    parametersClass.add(null);
                } else {
                    if (param.isPrimitive()) {
                        if (param.isArray()) {
                            parametersClass.add(getPrimitiveArrayClassOfPrimitive(param.getTypeClass()));
                        } else {
                            parametersClass.add(getPrimitiveClassOfPrimitive(param.getTypeClass()));
                        }
                    } else {
                        StringBuilder clazzName = new StringBuilder();
                        clazzName.append(param.isArray() ? "[L" : "");
                        clazzName.append(param.getTypeClass());
                        clazzName.append((param.isArray() ? ";" : ""));
                        parametersClass.add(Class.forName(clazzName.toString(), false, ClassLoader.getSystemClassLoader()));
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            int i = 0;
            //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        method = findMethodMatch(methodInvocationInfo.getName(), parametersClass, methodClass);
        if (method == null) {
            int i = 0;
        }

        if (!methodInvocationInfo.getClazz().getTypeClass().equals(method.getDeclaringClass().getName())) {
            //Se o método invocado é implementado em outra classe, computa a chamada para a classe em que foi declarado.
            TypeInfo newType = new TypeInfo();
            newType.setTypeClass(method.getDeclaringClass().getName());
            newType.setGeneric(methodInvocationInfo.getClazz().isGeneric());
            newType.getGenericClassArguments().addAll(methodInvocationInfo.getClazz().getGenericClassArguments());
            methodInvocationInfo.setClazz(newType);
        }
        methodInvocationInfo.getParameters().clear(); //Repopula parâmetros
        for (Class param : method.getParameterTypes()) {
            TypeInfo type = new TypeInfo();
            type.setPrimitive(param.isPrimitive());
            if (param.isArray()) {
                type.setArray(true);
                type.setTypeClass(param.getComponentType().getName());
            } else {
                type.setTypeClass(param.getName());
            }
            methodInvocationInfo.getParameters().add(type);
        }
        TypeInfo returntype = new TypeInfo(); //Seta retorno
        if (method.getReturnType().isArray()) {
            returntype.setArray(true);
            returntype.setTypeClass(method.getReturnType().getComponentType().getName());
            returntype.setPrimitive(method.getReturnType().getComponentType().isPrimitive());
        } else {
            returntype.setTypeClass(method.getReturnType().getName());
            returntype.setPrimitive(method.getReturnType().isPrimitive());
        }
        methodInvocationInfo.setReturnType(returntype);
        //Verifica se método é genérico
        if (methodInvocationInfo.getClazz().isGeneric()) {
            //Verifica se é estrutura de dados
            if (java.util.Map.Entry.class.isAssignableFrom(methodClass)) {
                if (methodInvocationInfo.getName().equals("getKey")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(0));
                }
                if (methodInvocationInfo.getName().equals("getValue")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(1));
                }
                if (methodInvocationInfo.getName().equals("setValue")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(1));
                }
            } else if (java.util.Map.class.isAssignableFrom(methodClass)) {
                if (methodInvocationInfo.getName().equals("put")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(1));
                }
                if (methodInvocationInfo.getName().equals("get")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(1));
                }
                if (methodInvocationInfo.getName().equals("remove")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(1));
                }
                if (methodInvocationInfo.getName().equals("keySet")) {
                    TypeInfo key = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo ret = new TypeInfo();
                    ret.setTypeClass(java.util.Set.class.getName());
                    ret.setGeneric(true);
                    ret.getGenericClassArguments().add(key);
                    methodInvocationInfo.setReturnType(ret);
                }
                if (methodInvocationInfo.getName().equals("values")) {
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(1);
                    TypeInfo ret = new TypeInfo();
                    ret.setTypeClass(java.util.Collection.class.getName());
                    ret.setGeneric(true);
                    ret.getGenericClassArguments().add(value);
                    methodInvocationInfo.setReturnType(ret);
                }
                if (methodInvocationInfo.getName().equals("entrySet")) {
                    TypeInfo key = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(1);
                    TypeInfo entry = new TypeInfo();
                    entry.setTypeClass(java.util.Map.Entry.class.getName());
                    entry.setGeneric(true);
                    entry.getGenericClassArguments().add(key);
                    entry.getGenericClassArguments().add(value);
                    TypeInfo ret = new TypeInfo();
                    ret.setTypeClass(java.util.Set.class.getName());
                    ret.setGeneric(true);
                    ret.getGenericClassArguments().add(entry);
                    methodInvocationInfo.setReturnType(ret);
                }
            } else if (java.util.List.class.isAssignableFrom(methodClass)) {
                if (methodInvocationInfo.getName().equals("get")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(0));
                }
                if (methodInvocationInfo.getName().equals("set")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(0));
                }
                if (methodInvocationInfo.getName().equals("remove")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(0));
                }
                if (methodInvocationInfo.getName().equals("iterator")) {
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo ret = new TypeInfo();
                    ret.setTypeClass(java.util.Iterator.class.getName());
                    ret.setGeneric(true);
                    ret.getGenericClassArguments().add(value);
                    methodInvocationInfo.setReturnType(ret);
                }
                if (methodInvocationInfo.getName().equals("toArray") && methodInvocationInfo.getParameters().size() == 1) {
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo type = new TypeInfo();
                    type.setTypeClass(value.getTypeClass());
                    type.setGeneric(value.isGeneric());
                    type.setGenericClassArguments(value.getGenericClassArguments());
                    type.setArray(true);
                    methodInvocationInfo.setReturnType(type);
                }
            } else if (java.util.Set.class.isAssignableFrom(methodClass)) {
                if (methodInvocationInfo.getName().equals("toArray") && methodInvocationInfo.getParameters().size() == 1) {
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo type = new TypeInfo();
                    type.setTypeClass(value.getTypeClass());
                    type.setGeneric(value.isGeneric());
                    type.setGenericClassArguments(value.getGenericClassArguments());
                    type.setArray(true);
                    methodInvocationInfo.setReturnType(type);
                }
                if (methodInvocationInfo.getName().equals("iterator")) {
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo ret = new TypeInfo();
                    ret.setTypeClass(java.util.Iterator.class.getName());
                    ret.setGeneric(true);
                    ret.getGenericClassArguments().add(value);
                    methodInvocationInfo.setReturnType(ret);
                }
            } else if (java.util.Iterator.class.isAssignableFrom(methodClass)) {
                if (methodInvocationInfo.getName().equals("next")) {
                    methodInvocationInfo.setReturnType(methodInvocationInfo.getClazz().getGenericClassArguments().get(0));
                }
            } else if (java.util.Collection.class.isAssignableFrom(methodClass)) {
                if (methodInvocationInfo.getName().equals("iterator")) {
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo ret = new TypeInfo();
                    ret.setTypeClass(java.util.Iterator.class.getName());
                    ret.setGeneric(true);
                    ret.getGenericClassArguments().add(value);
                    methodInvocationInfo.setReturnType(ret);
                }
                if (methodInvocationInfo.getName().equals("toArray") && methodInvocationInfo.getParameters().size() == 1) {
                    TypeInfo value = methodInvocationInfo.getClazz().getGenericClassArguments().get(0);
                    TypeInfo type = new TypeInfo();
                    type.setTypeClass(value.getTypeClass());
                    type.setGeneric(value.isGeneric());
                    type.setGenericClassArguments(value.getGenericClassArguments());
                    type.setArray(true);
                    methodInvocationInfo.setReturnType(type);
                }
            } else {
                int i = 0;
            }

        }
        if (returntype == null) {
            int i = 0;
        }

    }

    public static TypeInfo getTypeFromJCExpression(JCExpression jce, MethodInfo methodInfo, ClassInfo clazzInfo) {
        TypeInfo typeInfo = new TypeInfo();
        if (jce.type != null) {
            return getTypeInfoFromTypeSymbol(jce.type);
        } else {
            if (jce instanceof JCIdent) { //Identificador
                JCIdent jci = (JCIdent) jce;
                return getJCIdentType(jci, methodInfo, clazzInfo); //Captura o tipo
            } else if (jce instanceof JCTypeCast) { //Cast
                JCTypeCast jctc = (JCTypeCast) jce;
                getTypeFromJCExpression(jctc.getExpression(), methodInfo, clazzInfo); //Processa Expressão, pode ser invocação
                JCExpression jce2 = (JCExpression) jctc.clazz;
                return getTypeFromJCExpression(jce2, methodInfo, clazzInfo);
            } else if (jce instanceof JCFieldAccess) {
                JCFieldAccess jcfa = (JCFieldAccess) jce;
                Class clazz = null; //tenta verificar se o field access não se trata de uma declaração de class
                try {
                    clazz = Class.forName(jcfa.toString());
                } catch (ClassNotFoundException ex) {
                    //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (clazz != null) {
                    typeInfo.setTypeClass(clazz.getName());
                    typeInfo.setPrimitive(clazz.isPrimitive());
                    typeInfo.setArray(clazz.isArray());
                } else {
                    typeInfo = getFieldTypeFromFieldAccessChain(getJCFieldAccessChain(jcfa, methodInfo, clazzInfo));
                }
                return typeInfo;
            } else if (jce instanceof JCMethodInvocation) {
                JCMethodInvocation jcmi = (JCMethodInvocation) jce;
                MethodInvocationInfo mif = processMethodInvocation(jcmi, methodInfo, clazzInfo);
                return mif.getReturnType();
            } else if (jce instanceof JCNewClass) {
                JCNewClass jcnc = (JCNewClass) jce;
                ConstructorInvocationInfo cif = processConstructorInvocation(jcnc, methodInfo, clazzInfo);
                return cif.getClazz();
            } else if (jce instanceof JCNewArray) {
                JCNewArray jcna = (JCNewArray) jce;
                if (jcna.elems != null) {
                    for (JCExpression elem : jcna.elems) {
                        getTypeFromJCExpression(elem, methodInfo, clazzInfo); //Verifica se elemento não tem invocação de método
                    }
                }
                TypeInfo type = null;
                if (jcna.elemtype != null) {
                    type = getTypeFromJCExpression(jcna.elemtype, methodInfo, clazzInfo);
                } else {
                    type = getTypeFromJCExpression(jcna.elems.head, methodInfo, clazzInfo);
                }
                type.setArray(true);
                return type;
            } else if (jce instanceof JCArrayTypeTree) {
                JCArrayTypeTree jcatt = (JCArrayTypeTree) jce;
                TypeInfo type = getTypeFromJCExpression(jcatt.elemtype, methodInfo, clazzInfo);
                type.setArray(true);
                //ConstructorInvocationInfo cif = processConstructorInvocation(jcna., methodInfo, clazzInfo);
                return type;
            } else if (jce instanceof JCLiteral) {
                JCLiteral jcl = (JCLiteral) jce;
                TypeInfo type = new TypeInfo();
                if (jcl.toString().equals("true") || jcl.toString().equals("false")) { //Se booleano
                    type.setTypeClass(boolean.class.getName());
                    type.setPrimitive(true);
                } else if (jcl.toString().startsWith("'") && jcl.toString().endsWith("'")) {
                    type.setTypeClass(char.class.getName());
                    type.setPrimitive(true);
                } else if (jcl.toString().equals("null")) {
                    type.setTypeClass(null);
                } else {
                    if (jcl.value == null) {
                        type.setTypeClass(null);
                    } else {
                        try{
                            Integer.parseInt(jcl.value.toString());
                            type.setTypeClass(int.class.getName());
                            type.setPrimitive(true);
                            return type;
                        } catch(NumberFormatException ex){}
                        type.setTypeClass(jcl.value.getClass().getName());
                        type.setPrimitive(jcl.value.getClass().isPrimitive());
                    }
                }
                return type;
            } else if (jce instanceof JCBinary) {
                JCBinary jcb = (JCBinary) jce;
                TypeInfo left = getTypeFromJCExpression(jcb.getLeftOperand(), methodInfo, clazzInfo);
                TypeInfo right = getTypeFromJCExpression(jcb.getRightOperand(), methodInfo, clazzInfo);
                if ((jcb.toString().indexOf("==") >= 0
                        || jcb.toString().indexOf("!=") >= 0
                        || jcb.toString().indexOf(">") >= 0
                        || jcb.toString().indexOf(">=") >= 0
                        || jcb.toString().indexOf("<") >= 0
                        || jcb.toString().indexOf("<=") >= 0)
                        && (jcb.toString().indexOf(">>>") < 0)) {
                    TypeInfo type = new TypeInfo();
                    type.setPrimitive(true);
                    type.setTypeClass(boolean.class.getName());
                    return type;
                }
                if (left.getTypeClass() != null) {
                    return left;
                } else {
                    return right;
                }
            } else if (jce instanceof JCArrayAccess) {
                JCArrayAccess jcaa = (JCArrayAccess) jce;
                getTypeFromJCExpression(jcaa.getIndex(), methodInfo, clazzInfo);
                TypeInfo type = getTypeFromJCExpression(jcaa.getExpression(), methodInfo, clazzInfo);
                type.setArray(false);
                return type;
            } else if (jce instanceof JCPrimitiveTypeTree) {
                JCPrimitiveTypeTree jcpt = (JCPrimitiveTypeTree) jce;
                TypeInfo type = new TypeInfo();
                type.setPrimitive(true);
                type.setTypeClass(jcpt.toString());
                return type;
            } else if (jce instanceof JCConditional) {
                JCConditional jcc = (JCConditional) jce;
                TypeInfo cond = getTypeFromJCExpression(jcc.cond, methodInfo, clazzInfo);
                TypeInfo truep = getTypeFromJCExpression(jcc.truepart, methodInfo, clazzInfo);
                TypeInfo falsep = getTypeFromJCExpression(jcc.falsepart, methodInfo, clazzInfo);
                if (truep.getTypeClass() != null) {
                    return truep;
                } else {
                    return falsep;
                }
            } else if (jce instanceof JCParens) {
                JCParens jcp = (JCParens) jce;
                return getTypeFromJCExpression(jcp.expr, methodInfo, clazzInfo);
            } else if (jce instanceof JCUnary) {
                JCUnary jcu = (JCUnary) jce;
                TypeInfo type = getTypeFromJCExpression(jcu.arg, methodInfo, clazzInfo); //Processa, pode ser method invocation
                return type;
            } else if (jce instanceof JCAssign) {
                JCAssign jcas = (JCAssign) jce;
                TypeInfo esq = getTypeFromJCExpression(jcas.lhs, methodInfo, clazzInfo);
                TypeInfo right = getTypeFromJCExpression(jcas.rhs, methodInfo, clazzInfo);
                return esq;
            } else if (jce instanceof JCTypeApply) {
                JCTypeApply jcta = (JCTypeApply) jce;
                TypeInfo type = getTypeFromJCExpression(jcta.clazz, methodInfo, clazzInfo);
                type.setGeneric(true);
                for (JCExpression jcearg : jcta.arguments) {
                    type.getGenericClassArguments().add(getTypeFromJCExpression(jcearg, methodInfo, clazzInfo));
                }
                return type;
            } else {
                return null;
            }
        }
    }

    public static TypeInfo getJCIdentType(JCIdent jci, MethodInfo methodInfo, ClassInfo clazzInfo) {
        if (methodInfo != null) {
            if (methodInfo.getParameters().containsKey(jci.name.toString())) { //Verifica se é parâmetro é do método
                return methodInfo.getParameters().get(jci.name.toString());
            } else if (methodInfo.getLocalVariables().containsKey(jci.name.toString())) { //Verifica se é varíavel local do método
                return methodInfo.getLocalVariables().get(jci.name.toString());
            }
        }
        if (clazzInfo.getFieldsMap().containsKey(jci.name.toString())) { //Verifica se é atributo da classe
            return clazzInfo.getField(jci.name.toString()).getFieldType();
        } else if (clazzInfo.getNameOfSuperClass() != null) {
            Class superClass = null;
            try {
                superClass = Class.forName(clazzInfo.getNameOfSuperClass());
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(CodeAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            Field field = findFieldMatch(jci.name.toString(), superClass);
            if (field != null) {
                TypeInfo type = new TypeInfo();
                if (field.getType().isArray()) {
                    type.setArray(true);
                    type.setTypeClass(field.getType().getComponentType().getName());
                    type.setPrimitive(field.getType().getComponentType().isPrimitive());
                } else {
                    type.setTypeClass(field.getType().getName());
                    type.setPrimitive(field.getType().isPrimitive());
                }
                return type;
            } else {
                return getClassNameFromName(jci.getName(), clazzInfo);
            }
        } else {
            return getClassNameFromName(jci.getName(), clazzInfo);
        }
    }

    public static TypeInfo getTypeInfoFromTypeSymbol(Type t) {
        TypeInfo type = new TypeInfo();
        if (t instanceof ArrayType) {
            ArrayType at = (ArrayType) t;
            type.setArray(true);
            type.setPrimitive(at.elemtype.isPrimitive());
            type.setTypeClass(at.elemtype.tsym.flatName().toString());
        } else if (t instanceof ClassType) {
            ClassType ct = (ClassType) t;
            type.setTypeClass(ct.tsym.flatName().toString());
            if (ct.getParameterTypes().size() > 0) {
                type.setGeneric(true);
                for (Type param : ct.typarams_field) {
                    type.getGenericClassArguments().add(getTypeInfoFromTypeSymbol(param));
                }
            }
        } else if (t instanceof TypeVar) { //Generic
            TypeVar tv = (TypeVar) t;
            type.setTypeClass(tv.bound.tsym.flatName().toString());
            type.setGeneric(true);
        } else if (t instanceof Type) {
            type.setPrimitive(true);
            type.setTypeClass(t.tsym.flatName().toString());
        } else {
            type.setTypeClass("Símbolo do parâmetro não reconhecido");
        }
        return type;
    }
    
    public static boolean isAssignableFrom(Class c1, Class c2){
        if(c1.isAssignableFrom(c2)){
            return true;
        }
        Set<Class> strings = new HashSet<Class>();
        strings.add(char.class);
        strings.add(String.class);
        strings.add(Character.class);

        if(strings.contains(c1) && strings.contains(c2)){
            return true;
        }
        
        Set<Class> inteiros = new HashSet<Class>();
        inteiros.add(int.class);
        inteiros.add(Integer.class);
        inteiros.add(long.class);
        inteiros.add(Long.class);
        inteiros.add(byte.class);
        inteiros.add(Byte.class);
        inteiros.add(char.class);
        
        if(inteiros.contains(c1) && inteiros.contains(c2)){
            return true;
        }
        
        Set<Class> flutuantes = new HashSet<Class>();
        flutuantes.add(float.class);
        flutuantes.add(Float.class);
        flutuantes.add(Double.class);
        flutuantes.add(double.class);
        
        if(flutuantes.contains(c1) && flutuantes.contains(c2)){
            return true;
        }
        return false;

        
    }
}
