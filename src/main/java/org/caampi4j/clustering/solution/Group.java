/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.solution;

import java.util.ArrayList;
import java.util.List;

import org.caampi4j.model.Metodo;
import org.caampi4j.rule.Adjunto;
import org.caampi4j.rule.InterGrupo;
import org.caampi4j.rule.PontoCorte;
import org.caampi4j.rule.Precedencia;

/**
 *
 * @author Edison
 */
public class Group {
    
    public int idGroup;
    public String nomeGrupo;
    public List<Metodo> metodos = new ArrayList<Metodo>();
    public List<Precedencia> precedencias = new ArrayList<Precedencia>();
    public List<Adjunto> adjuntos = new ArrayList<Adjunto>();
    public List<InterGrupo> interGrupos = new ArrayList<InterGrupo>();
    public List<PontoCorte> pontosCorte = new ArrayList<PontoCorte>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0 ; i < metodos.size() ; i++){
            sb.append(metodos.get(i).toString());
            if(i < metodos.size()-1){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    
}
