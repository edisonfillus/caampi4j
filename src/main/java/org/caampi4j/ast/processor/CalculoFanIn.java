/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.ast.processor;

import java.util.Collection;
import java.util.List;

import org.caampi4j.model.Invocacao;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class CalculoFanIn {
    
    public static void calculoFanIn(Collection<Invocacao> invocacoes){
        for(Invocacao inv : invocacoes){
            Metodo invocado = inv.getMetodoInvocado();
            invocado.getFanIn().add(inv.getOwner());
            for(Metodo over : invocado.getMetodosSobreescrevidos()){
                over.getFanIn().add(inv.getOwner());
            }
            for(Metodo ref : invocado.getMetodosRefinados()){
                ref.getFanIn().add(inv.getOwner());
            }
        }
    }
    
}
