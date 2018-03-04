/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.initialgroups.GruposIniciais;
import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class ClusteringHierarquico implements Clustering {
    
      
    private Double[][] cacheArr = new Double[5000][5000];
    
    //private Map<CacheDistancia,Double> mapCache = new HashMap<CacheDistancia,Double>();
    
    public double completeLinkageDistance(Group c1, Group c2, MedidaDistancia medida){
        Double resultCache = cacheArr[c1.idGroup][c2.idGroup];
        if(resultCache != null){
            return resultCache;
        }
        double maxDistance = Double.MIN_VALUE;
        for(Metodo mc1 : c1.metodos){
            for(Metodo mc2 : c2.metodos){
                double dist = medida.calcularDistancia(mc1, mc2);
                if(dist > maxDistance){
                    maxDistance = dist;
                }
            }
        }
        cacheArr[c1.idGroup][c2.idGroup] = maxDistance;
        return maxDistance;
    }
    public List<Group> execute(ASTModel astModel, MedidaDistancia medida) {
        //Heurística para identificação dos métodos representativos iniciais
        List<Metodo> representativos = GruposIniciais.selecionaMetodosRepresentativos(astModel.metodos.values(), medida);

        //Inicializa Grupos
        List<Group> grupos = new ArrayList<Group>();
        int idGroup = 0;
        for (Metodo m : astModel.metodos.values()) {
            Group c = new Group();
            c.idGroup = ++idGroup;
            c.metodos.add(m);
            grupos.add(c);
        }
        int iteracoes = 0;
        while (grupos.size() > representativos.size()){
            Group Ki = null;
            Group Kj = null;
            double distMinKiKj = Double.MAX_VALUE;
            for(int i = 0; i < grupos.size() ; i++){
                for(int j = 0; j < grupos.size(); j++){
                    if(i==j){
                        continue;
                    }
                    double dist_ij = completeLinkageDistance(grupos.get(i), grupos.get(j), medida);
                    if(dist_ij < distMinKiKj){
                        distMinKiKj = dist_ij;
                        Ki = grupos.get(i);
                        Kj = grupos.get(j);
                    }
                }
            }
            Group Kij = new Group();
            Kij.idGroup = ++idGroup;
            Kij.metodos.addAll(Ki.metodos);
            Kij.metodos.addAll(Kj.metodos);
            cacheArr[Ki.idGroup][Kj.idGroup] = null; //Zera Cache
            grupos.remove(Ki);
            grupos.remove(Kj);
            grupos.add(Kij);
            if(org.caampi4j.main.Main.SHOW_CLUSTERING_PROCESS){
                System.out.println("Fim Iteração " + ++iteracoes);
            }
        }
            
        return grupos;
        
    }

}
