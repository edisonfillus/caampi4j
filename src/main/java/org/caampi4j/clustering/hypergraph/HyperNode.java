package org.caampi4j.clustering.hypergraph;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class HyperNode {

    private int id;
    private Integer data;
    private HyperGraph graph;
    private Set<HyperEdge> edges = new LinkedHashSet<HyperEdge>();
    private int assignedPartition;

    public int relationWeight(HyperNode other) {
        int weight = 0;
        Set<HyperEdge> edgesClone = new HashSet<HyperEdge>();
        edgesClone.addAll(edges);
        edgesClone.retainAll(other.edges);
        return edgesClone.size();
    }

    public HyperGraph getGraph() {
        return graph;
    }

    public void setGraph(HyperGraph graph) {
        this.graph = graph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPart() {
        return assignedPartition;
    }

    public void setPart(int part) {
        assignedPartition = part;
    }

    public Integer getData() {
        return data;
    }

    public HyperNode(Integer data, HyperGraph graph) {
        this.graph = graph;
        this.data = data;
    }

    public void addEdge(HyperEdge edge) {
        edges.add(edge);
        graph.getPartition(assignedPartition).add(edge);
    }

    public void print(PrintWriter pw) {
        pw.print(id);
    }

    public Set<HyperEdge> getEdges() {
        return edges;
    }
}
