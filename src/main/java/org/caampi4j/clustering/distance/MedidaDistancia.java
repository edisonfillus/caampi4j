/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.distance;

import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public interface MedidaDistancia {
    
    public double calcularDistancia(Metodo m1, Metodo m2);
        
}
