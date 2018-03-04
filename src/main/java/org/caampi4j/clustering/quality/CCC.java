/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import java.util.ArrayList;
import java.util.List;

import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class CCC {
    public static double indice(Group K, List<Metodo> CCC){
        int qtd = 0;
        for(Metodo m : K.metodos){
            if(CCC.contains(m)){
                qtd++;
            }
        }
        return (double)qtd/(double)K.metodos.size();
    }
}
