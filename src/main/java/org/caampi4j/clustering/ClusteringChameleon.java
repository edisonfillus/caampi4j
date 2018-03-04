/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.hypergraph.HMetis;
import org.caampi4j.clustering.hypergraph.HyperEdge;
import org.caampi4j.clustering.hypergraph.HyperGraph;
import org.caampi4j.clustering.hypergraph.HyperNode;
import org.caampi4j.clustering.hypergraph.Partition;
import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class ClusteringChameleon implements Clustering {

    class K implements Comparable<K> {

        int metodo;
        double distancia;

        public K(int metodo, double distancia) {
            this.metodo = metodo;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(K o) {
            return Double.compare(distancia, o.distancia);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final K other = (K) obj;
            if (this.metodo != other.metodo) {
                return false;
            }
            if (Double.doubleToLongBits(this.distancia) != Double.doubleToLongBits(other.distancia)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 47 * hash + this.metodo;
            hash = 47 * hash + (int) (Double.doubleToLongBits(this.distancia) ^ (Double.doubleToLongBits(this.distancia) >>> 32));
            return hash;
        }
    }
    int[][] grafoKvizinhos = new int[10500][org.caampi4j.main.Main.CHAMELEON_K_VIZINHOS];

    public List<Group> execute(ASTModel astModel, MedidaDistancia medida) throws Exception {

        System.out.println("1. Formando Grafo K Vizinhos");
        //Gera o grafo dos k vizinhos mais próximos
        for (Metodo m : astModel.metodos.values()) {
            List<K> kVizinhos = new ArrayList<K>();
            for (Metodo other : astModel.metodos.values()) {
                if (other.getIdMetodo() != m.getIdMetodo()) {
                    double dist = medida.calcularDistancia(m, other);
                    kVizinhos.add(new K(other.getIdMetodo(), dist));
                }
            }
            Collections.sort(kVizinhos);
            for (int i = 0; i < org.caampi4j.main.Main.CHAMELEON_K_VIZINHOS; i++) {
                K k = kVizinhos.get(i);
                grafoKvizinhos[m.getIdMetodo()][i] = k.metodo;
            }

        }

        //Primeira Fase Concluída
        //Criando Hipergrafo para fase 2
        int edgeid = 0;
        HyperGraph graph = new HyperGraph();
        for (Metodo m : astModel.metodos.values()) {
            HyperNode node = graph.getNode(m.getIdMetodo());
            for (int i = 0; i < org.caampi4j.main.Main.CHAMELEON_K_VIZINHOS; i++) {
                HyperEdge edge = graph.getEdge(++edgeid);
                HyperNode otherNode = graph.getNode(grafoKvizinhos[m.getIdMetodo()][i]);
                double dist = medida.calcularDistancia(m, astModel.metodos.get(grafoKvizinhos[m.getIdMetodo()][i]));
                dist = 1.01d - dist;
                edge.setWeight((int)(dist*100));
                edge.addNode(node);
                edge.addNode(otherNode);
                node.addEdge(edge);

            }
        }


        System.out.println("2. Particionando Grafo");
        //Faz a partição do hipergrafo até MINSIZE
        boolean particionado = false;
        do {
            //Escolhe o maior subcluster
            int partMax = -1;
            int sizeMax = -1;
            for (Partition part : graph.getPartitions().values()) {
                if (part.getSize() > sizeMax) {
                    sizeMax = part.getSize();
                    partMax = part.getId();
                }
            }
            if (sizeMax > org.caampi4j.main.Main.CHAMELEON_MIN_SIZE) { //MINSIZE
                HMetis.shmetisPartition(graph.getPartition(partMax), 25); //Particiona a maior partição
                particionado = true;
            } else {
                particionado = false;
            }
        } while (particionado == true);

        System.out.println("3. Agrupando");
        //Montar grupos
        //Inicializa Grupos
        List<Group> grupos = new ArrayList<Group>();
        int idGroup = 0;
        for (Partition part : graph.getPartitions().values()) {
            Group c = new Group();
            c.idGroup = ++idGroup;
            for (HyperNode node : part.getNodes().values()) {
                c.metodos.add(astModel.metodos.get(node.getData()));
            }
            grupos.add(c);
        }
        graph = null;
        
        boolean changed = true;
        int iteracoes = 0;
        while (changed == true){
            System.out.println("Iteração: " + ++iteracoes);

            double maxDist = 0.0d;
            Group g1 = null;
            Group g2 = null;
            for (Group grupo1 : grupos) {
                for (Group grupo2 : grupos) {
                    if (grupo1.idGroup == grupo2.idGroup) {
                        continue;
                    }
                    double dist = distanciaICIP(grupo1, grupo2, medida, astModel);
                    if (dist > maxDist) {
                        maxDist = dist;
                        g1 = grupo1;
                        g2 = grupo2;
                    }
                }
            }
            if(maxDist > org.caampi4j.main.Main.CHAMELEON_MIN_SIMILARITY){
                Group g1g2 = new Group();
                g1g2.idGroup = ++idGroup;
                cacheBisec.remove(g1.idGroup); //Remove do cache
                cacheBisec.remove(g2.idGroup); //Remove do cache
                grupos.remove(g1); //Remove dos grupos
                grupos.remove(g2); //Remove dos grupos
                g1g2.metodos.addAll(g1.metodos);
                g1g2.metodos.addAll(g2.metodos);
                grupos.add(g1g2); //Adiciona o grupo merged
                changed = true;
            } else {
                changed = false;
            }
        }
        return grupos;

    }
    private Map<Integer, List<HyperEdge>> cacheBisec = new HashMap<Integer, List<HyperEdge>>();

    
     private Double[][] cacheDistancia = new Double[5000][5000];
    
     private double distanciaICIP(Group c1, Group c2, MedidaDistancia medida, ASTModel astModel) throws Exception {

        Double cache = cacheDistancia[c1.idGroup][c2.idGroup];
        if(cache !=null){
            return cache;
        }
        cache = cacheDistancia[c2.idGroup][c1.idGroup];
        if(cache !=null){
            return cache;
        }
                 
        Set<Integer> metodosC1 = new HashSet<Integer>();
        for (Metodo m : c1.metodos) {
            metodosC1.add(m.getIdMetodo());
        }
        Set<Integer> metodosC2 = new HashSet<Integer>();
        for (Metodo m : c2.metodos) {
            metodosC2.add(m.getIdMetodo());
        }
        double PAc1c2 = 0.0d;
        int qtdPAc1c2 = 0;
        double PAc1 = 0.0d;
        double PAc2 = 0.0d;
        double CAc1c2 = 0.0d;
        double CAc1 = 0.0d;
        double CAc2 = 0.0d;
        for (Integer m : metodosC1) {
            for (int i = 0; i < org.caampi4j.main.Main.CHAMELEON_K_VIZINHOS; i++) {
                int k = grafoKvizinhos[m][i];
                if (metodosC2.contains(k)) {
                    double dist = medida.calcularDistancia(astModel.metodos.get(m), astModel.metodos.get(k));
                    if(dist > 1.00d){
                        dist = 1.00d; //Limiar
                    }
                    qtdPAc1c2++;
                    PAc1c2 += (1 - dist);
                    CAc1c2 += (1 - dist);
                }
            }
        }
        for (Integer m : metodosC2) {
            for (int i = 0; i < org.caampi4j.main.Main.CHAMELEON_K_VIZINHOS; i++) {
                int k = grafoKvizinhos[m][i];
                if (metodosC1.contains(k)) {
                    double dist = medida.calcularDistancia(astModel.metodos.get(m), astModel.metodos.get(k));
                    if(dist > 1.00d){
                        dist = 1.00d; //Limiar
                    }
                    qtdPAc1c2++;
                    PAc1c2 += (1 - dist);
                    CAc1c2 += (1 - dist);
                }
            }
        }
        if (PAc1c2 == 0.0d && CAc1c2 == 0.0d) {
            return 0.0d;
        }
        PAc1c2 = PAc1c2 / qtdPAc1c2; //Média
        
        //Identifica bisec de C1
        List<HyperEdge> bisectC1 = cacheBisec.get(c1.idGroup);
        if (bisectC1 == null) {
            int edgeid = 0;
            HyperGraph graphC1 = new HyperGraph();
            for (Integer m : metodosC1) {
                HyperNode node = graphC1.getNode(m);
                for (int i = 0; i < 3; i++) {
                    int vizinho = grafoKvizinhos[m][i];
                    //Verifica no grafo vizinhos mais próximos as ligações intra-grupo
                    if (metodosC1.contains(vizinho)) {
                        HyperEdge edge = graphC1.getEdge(++edgeid);
                        HyperNode otherNode = graphC1.getNode(vizinho);
                        double dist = medida.calcularDistancia(astModel.metodos.get(m), astModel.metodos.get(vizinho));
                        dist = 1.01d - dist;
                        edge.setWeight((int)(dist*100));
                        edge.addNode(node);
                        edge.addNode(otherNode);
                        node.addEdge(edge);
                    }
                }
            }
            if (!graphC1.getPartition(0).getEdges().isEmpty()) {
                bisectC1 = HMetis.shmetisFindBisect(graphC1.getPartition(0), 25);
            } else {
                bisectC1 = new ArrayList<HyperEdge>();
            }
            cacheBisec.put(c1.idGroup, bisectC1);
        }
        for (HyperEdge edge : bisectC1) {
            Iterator<HyperNode> iterator = edge.getNodes().iterator();
            Metodo m1 = astModel.metodos.get(iterator.next().getData());
            Metodo m2 = astModel.metodos.get(iterator.next().getData());
            double dist = medida.calcularDistancia(m1, m2);
            if(dist > 1.00d){
                        dist = 1.00d; //Limiar
                    }
            PAc1 += (1 - dist);
            CAc1 += (1 - dist);
        }
        if(!bisectC1.isEmpty()){
            PAc1 = PAc1 / bisectC1.size();
        }
        
        //Identifica bisec de C2
        List<HyperEdge> bisectC2 = cacheBisec.get(c2.idGroup);
        if (bisectC2 == null) {
            int edgeid = 0;
            HyperGraph graphC2 = new HyperGraph();
            for (Integer m : metodosC2) {
                HyperNode node = graphC2.getNode(m);
                for (int i = 0; i < org.caampi4j.main.Main.CHAMELEON_K_VIZINHOS; i++) {
                    int vizinho = grafoKvizinhos[m][i];
                    //Verifica no grafo vizinhos mais próximos as ligações intra-grupo
                    if (metodosC2.contains(vizinho)) {
                        HyperEdge edge = graphC2.getEdge(++edgeid);
                        HyperNode otherNode = graphC2.getNode(vizinho);
                        double dist = medida.calcularDistancia(astModel.metodos.get(m), astModel.metodos.get(vizinho));
                        dist = 1.01d - dist;
                        edge.setWeight((int)(dist*100));
                        edge.addNode(node);
                        edge.addNode(otherNode);
                        node.addEdge(edge);
                    }
                }
            }
            if (!graphC2.getPartition(0).getEdges().isEmpty()) {
                bisectC2 = HMetis.shmetisFindBisect(graphC2.getPartition(0), 25);
            } else {
                bisectC2 = new ArrayList<HyperEdge>();
            }
            cacheBisec.put(c2.idGroup, bisectC2);
        }
        for (HyperEdge edge : bisectC2) {
            Iterator<HyperNode> iterator = edge.getNodes().iterator();
            Metodo m1 = astModel.metodos.get(iterator.next().getData());
            Metodo m2 = astModel.metodos.get(iterator.next().getData());
            double dist = medida.calcularDistancia(m1, m2);
            if(dist > 1.00d){
                        dist = 1.00d; //Limiar
                    }
            PAc2 += (1 - dist);
            CAc2 += (1 - dist);
        }
        if(!bisectC2.isEmpty()){
            PAc2 = PAc2 / bisectC2.size();
        }

        double RC = 0.0d;
        if ((PAc1 + PAc2) != 0) {
            double c1factor = (double)c1.metodos.size() / (double)(c1.metodos.size()+c2.metodos.size()); 
            double c2factor = (double)c2.metodos.size() / (double)(c1.metodos.size()+c2.metodos.size()); 
            RC = PAc1c2 / ((PAc1 * c1factor) + (PAc2 * c2factor));
        } else {
            RC = PAc1c2;
        }
        
        double RI = 0.0d;
        if ((CAc1 + CAc2) != 0) {
            RI = CAc1c2 / ((CAc1 + CAc2) / 2);
        } else {
            RI = CAc1c2;
        }
        double result = RC * RI;
        cacheDistancia[c1.idGroup][c2.idGroup] = result;
        return result;
    }
    
}
