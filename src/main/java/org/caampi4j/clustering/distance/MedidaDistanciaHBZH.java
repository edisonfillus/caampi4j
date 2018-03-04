/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.distance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.caampi4j.model.Invocacao;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class MedidaDistanciaHBZH implements MedidaDistancia{
    
   private static Double[][] cacheArr = new Double[10500][10500];
    
    
    @Override
    public double calcularDistancia(Metodo m1, Metodo m2){
        //Cache
        if(m1.getIdMetodo() == m2.getIdMetodo()){
            return 0.0d;
        }
        Double resultCache = cacheArr[m1.getIdMetodo()][m2.getIdMetodo()];
        if(resultCache != null){
            return resultCache;
        }
        resultCache = cacheArr[m2.getIdMetodo()][m1.getIdMetodo()]; //Verifica o inverso
        if(resultCache != null){
            return resultCache;
        }
        Set<Integer> colM1 = getCollectionAMUCAFromM(m1);
        Set<Integer> colM2 = getCollectionAMUCAFromM(m2);
        
        if(colM1.isEmpty() && colM2.isEmpty()){
            return 0.0d;
        }
        Set<Integer> q = new HashSet<Integer>(colM1); //q = intersecção
        q.retainAll(colM2);
        Set<Integer> r = new HashSet<Integer>(colM1); //r = tudo de 1 que nao esta em 2
        r.removeAll(colM2);
        Set<Integer> s = new HashSet<Integer>(colM2); //s = tudo de 2 que nao esta em 1
        s.removeAll(colM1);
        double result = (double)(r.size()+s.size())/(double)(q.size()+r.size()+s.size());
        cacheArr[m1.getIdMetodo()][m2.getIdMetodo()] = result;
        return result;
    }
    
    private static Set<Integer> getCollectionAMUCAFromM(Metodo m){
        Set<Integer> colM = new HashSet<Integer>();
        for(Invocacao inv : m.getInvocadores()){
            colM.add(inv.getOwner().getIdMetodo()); //Métodos que invocaram m1
        }
        return colM;
    }
}
