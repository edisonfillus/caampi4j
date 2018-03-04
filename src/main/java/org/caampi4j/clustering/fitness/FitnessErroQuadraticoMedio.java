/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.fitness;

import java.util.ArrayList;
import java.util.List;

import org.caampi4j.clustering.solution.Group;

/**
 *
 * @author Edison
 */
public class FitnessErroQuadraticoMedio implements Fitness{

    @Override
    public double calculaFitness(List<Group> grupos) {
        return 0.0d;
    }
    
    private double[] calculaCentroideGrupo(Group grupo){
        List<Integer> dimensoes = new ArrayList<Integer>();
        
        return null;
    }
    
}
