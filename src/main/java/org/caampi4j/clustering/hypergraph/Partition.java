package org.caampi4j.clustering.hypergraph;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Partition {

    private int id;
    private HyperGraph graph;
    private Map<Integer, HyperNode> nodes = new LinkedHashMap<Integer, HyperNode>();
    private Map<Integer, HyperEdge> edges = new LinkedHashMap<Integer, HyperEdge>();

    public void removeEdge(HyperEdge edge) {
        edges.remove(edge.getData());
    }
    
    public void removeNode(HyperNode node) {
        for (HyperEdge edge : node.getEdges()) {
            edges.remove(edge.getData());
        }
        nodes.remove(node.getData());
    }

    public int getSize() {
        return nodes.size();
    }

    public int getId() {
        return id;
    }

    public Partition(int id, HyperGraph hp) {
        this.graph = hp;
        this.id = id;
    }

    public void add(HyperNode node) {
        nodes.put(node.getData(), node);
        for (HyperEdge edge : node.getEdges()) {
            edges.put(edge.getData(), edge);
        }
    }
    
    public void add(HyperEdge edge){
        edges.put(edge.getData(), edge);
    }

    public Map<Integer, HyperEdge> getEdges() {
        return edges;
    }

    public Map<Integer, HyperNode> getNodes() {
        return nodes;
    }

    public HyperGraph getGraph() {
        return graph;
    }
    
    public void print(PrintWriter pw) {
        int numOfVertices = nodes.size();
        int numOfEdges = edges.size();
            
        pw.println(numOfEdges + " " + numOfVertices + " 1");
        for (HyperEdge edge : edges.values()) {
            edge.print(pw);
            pw.println();
        }
    }
    
   
}
