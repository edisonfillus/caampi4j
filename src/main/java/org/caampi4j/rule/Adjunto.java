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
public class Adjunto {

    public enum Tipo {
        ABSOLUTO,
        RELACIONADO;
    }
    public Tipo tipo;
    
    public int m1;
    public int m2;

    public Adjunto(int m1, int m2, Tipo tipo) {
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
        final Adjunto other = (Adjunto) obj;
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
            sb.append(m1);
            sb.append(" ADJUNTO ");
            sb.append(tipo.name());
            sb.append(" ");
            sb.append(m2);
        return sb.toString();
    }
}
