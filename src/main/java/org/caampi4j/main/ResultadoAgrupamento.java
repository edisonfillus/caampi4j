/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.main;

import java.util.List;

import org.caampi4j.clustering.solution.Group;
import org.caampi4j.main.Main.Agrupamento;
import org.caampi4j.main.Main.Medida;
import org.caampi4j.main.Main.Software;
import org.caampi4j.model.ASTModel;

/**
 *
 * @author Edison
 */
public class ResultadoAgrupamento {
        
    public int FANIN_MIN;
    public double DIST_MIN;
    public boolean ANALISAR_ESTRUTURAS_DADOS;
    public Medida MEDIDA_DISTANCIA;
    public Agrupamento METODO_CLUSTERING;
    public boolean CONSIDERAR_POLIMORFISMO;
    public Software SOFTWARE;
    public long tempo;
    List<Group> grupos;
    ASTModel astModel;
    
   
}
