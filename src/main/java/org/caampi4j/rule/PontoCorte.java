/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.rule;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Edison
 */
public class PontoCorte {

    public enum Tipo {

        ANTES_DA_CHAMADA,
        APOS_A_CHAMADA,
        ANTES_DA_EXECUCAO,
        APOS_A_EXECUCAO,
        DURANTE_A_EXECUCAO;
    }
    public Tipo tipo;
    public int m1;
    public int m2;
    public Set<Integer> methodBodies = new HashSet<Integer>();

    public Set<Integer> getMethodBodies() {
        return methodBodies;
    }

    public void addMethodBody(int metodo) {
        this.methodBodies.add(metodo);
    }

    public PontoCorte(int m1, int m2, Tipo tipo, Set<Integer> methodBodies) {
        this.m1 = m1;
        this.m2 = m2;
        this.tipo = tipo;
        this.methodBodies = methodBodies;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PontoCorte other = (PontoCorte) obj;
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
        return m1 * 13 + m2 * 17;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (tipo == Tipo.ANTES_DA_CHAMADA || tipo == Tipo.APOS_A_CHAMADA || tipo == Tipo.DURANTE_A_EXECUCAO) {
            sb.append(m1);
            sb.append(" ");
            sb.append(tipo.name().replace('_', ' '));
            sb.append(" ");
            sb.append(m2);
        } else {
            sb.append(m1);
            sb.append(" ");
            sb.append(tipo.name().replace('_', ' ')).append(" [");
            int i = 0;
            for (int m : methodBodies) {
                i++;
                sb.append(m);
                if (i < methodBodies.size()) {
                    sb.append(",");
                }
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
