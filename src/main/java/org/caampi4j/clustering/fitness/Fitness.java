/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.fitness;

import java.util.List;

import org.caampi4j.clustering.solution.Group;

/**
 *
 * @author Edison
 */
public interface Fitness {
    public double calculaFitness(List<Group> grupos);
}
