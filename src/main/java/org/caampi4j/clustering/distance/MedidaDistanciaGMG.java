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
public class MedidaDistanciaGMG implements MedidaDistancia {

    private static Double[][] cacheArr = new Double[10500][10500];
    private static Map<Integer, Set<Integer>> colCache = new HashMap<Integer, Set<Integer>>();

    @Override
    public double calcularDistancia(Metodo m1, Metodo m2) {
        if (m1.getIdMetodo() == m2.getIdMetodo()) {
            return 0.0d;
        }
        Double resultCache = cacheArr[m1.getIdMetodo()][m2.getIdMetodo()];
        if (resultCache != null) {
            return resultCache;
        }
        resultCache = cacheArr[m2.getIdMetodo()][m1.getIdMetodo()]; //Verifica o inverso
        if (resultCache != null) {
            return resultCache;
        }
        Set<Integer> colM1 = getCollectionHACOFromM(m1);
        Set<Integer> colM2 = getCollectionHACOFromM(m2);
        Set<Integer> intersection = new HashSet<Integer>(colM1);
        intersection.retainAll(colM2);
        if (intersection.isEmpty()) {
            cacheArr[m1.getIdMetodo()][m2.getIdMetodo()] = 1.00d;
            return 1.00d;
        }
        Set<Integer> union = new HashSet<Integer>(colM1);
        union.addAll(colM2);
        double result = 1.0d - ((double) intersection.size() / (double) union.size());
        cacheArr[m1.getIdMetodo()][m2.getIdMetodo()] = result;
        return result;
    }

    private static Set<Integer> getCollectionHACOFromM(Metodo m) {
        Set<Integer> cache = colCache.get(m.getIdMetodo());
        if (cache != null) {
            return cache;
        }
        Set<Integer> colM = new HashSet<Integer>();
        colM.add(m.getIdMetodo()); //Método em Si
        colM.add(-m.getClasse().getIdClass()); //Classe em que o método esta contido
        if (m.getClasse().isInnerClass()) {//Se inner classe, classe que está contido
            colM.add(-m.getClasse().getEnclosureClass().getIdClass());
        }
        for (Invocacao inv : m.getInvocadores()) {
            colM.add(inv.getOwner().getIdMetodo()); //Métodos que invocaram m1
            colM.add(-inv.getOwner().getClasse().getIdClass()); //Classe que invocaram m1
            if (inv.getOwner().getClasse().isInnerClass()) {
                colM.add(-inv.getOwner().getClasse().getEnclosureClass().getIdClass());
            }
        }
        colCache.put(m.getIdMetodo(), colM);
        return colM;
    }
}
