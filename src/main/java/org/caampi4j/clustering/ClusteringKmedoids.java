/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.initialgroups.GruposIniciais;
import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class ClusteringKmedoids implements Clustering {

    public List<Group> execute(ASTModel astModel, MedidaDistancia medida) {
        //Heurística para identificação dos medoids iniciais
        List<Metodo> medoids = GruposIniciais.selecionaMetodosRepresentativos(astModel.metodos.values(), medida);
        List<Metodo> naomedoids = new ArrayList<Metodo>(astModel.metodos.values());
        naomedoids.removeAll(medoids);

        //Inicializa Grupos
        HashMap<Metodo, Group> grupos = new HashMap<Metodo, Group>();
        for (Metodo medoid : medoids) {
            grupos.put(medoid, new Group());
        }
        
        boolean changed = false;
        int iteracoes = 0;
        do{
            changed = false;
            //Limpa Grupos
            for (Metodo medoid : medoids) {
                grupos.put(medoid, new Group());
            }
            //Associa elementos ao medoid mais próximo
            for (Metodo elem : naomedoids) {
                double distMinMedoid = Double.MAX_VALUE;
                Metodo medoidDistMin = null;
                for (Metodo medoid : medoids) {
                    double distanciaCentroide = medida.calcularDistancia(elem, medoid);
                    if(distanciaCentroide < distMinMedoid){
                        distMinMedoid = distanciaCentroide;
                        medoidDistMin = medoid;
                    }
                }
                grupos.get(medoidDistMin).metodos.add(elem);
            }

            //Remove medoids com grupos vazios
            List<Metodo> medoidsToRemove = new ArrayList<Metodo>();
            for(Metodo medoid : medoids){
                if(grupos.get(medoid).metodos.isEmpty()){
                    medoidsToRemove.add(medoid);
                }
            }
            if(!medoidsToRemove.isEmpty()){
                for(Metodo medoidToRemove : medoidsToRemove){
                    grupos.remove(medoidToRemove);
                    medoids.remove(medoidToRemove);
                    naomedoids.add(medoidToRemove);
                }
            }
            //Troca medoids se configuração tiver menor custo
            for(Metodo medoid : medoids){
                for(Metodo h : grupos.get(medoid).metodos){
                    double dist = 0.0d;
                    for(Metodo j : medoids){
                        double dist_jh = medida.calcularDistancia(j, h);
                        double dist_jm = medida.calcularDistancia(j, medoid);
                        double dist_dif = dist_jh - dist_jm;
                        dist += dist_dif;
                    }
                    if(dist < 0.0d){
                        medoids.set(medoids.indexOf(medoid), h);
                        naomedoids.remove(h);
                        naomedoids.add(medoid);
                        grupos.remove(medoid);
                        grupos.put(h,new Group());
                        changed = true;
                        break;
                    }
                }

            }
            if(org.caampi4j.main.Main.SHOW_CLUSTERING_PROCESS){
                System.out.println("Fim Iteração " + ++iteracoes);
            }
        } while (changed);
        
        for(Entry<Metodo,Group> ent : grupos.entrySet()){
            ent.getValue().metodos.add(ent.getKey());
        }
        
        return new ArrayList<Group>(grupos.values());
        
    }

}
