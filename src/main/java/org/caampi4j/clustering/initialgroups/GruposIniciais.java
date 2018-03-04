/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.initialgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class GruposIniciais {

    public static HashMap<Metodo, Double> candidatos = new HashMap<Metodo, Double>();
    public static List<Metodo> naoRepresentativos = new ArrayList<Metodo>();
    public static List<Metodo> representativos = new ArrayList<Metodo>();

    public static List<Metodo> selecionaMetodosRepresentativos(Collection<Metodo> metodos, MedidaDistancia medida) {
        naoRepresentativos.addAll(metodos);
        Metodo primeiro = selecionaMetodoComMaiorDistanciaEntreTodos(metodos, medida);
        representativos.add(primeiro);
        naoRepresentativos.remove(primeiro);
        if(org.caampi4j.main.Main.SHOW_CLUSTERING_PROCESS){
            System.out.println("Representativo Selecionado: " + representativos.size());
        }
        //Verifica os candidados a método representativo
        for (Metodo naoRepresentativo : naoRepresentativos) {
            double minDistanceRepresentivo = Double.MAX_VALUE; //Armazena a distância do método representativo mais próximo
            for (Metodo representativo : representativos) {
                double distancia = medida.calcularDistancia(naoRepresentativo, representativo);
                if (distancia < minDistanceRepresentivo) {
                    minDistanceRepresentivo = distancia;
                }
            }
            if (minDistanceRepresentivo > org.caampi4j.main.Main.DIST_MIN) {
                candidatos.put(naoRepresentativo, minDistanceRepresentivo);
            }
        }
        while (true) { //Seleciona
            Metodo novoRepresentativo = null;
            double maxDistanceRepresentativo = 0.0d;
            for (Entry<Metodo, Double> entry : candidatos.entrySet()) {
                if (entry.getValue() > maxDistanceRepresentativo) {
                    novoRepresentativo = entry.getKey();
                    maxDistanceRepresentativo = entry.getValue();
                }
            }
            if (novoRepresentativo != null) {
                representativos.add(novoRepresentativo);
                naoRepresentativos.remove(novoRepresentativo);
                candidatos.remove(novoRepresentativo);
                List<Metodo> candidatosToRemove = new ArrayList<Metodo>();
                //Recalcula distancias
                for (Entry<Metodo, Double> entry : candidatos.entrySet()) {
                    double distancia = medida.calcularDistancia(entry.getKey(), novoRepresentativo);
                    if (distancia < entry.getValue()) {
                        entry.setValue(distancia);
                    }
                    if(distancia < org.caampi4j.main.Main.DIST_MIN){
                        candidatosToRemove.add(entry.getKey());
                    }
                }
                //Remove candidatos com distancia menor que dmin
                for(Metodo candidatoToRemove : candidatosToRemove){
                    candidatos.remove(candidatoToRemove);
                }
                if(org.caampi4j.main.Main.SHOW_CLUSTERING_PROCESS){
                    System.out.println("Representativo Selecionado: " + representativos.size());
                }
            } else {
                break;
            }
        }
        naoRepresentativos = null; //Limpa lista
        candidatos = null; //limpa lista
        List<Metodo> representativosReturn = representativos; //Transfere para não estático
        representativos = null;
        return representativosReturn;
    }

   
    private static Metodo selecionaMetodoComMaiorDistanciaEntreTodos(Collection<Metodo> metodos, MedidaDistancia medida) {
        Metodo maxMethod = null;
        double maxDistance = 0.0; //Armazena a distância do método com maior distância
        for (Metodo m1 : metodos) {
            double distM1 = 0.0d;
            for (Metodo m2 : metodos) {
                if (m1.getIdMetodo() != m2.getIdMetodo()) {
                    distM1 += medida.calcularDistancia(m1, m2);
                }
            }
            distM1 = distM1 / (metodos.size() - 1); //Calcula a distância
            if (distM1 > maxDistance) {
                maxDistance = distM1;
                maxMethod = m1;
            }
        }
        return maxMethod;
    }
}
