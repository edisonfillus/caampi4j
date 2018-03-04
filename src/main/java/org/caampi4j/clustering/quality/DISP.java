/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import java.util.List;

import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class DISP {

    public static double calcular(List<Group> K, List<Group> CCC) {
        double result = 0.0d;
        for (Group C : CCC) {
            result += dispersao(C, K);
        }
        result = (1.0d/(double)CCC.size()) * result;
        return result;
    }

    private static double dispersao(Group C, List<Group> K) {
        int countGroups = 0;
        forGrupos:
        for (Group k : K) {
            for (Metodo e : C.metodos) {
                if (k.metodos.contains(e)) {
                    countGroups++;
                    continue forGrupos;
                }
            }
        }
        if(countGroups == 0){
            return 0;
        }
        double result = 1.0d/(double)countGroups;
        return result;
    }
}
