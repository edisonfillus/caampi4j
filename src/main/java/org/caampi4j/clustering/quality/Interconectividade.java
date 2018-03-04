/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import java.util.List;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class Interconectividade {

    public static double calcular(Group K, MedidaDistancia medida) {
        if(K.metodos.size() == 1){
            return 1.0d;
        }
        double result = 0.0d;
        int qtd = 0;
        for (Metodo m1 : K.metodos) {
            for(Metodo m2 : K.metodos){
                if(m1.getIdMetodo() != m2.getIdMetodo()){
                    result += medida.calcularDistancia(m1, m2);
                    qtd++;
                }
            }
        }
        
        
        result = result/qtd;
        return 1 - result;
    }

    
}
