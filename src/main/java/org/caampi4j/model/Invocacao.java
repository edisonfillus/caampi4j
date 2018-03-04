/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.model;

import java.io.Serializable;

/**
 *
 * @author Edison
 */
public class Invocacao  implements Serializable{
    int idInvocacao;
    Metodo owner;
    Metodo metodoInvocado;

    public int getIdInvocacao() {
        return idInvocacao;
    }

    public void setIdInvocacao(int idInvocacao) {
        this.idInvocacao = idInvocacao;
    }

    public Metodo getMetodoInvocado() {
        return metodoInvocado;
    }

    public void setMetodoInvocado(Metodo metodoInvocado) {
        this.metodoInvocado = metodoInvocado;
    }

    public Metodo getOwner() {
        return owner;
    }

    public void setOwner(Metodo owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invocacao other = (Invocacao) obj;
        if (this.idInvocacao != other.idInvocacao) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idInvocacao;
        return hash;
    }

    @Override
    public String toString() {
        return metodoInvocado.toString();
    }
    
    
    
}
