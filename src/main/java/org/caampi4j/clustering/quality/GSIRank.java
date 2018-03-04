/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.solution.Group;

/**
 *
 * @author Edison
 */
public class GSIRank {
        public static double calcular(Group K, MedidaDistancia medida) {
            return Espalhamento.calcular(K) * Interconectividade.calcular(K, medida);
        }
}
