/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.Invocacao;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class ENT {
    public static double calcular(int countClasses, Group C){
        Set<Integer> classesDistintas = new HashSet<Integer>();
        for(Metodo m : C.metodos){
            classesDistintas.add(m.getClasse().getIdClass());
        }
        return (double)classesDistintas.size()/(double)countClasses;
    }
}
