package org.caampi4j.clustering.hypergraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HMetis {


    public static void shmetisPartition(Partition partition, int factor) throws Exception {
        int parts = 2; //Numero de partições
        int newId = 1; //Reconfigura Ids
        Map<Integer, HyperNode> mapIdToNode = new LinkedHashMap<Integer, HyperNode>(); //Mapear o ID do HMetis com o data
        Collection<HyperNode> nodes = partition.getNodes().values();
        for(HyperNode node : nodes){
            node.setId(newId++);
            mapIdToNode.put(node.getId(), node);
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("C:\\Temp\\hmetisIn")));
        partition.print(pw);
        pw.close();
        Process proc = Runtime.getRuntime().exec(org.caampi4j.main.Main.shmetis + " " + "C:\\Temp\\hmetisIn" + " " + parts + " " + factor, null, new File("C:\\Temp"));
        final InputStream input = proc.getInputStream();
        final InputStream error = proc.getErrorStream();
        new StreamCleaner(input).start();
        new StreamCleaner(error).start();
        proc.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Temp\\hmetisIn.part." + parts)));
        String partString = br.readLine();

        int idNewPart = partition.getGraph().getNumOfPartitions();
        int id = 1;
        while (partString != null) {
            int part = Integer.parseInt(partString);
            if (part != 0) {
                partition.getGraph().setPartition(mapIdToNode.get(id), idNewPart);
            }
            id++;
            partString = br.readLine();
        }
        partition.getGraph().checkEdgesChange();
        
    }
    
    public static List<HyperEdge> shmetisFindBisect(Partition partition, int factor) throws Exception {
        int parts = 2; //Numero de partições
        int newId = 1; //Reconfigura Ids
        Map<Integer, HyperNode> mapIdToNode = new LinkedHashMap<Integer, HyperNode>(); //Mapear o ID do HMetis com o data
        Collection<HyperNode> nodes = partition.getNodes().values();
        for(HyperNode node : nodes){
            node.setId(newId++);
            mapIdToNode.put(node.getId(), node);
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("C:\\Temp\\hmetisIn")));
        partition.print(pw);
        pw.close();
        Process proc = Runtime.getRuntime().exec(org.caampi4j.main.Main.shmetis + " " + "C:\\Temp\\hmetisIn" + " " + parts + " " + factor, null, new File("C:\\Temp"));
        final InputStream input = proc.getInputStream();
        final InputStream error = proc.getErrorStream();
        new StreamCleaner(input).start();
        new StreamCleaner(error).start();
        proc.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Temp\\hmetisIn.part." + parts)));
        String partString = br.readLine();

        int idNewPart = partition.getGraph().getNumOfPartitions();
        int id = 1;
        while (partString != null) {
            int part = Integer.parseInt(partString);
            if (part != 0) {
                partition.getGraph().setPartition(mapIdToNode.get(id), idNewPart);
            }
            id++;
            partString = br.readLine();
        }
        return partition.getGraph().findEdgesBisect();
        
    }
    
    /*
    public static void shmetis(HyperGraph graph, int parts, int factor) throws Exception {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("C:\\Temp\\hmetisIn")));
        graph.print(pw);
        pw.close();
        Process proc = Runtime.getRuntime().exec(main.Main.shmetis + " " + "C:\\Temp\\hmetisIn" + " " + parts + " " + factor, null, new File("C:\\Temp"));
        final InputStream input = proc.getInputStream();
        final InputStream error = proc.getErrorStream();
        new StreamCleaner(input).start();
        new StreamCleaner(error).start();
        proc.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Temp\\hmetisIn.part." + parts)));
        String partString = br.readLine();
        int i = 0;
        while (partString != null) {
            int part = Integer.parseInt(partString);
            graph.setPartition(i, part);
            i++;
            partString = br.readLine();
        }
    }
     * */
    
}
