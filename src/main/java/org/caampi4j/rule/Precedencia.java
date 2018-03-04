/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.rule;

import java.util.Set;

/**
 *
 * @author Edison
 */
public class Precedencia {

    public enum Tipo {
        ANTECEDE,
        PRECEDE;
    }
    public Tipo tipo;
    public static int INICIO = Integer.MIN_VALUE;
    public static int FIM = Integer.MAX_VALUE;
    public int m1;
    public int m2;

    public Precedencia(int m1, int m2, Tipo tipo) {
        this.m1 = m1;
        this.m2 = m2;
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Precedencia other = (Precedencia) obj;
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
        if (tipo == Tipo.PRECEDE) {
            sb.append(m1);
            sb.append(" PRECEDE ");
            if (m2 == INICIO) {
                sb.append("INICIO");
            } else {
                sb.append(" ").append(m2);
            }
        }
        if (tipo == Tipo.ANTECEDE) {
            sb.append(m1);
            sb.append(" ANTECEDE ");
            if (m2 == FIM) {
                sb.append("FIM");
            } else {
                sb.append(" ").append(m2);
            }
            
        }
        return sb.toString();
    }
}
