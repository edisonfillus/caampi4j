/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.ast.processor;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

/**
 *
 * @author Edison
 */

public class MethodAnalyserTreeVisitor extends TreePathScanner<Object, Trees> {

    @Override
    public Object visitMethodInvocation(MethodInvocationTree mit, Trees p) {
        int i =0;
        return super.visitMethodInvocation(mit, p);
    }
   

}
