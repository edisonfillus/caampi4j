/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.caampi4j.rule.Regra;

/**
 *
 * @author Edison
 */
public class Metodo  implements Serializable{
    int idMetodo;
    Classe classe;
    String nomeMetodo;
    List<String> parametros = new ArrayList<String>();
    String retorno;
    List<Invocacao> invocacoes = new ArrayList<Invocacao>(); //Métodos que invocou
    List<Invocacao> invocadores = new ArrayList<Invocacao>(); //Métodos que invocaram este
    Set<Metodo> metodosSobreescrevidos = new HashSet<Metodo>(); //Metodos que este herdou
    Set<Metodo> metodosRefinados = new HashSet<Metodo>(); //Metodos que fizeram override deste
    Set<Metodo> fanIn = new HashSet<Metodo>();
    Map<Integer,Regra> regrasAntecedencia;
    Map<Integer,Regra> regrasPrecedencia;
    int idGroup;

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public Map<Integer, Regra> getRegrasAntecedencia() {
        if(regrasAntecedencia == null){
            regrasAntecedencia = new HashMap<Integer,Regra>();
        }
        return regrasAntecedencia;
    }

    public void addRegraAntecedencia(int m2, int methodBody) {
        Regra r = getRegrasAntecedencia().get(m2);
        if(r == null){
            r = new Regra(idMetodo,m2);
            r.addMethodBody(methodBody);
            r.tipo = Regra.Tipo.ANTECEDIDO;
            getRegrasAntecedencia().put(m2, r);
        } else {
            r.addMethodBody(methodBody);
            getRegrasAntecedencia().put(m2, r);        
        }
    }

    public Map<Integer, Regra> getRegrasPrecedencia() {
        if(regrasPrecedencia == null){
           regrasPrecedencia = new HashMap<Integer,Regra>();  
        } 
        return regrasPrecedencia;
    }

    public void addRegraPrecedencia(int m1, int methodBody) {
        Regra r = getRegrasPrecedencia().get(m1);
        if(r == null){
            r = new Regra(idMetodo,m1);
            r.addMethodBody(methodBody);
            r.tipo = Regra.Tipo.PRECEDIDO;
            getRegrasPrecedencia().put(m1, r);
        } else {
            r.addMethodBody(methodBody);
            getRegrasPrecedencia().put(m1, r);        
        }
    }

        
    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    
        public Set<Metodo> getMetodosRefinados() {
        return metodosRefinados;
    }

    public void setMetodosRefinados(Set<Metodo> metodosRefinados) {
        this.metodosRefinados = metodosRefinados;
    }

    public Set<Metodo> getMetodosSobreescrevidos() {
        return metodosSobreescrevidos;
    }

    public void setMetodosSobreescrevidos(Set<Metodo> metodosSobreescrevidos) {
        this.metodosSobreescrevidos = metodosSobreescrevidos;
    }
    
    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public List<Invocacao> getInvocacoes() {
        return invocacoes;
    }

    public void setInvocacoes(List<Invocacao> invocacoes) {
        this.invocacoes = invocacoes;
    }

    public List<Invocacao> getInvocadores() {
        return invocadores;
    }

    public void setInvocadores(List<Invocacao> invocadores) {
        this.invocadores = invocadores;
    }

    public String getNomeMetodo() {
        return nomeMetodo;
    }

    public void setNomeMetodo(String nomeMetodo) {
        this.nomeMetodo = nomeMetodo;
    }

    public List<String> getParametros() {
        return parametros;
    }

    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public Set<Metodo> getFanIn() {
        return fanIn;
    }

    public void setFanIn(Set<Metodo> fanIn) {
        this.fanIn = fanIn;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(idMetodo);
        sb.append("]");
        sb.append(retorno);
        sb.append(" ");
        sb.append(classe.getNomeCompletoClasse());
        sb.append(".");
        sb.append(nomeMetodo);
        sb.append("(");
        int i = 0;
        for (String param : parametros) {
            i++;
            sb.append(param);
            if (i < parametros.size()){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Metodo other = (Metodo) obj;
        if (this.idMetodo != other.idMetodo) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.idMetodo;
        return hash;
    }
    
    public void calculaSuporte(){
        int qtdTotalOcorre = 0;
        for(Regra r : getRegrasAntecedencia().values()){
            qtdTotalOcorre += r.getMethodBodies().size();
        }
        for(Regra r : getRegrasAntecedencia().values()){
            r.confidencia = (double)r.getMethodBodies().size()/(double)qtdTotalOcorre;
        }
        
        int qtdTotalOcorreu = 0;
        for(Regra r : getRegrasPrecedencia().values()){
            qtdTotalOcorreu += r.getMethodBodies().size();
        }
        for(Regra r : getRegrasPrecedencia().values()){
            r.confidencia = (double)r.getMethodBodies().size()/(double)qtdTotalOcorreu;
        }
    }
    
    
    
}
