/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Edison
 */
public class ASTModel implements Serializable{

    public LinkedHashMap<String, Classe> classes = new LinkedHashMap<String, Classe>();
    public LinkedHashMap<Integer, Metodo> metodos = new LinkedHashMap<Integer, Metodo>();
    public LinkedHashMap<Integer, Invocacao> invocacoes = new LinkedHashMap<Integer, Invocacao>();
    public List<Classe> classesList = new ArrayList<Classe>();
    public List<Metodo> metodosList = new ArrayList<Metodo>();
    public List<Invocacao> invocacoesList = new ArrayList<Invocacao>();
       
    public void populaListas(){
        classesList.addAll(classes.values());
        metodosList.addAll(metodos.values());
        invocacoesList.addAll(invocacoes.values());
    }
    public void populaMapas(){
        for(Classe c : classesList){
            classes.put(c.getNomeCompletoClasse(), c);
        }
        for(Metodo m : metodosList){
            metodos.put(m.getIdMetodo(), m);
        }
        for(Invocacao i : invocacoesList){
            invocacoes.put(i.getIdInvocacao(), i);
        }
    }
    
}
