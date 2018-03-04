/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.rule;

import java.util.HashMap;
import java.util.Map;

import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Invocacao;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class ExtracaoRegras {
    
    public static final int INICIO = Integer.MIN_VALUE;
    public static final int FIM = Integer.MAX_VALUE;
    
    public static void execute(ASTModel astModel){

        for(Metodo t : astModel.metodos.values()){
            if(t.getInvocacoes().isEmpty()){
                continue;
            } else if (t.getInvocacoes().size() == 1){
                Invocacao inv = t.getInvocacoes().get(0);
                if(astModel.metodos.containsKey(inv.getMetodoInvocado().getIdMetodo())){ //Evita captura de regra com método filtrado
                    inv.getMetodoInvocado().addRegraAntecedencia(Regra.INICIO,t.getIdMetodo());
                    inv.getMetodoInvocado().addRegraPrecedencia(Regra.FIM,t.getIdMetodo());
                }
                /*
                if(main.Main.CONSIDERAR_POLIMORFISMO == true){
                    for(Metodo m : inv.getMetodoInvocado().getMetodosRefinados()){
                        m.addRegraOcorre(Regra.FIM);
                        m.addRegraOcorreu(Regra.INICIO);
                    }
                    for(Metodo m : inv.getMetodoInvocado().getMetodosSobreescrevidos()){
                        m.addRegraOcorre(Regra.FIM);
                        m.addRegraOcorreu(Regra.INICIO);
                    }              
                }
                 */
            } else {
                for (int i = 0 ; i < t.getInvocacoes().size()-1 ; i++){
                    Invocacao m1 = t.getInvocacoes().get(i);
                    Invocacao m2 = t.getInvocacoes().get(i+1);
                    if(astModel.metodos.containsKey(m2.getMetodoInvocado().getIdMetodo())){ //Evita captura de regra com método filtrado
                        m1.getMetodoInvocado().addRegraPrecedencia(m2.getMetodoInvocado().getIdMetodo(),t.getIdMetodo());
                    }
                    if(astModel.metodos.containsKey(m1.getMetodoInvocado().getIdMetodo())){ //Evita captura de regra com método filtrado
                        m2.getMetodoInvocado().addRegraAntecedencia(m1.getMetodoInvocado().getIdMetodo(),t.getIdMetodo());
                    }
                    /*
                    if(main.Main.CONSIDERAR_POLIMORFISMO == true){
                        for(Metodo m : m1.getMetodoInvocado().getMetodosRefinados()){
                            m.addRegraOcorre(m2.getMetodoInvocado().getIdMetodo());
                        }
                        for(Metodo m : m1.getMetodoInvocado().getMetodosSobreescrevidos()){
                            m.addRegraOcorre(m2.getMetodoInvocado().getIdMetodo());                    
                        }
                        for(Metodo m : m2.getMetodoInvocado().getMetodosRefinados()){
                            m.addRegraOcorreu(m1.getMetodoInvocado().getIdMetodo());
                        }
                        for(Metodo m : m2.getMetodoInvocado().getMetodosSobreescrevidos()){
                            m.addRegraOcorreu(m1.getMetodoInvocado().getIdMetodo());                    
                        }
                    }
                     */
                }
                Invocacao ultima = t.getInvocacoes().get(t.getInvocacoes().size()-1);
                if(astModel.metodos.containsKey(ultima.getMetodoInvocado().getIdMetodo())){ //Evita captura de regra com método filtrado
                    ultima.getMetodoInvocado().addRegraPrecedencia(Regra.FIM,t.getIdMetodo());
                }
                
                Invocacao primeira = t.getInvocacoes().get(0);
                if(astModel.metodos.containsKey(primeira.getMetodoInvocado().getIdMetodo())){ //Evita captura de regra com método filtrado
                    primeira.getMetodoInvocado().addRegraAntecedencia(Regra.INICIO,t.getIdMetodo());
                }
                /*
                if(main.Main.CONSIDERAR_POLIMORFISMO == true){
                    for(Metodo m : ultima.getMetodoInvocado().getMetodosRefinados()){
                        m.addRegraOcorre(Regra.FIM);
                    }
                    for(Metodo m : ultima.getMetodoInvocado().getMetodosSobreescrevidos()){
                        m.addRegraOcorre(Regra.FIM);
                    }
                    for(Metodo m : primeira.getMetodoInvocado().getMetodosRefinados()){
                        m.addRegraOcorreu(Regra.INICIO);
                    }
                    for(Metodo m : primeira.getMetodoInvocado().getMetodosSobreescrevidos()){
                        m.addRegraOcorreu(Regra.INICIO);
                    }
                }            
                 */
            }
        }
        //Calculo Suporte
        for(Metodo t : astModel.metodos.values()){
            t.calculaSuporte();
        }
            
    }
}
