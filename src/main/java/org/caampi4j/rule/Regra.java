/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Edison
 */
public class Regra {
    
    public enum Tipo{
        PRECEDIDO,
        ANTECEDIDO;
    }
    
    public Tipo tipo;
    public static int INICIO = Integer.MIN_VALUE;
    public static int FIM = Integer.MAX_VALUE;
    
    public int m1;
    public int m2;
    private Set<Integer> methodBodies = new HashSet<Integer>();
    public double confidencia;

    public Set<Integer> getMethodBodies() {
        return methodBodies;
    }

    public void addMethodBody(int metodo) {
        this.methodBodies.add(metodo);
    }
    
    public Regra(int m1, int m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Regra other = (Regra) obj;
        if (this.m1 != other.m1) {
            return false;
        }
        if (this.m2 != other.m2) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return m1*13 + m2*17;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(m1).append(" ");
        if(m2 == INICIO){
            sb.append("INICIO EXECUCAO [");
            int i = 0;
            for(int m : methodBodies){
                i++;
                sb.append(m);
                if(i<methodBodies.size()){
                    sb.append(",");
                }
            }
            sb.append("]");
        } else if(m2 == FIM){
            sb.append("FIM EXECUCAO [");
            int i = 0;
            for(int m : methodBodies){
                i++;
                sb.append(m);
                if(i<methodBodies.size()){
                    sb.append(",");
                }
            }
            sb.append("]");
        } else {
            sb.append(tipo.name()).append(" ").append(m2);
        }
        //sb.append(" (Sup: ").append(qtd).append(", Conf: ").append(confidencia).append(")");
        return sb.toString();
    }
    
    
    
}
