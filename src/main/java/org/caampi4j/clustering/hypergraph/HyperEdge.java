package org.caampi4j.clustering.hypergraph;

import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

public class HyperEdge {

    private Integer data;
    private HyperGraph graph;
    private Set<HyperNode> nodes;
    private int weight;

    public Set<HyperNode> getNodes() {
        return nodes;
    }

    public void removeNode(HyperNode node) {
        nodes.remove(node);
    }

    public Integer getData() {
        return data;
    }

    HyperEdge(Integer data, HyperGraph graph) {
        this.graph = graph;
        this.data = data;
    }

    public void addNode(HyperNode node) {
        if (nodes == null) {
            nodes = new LinkedHashSet<HyperNode>();
        }
        nodes.add(node);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    

    public void print(PrintWriter pw) {
        if (nodes == null) {
            return;
        }
        pw.print(weight);
        pw.print(' ');
        for (HyperNode node : nodes) {
            node.print(pw);
            pw.print(' ');
        }
    }
}
