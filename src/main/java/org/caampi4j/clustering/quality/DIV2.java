/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class DIV2 {

    public static double calcular(List<Group> K, List<Group> CCC) {
        double result = 0.0d;
        int count = K.size();
        for (Group k : K) {
            double div = diversidade(CCC, k);
            if(div ==0.0d){
                count--;
            } else {
                result += div;
            }
        }
        result = (1.0d / (double) count) * result;
        return result;
    }

    private static double diversidade(List<Group> CCC, Group k) {
        int NCCC = 0;
        Set<Group> ITS = new HashSet<Group>();
        forMetodos:
        for (Metodo m : k.metodos) {
            for (Group C : CCC) {
                if (C.metodos.contains(m)) {
                    ITS.add(C);
                    continue forMetodos;
                }
            }
            NCCC++; //Se não pertence a nenhum CCC
        }
        if(NCCC == k.metodos.size()){
            return 0.0d;
        }
       
        double result = 1.0d / ((double) ITS.size() + ((NCCC>0)?1.0d:0.0d));
        return result;
    }
}
