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
public class MedidaDistanciaGAM implements MedidaDistancia {

    private static Map<CacheDistancia,Double> mapCache = new HashMap<CacheDistancia,Double>();
    
    private class CacheDistancia{
        int idM1;
        int idM2;

        public CacheDistancia(int idM1, int idM2) {
            this.idM1 = idM1;
            this.idM2 = idM2;
        }
        
        @Override
        public boolean equals(Object obj) {
            final CacheDistancia other = (CacheDistancia) obj;
            if((this.idM1 == other.idM1 && this.idM2 == other.idM2)
             ||(this.idM1 == other.idM2 && this.idM2 == other.idM1)){
                return true;
            } else {
                return false;
            } 
        }

        @Override
        public int hashCode() {
            return this.idM1 + this.idM2;
        }
        
        
    }
    
    
    @Override
    public double calcularDistancia(Metodo m1, Metodo m2){
        //Cache
        CacheDistancia cache = new CacheDistancia(m1.getIdMetodo(),m2.getIdMetodo());
        if(mapCache.containsKey(cache)){
            return mapCache.get(cache);
        }
        Set<Integer> colM1 = getCollectionGAMFromM(m1);
        Set<Integer> colM2 = getCollectionGAMFromM(m2);
        Set<Integer> union = new HashSet<Integer>(colM1);
        union.addAll(colM2);
        double result = Math.pow(m1.getFanIn().size() - m2.getFanIn().size(), 2.0d);
        for (Integer cl : union) {
            if (!colM1.contains(cl) || !colM2.contains(cl)) {
                result += 1;
            }
        }
        result = Math.sqrt(result);
        mapCache.put(cache, result);
        return result;
    }

    private static Set<Integer> getCollectionGAMFromM(Metodo m) {
        Set<Integer> colM = new HashSet<Integer>();
        for (Invocacao inv : m.getInvocadores()) {
            colM.add(inv.getOwner().getClasse().getIdClass()); //Classe do método que invocou
        }
        return colM;
    }
}
