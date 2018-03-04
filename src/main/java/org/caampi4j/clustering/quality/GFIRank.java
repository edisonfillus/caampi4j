/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class GFIRank {
        public static double calcular(Group K, MedidaDistancia medida) {
            int points = 0;
            for(Metodo m : K.metodos){
                points += m.getFanIn().size();
            }
            return points * Interconectividade.calcular(K, medida);
        }
}
