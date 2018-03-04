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
public class Espalhamento {
    public static double calcular(Group C){
        Set<Integer> classesDistintos = new HashSet<Integer>();
        Set<Integer> metodosDistingos = new HashSet<Integer>();
        for(Metodo m : C.metodos){
            for(Metodo inv : m.getFanIn()){
                classesDistintos.add(inv.getClasse().getIdClass());
                metodosDistingos.add(inv.getIdMetodo());
            }
            classesDistintos.add(m.getClasse().getIdClass());
        }
        double result = classesDistintos.size()+metodosDistingos.size();
        return result;
    }
}
