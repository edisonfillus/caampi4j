/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering;

import java.util.HashMap;
import java.util.Map.Entry;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.initialgroups.GruposIniciais;
import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Classe;
import org.caampi4j.model.Invocacao;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class ClusteringKmeans implements Clustering {

    public void execute(ASTModel astModel, MedidaDistancia medida) {
        //Cria espaço vetorial
        int[] dimensoes = new int[astModel.classes.size()];
        int c = 0;
        for (Classe cl : astModel.classes.values()) {
            dimensoes[c++] = cl.getIdClass();
        }
        HashMap<Integer, Double[]> elementos = new HashMap<Integer, Double[]>();
        for (Metodo m : astModel.metodos.values()) {
            Double[] dimElemento = new Double[dimensoes.length];
            forDimensoes:
            for (int i = 0; i < dimensoes.length; i++) {
                dimElemento[i] = 0.0d;
                //Popula cada dimensão
                for (Invocacao inv : m.getInvocadores()) {
                    if (inv.getOwner().getClasse().getIdClass() == dimensoes[i]) {
                        dimElemento[i] = 1.0d;
                        continue forDimensoes;
                    }
                }
            }
            elementos.put(m.getIdMetodo(), dimElemento);
        }

        //Heurística para identificação de quantidade de grupos
        int numGrupos = GruposIniciais.selecionaMetodosRepresentativos(astModel.metodos.values(), medida).size();

        //Cria centroides iniciais
        HashMap<Integer, Double[]> centroides = new HashMap<Integer, Double[]>();
        for (int g = 0; g < numGrupos; g++) {
            Double[] dimCentroide = new Double[dimensoes.length];
            for (int d = 0; d < dimensoes.length; d++) {
                dimCentroide[d] = Math.random();
            }
            centroides.put(g, dimCentroide);
        }


        //Inicializa Grupos
        HashMap<Integer, Group> grupos = new HashMap<Integer, Group>();
        for (int g = 0; g < numGrupos; g++) {
            grupos.put(g, new Group());
        }
        
        
        //Associa elementos ao centróide mais próximo
        for (Entry<Integer, Double[]> elemento : elementos.entrySet()) {
            double distMinCentroid = Double.MAX_VALUE;
            int idCentroidDistMin = 0;
            for (int g = 0; g < numGrupos; g++) {
                Double[] centroide = centroides.get(g);
                double distanciaCentroide = calculaDistanciaEuclidiana(elemento.getValue(), centroide);
                if(distanciaCentroide < distMinCentroid){
                    idCentroidDistMin = g;
                }
            }
            grupos.get(idCentroidDistMin).metodos.add(astModel.metodos.get(idCentroidDistMin));
        }
    }
    
    private double calculaDistanciaEuclidiana(Double[] e1,Double[] e2){
        int dim = e1.length;
        double result = 0.0d;
        for(int i = 0; i < dim ; i++){
            double dif = e1[i] - e2[i];
            if(dif < 0){ //somente positivo
                dif = -dif;
            }
            result += Math.pow(dif, 2);
        }
        result = Math.sqrt(result);
        return result;
    }
}
