package org.caampi4j.clustering.hypergraph;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HyperGraph {

    private Map<Integer, HyperNode> nodes = new LinkedHashMap<Integer, HyperNode>();
    private Map<Integer, HyperEdge> edges = new LinkedHashMap<Integer, HyperEdge>();
    private Map<Integer, Partition> partitions = new LinkedHashMap<Integer, Partition>();

    public void removeNode(Integer data) {
        HyperNode node = nodes.remove(data);
        for (Partition part : partitions.values()) {
            part.removeNode(node);
        }
        for (HyperEdge edge : edges.values()) {
            edge.removeNode(node);
        }
    }

    public void removeEdge(Integer data) {
        HyperEdge edge = edges.remove(data);
        for (HyperNode node : edge.getNodes()) {
            node.getEdges().remove(edge);
        }
        for (Partition part : partitions.values()) {
            part.removeEdge(edge);
        }
    }

      public int findMostRelatedPartition(Integer nodeData) {
        HyperNode node = this.getNode(nodeData);
        int max = Integer.MIN_VALUE;
        int partition = -1;
        for (Map.Entry<Integer, Partition> part : partitions.entrySet()) {
            int weight = relationWeight(node, part.getValue());
            if (weight > max) {
                max = weight;
                partition = part.getKey();
            }
        }
        return partition;
    }

    private int relationWeight(HyperNode node, Partition p) {
        int weight = 0;
        for (HyperNode other : p.getNodes().values()) {
            weight += node.relationWeight(other);
        }
        return weight;
    }

    public int getLeastLoadedPartition() {
        int min = Integer.MAX_VALUE;
        int partitionNumber = -1;
        for (Map.Entry<Integer, Partition> part : partitions.entrySet()) {
            Partition p = part.getValue();
            int size = p.getSize();
            if (size < min) {
                min = size;
                partitionNumber = part.getKey();
            }
        }
        return partitionNumber;
    }

    public int getNumOfEdges() {
        return edges.size();
    }

    public int getNumOfNodes() {
        return nodes.size();
    }

    public int getNumOfPartitions() {
        return partitions.size();
    }

    public void setPartition(HyperNode node, int part) {
        Partition oldPartition = getPartition(node.getPart());
        oldPartition.removeNode(node);
        Partition partition = getPartition(part);
        node.setPart(part);
        partition.add(node);
    }

    public Partition getPartition(int part) {
        Partition partition = partitions.get(part);
        if (partition == null) {
            partition = new Partition(part, this);
            partitions.put(part, partition);
        }
        return partition;
    }

    public HyperEdge getEdge(Integer data) {
        HyperEdge edge = edges.get(data);
        if (edge == null) {
            edge = new HyperEdge(data, this);
            edges.put(data, edge);
        }
        return edge;
    }

    public HyperNode getNode(Integer data) {
        HyperNode node = nodes.get(data);
        if (node == null) {
            node = new HyperNode(data, this);
            node.setId(nodes.size() + 1);
            node.setPart(0);
            nodes.put(data, node);
            getPartition(0).add(node);
        }
        return node;
    }

    public void print(PrintWriter pw) {
        int numOfVertices = nodes.size();
        int numOfEdges = edges.size();
        pw.println(numOfEdges + " " + numOfVertices);
        for (HyperEdge edge : edges.values()) {
            edge.print(pw);
            pw.println();
        }
    }
    
    
    public List<HyperEdge> findEdgesBisect(){
        List<HyperEdge> edgesBisect = new ArrayList<HyperEdge>();
        forEdges:
        for(HyperEdge edge : edges.values()){
            Set<HyperNode> nodesToCheck = edge.getNodes();
            int part = nodesToCheck.iterator().next().getPart();
            for(HyperNode n : nodesToCheck){
                if(n.getPart() != part){
                    edgesBisect.add(edge);
                    continue forEdges;
                }
            }
        }
        return edgesBisect;
    }
    
    public void checkEdgesChange(){
        List<HyperEdge> edgesToRemove = new ArrayList<HyperEdge>();
        forEdges:
        for(HyperEdge edge : edges.values()){
            Set<HyperNode> nodesToCheck = edge.getNodes();
            int part = nodesToCheck.iterator().next().getPart();
            for(HyperNode n : nodesToCheck){
                if(n.getPart() != part){
                    edgesToRemove.add(edge);
                    continue forEdges;
                }
            }
        }
        for(HyperEdge toRemove : edgesToRemove){
            removeEdge(toRemove.getData());
        }
    }

    public Map<Integer, Partition> getPartitions() {
        return partitions;
    }
    
    
    
}
