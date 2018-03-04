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
public class MedidaDistanciaAMUCACustom implements MedidaDistancia {

    private static Double[][] cacheArr = new Double[10000][10000];

    @Override
    public double calcularDistancia(Metodo m1, Metodo m2) {
        //Cache
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
        Set<Integer> colM1Scater = getCollectionAMUCAFromM(m1);
        Set<Integer> colM2Scater = getCollectionAMUCAFromM(m2);
        Set<Integer> colM1Tangling = getCollectionTanglingFromM(m1);
        Set<Integer> colM2Tangling = getCollectionTanglingFromM(m2);

        double scater = 0.0d;
        if (colM1Scater.isEmpty() && colM2Scater.isEmpty()) {
            scater = 0.0d;
        } else {
            Set<Integer> qs = new HashSet<Integer>(colM1Scater); //q = intersecção
            qs.retainAll(colM2Scater);
            Set<Integer> rs = new HashSet<Integer>(colM1Scater); //r = tudo de 1 que nao esta em 2
            rs.removeAll(colM2Scater);
            Set<Integer> ss = new HashSet<Integer>(colM2Scater); //s = tudo de 2 que nao esta em 1
            ss.removeAll(colM1Scater);
            scater = (double) (rs.size() + ss.size()) / (double) (qs.size() + rs.size() + ss.size());
        }



        double tangling = 0.0d;
        if (colM1Tangling.isEmpty() && colM2Tangling.isEmpty()) {
            tangling = 0.0d;
        } else {
            Set<Integer> qt = new HashSet<Integer>(colM1Tangling); //q = intersecção
            qt.retainAll(colM2Tangling);
            Set<Integer> rt = new HashSet<Integer>(colM1Tangling); //r = tudo de 1 que nao esta em 2
            rt.removeAll(colM2Tangling);
            Set<Integer> st = new HashSet<Integer>(colM2Tangling); //s = tudo de 2 que nao esta em 1
            st.removeAll(colM1Tangling);
            tangling = (double) (rt.size() + st.size()) / (double) (qt.size() + rt.size() + st.size());
        }

        double result = scater * 0.8d + tangling * 0.2d;

        cacheArr[m1.getIdMetodo()][m2.getIdMetodo()] = result;
        return result;
    }

    private static Set<Integer> getCollectionAMUCAFromM(Metodo m) {
        Set<Integer> colM = new HashSet<Integer>();
        for (Metodo inv : m.getFanIn()) {
            colM.add(inv.getIdMetodo());
        }
        return colM;
    }

    private static Set<Integer> getCollectionTanglingFromM(Metodo m) {
        Set<Integer> colM = new HashSet<Integer>();
        for (Invocacao inv : m.getInvocacoes()) {
            colM.add(inv.getMetodoInvocado().getIdMetodo());
        }
        for (Metodo mover : m.getMetodosSobreescrevidos()) {
            for (Invocacao inv : mover.getInvocacoes()) {
                colM.add(inv.getMetodoInvocado().getIdMetodo());
            }
        }
        for (Metodo mref : m.getMetodosRefinados()) {
            for (Invocacao inv : mref.getInvocacoes()) {
                colM.add(inv.getMetodoInvocado().getIdMetodo());
            }
        }

        return colM;
    }
}
