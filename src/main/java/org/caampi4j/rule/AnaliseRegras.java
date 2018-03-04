/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.rule;

import java.util.ArrayList;
import java.util.List;

import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class AnaliseRegras {
    
    public static void execute(ASTModel astModel, List<Group> grupos){
        for(Group grupo : grupos){
            List<Regra> regrasAntecedencia = new ArrayList<Regra>();
            List<Regra> regrasPrecedencia = new ArrayList<Regra>();

            for(Metodo m : grupo.metodos){
                regrasAntecedencia.addAll(m.getRegrasAntecedencia().values());
                regrasPrecedencia.addAll(m.getRegrasPrecedencia().values());
            }

            for(Regra regraAntecede : regrasAntecedencia){
                int cx = regraAntecede.m1;
                int cy = regraAntecede.m2;
                if(cx == cy){
                    continue;
                }
                if(regraAntecede.confidencia >=  org.caampi4j.main.Main.CONFIDENCIA_PONTO_CORTE && regraAntecede.getMethodBodies().size() > 1){
                    if(cy == Regra.INICIO){
                        grupo.pontosCorte.add(new PontoCorte(cx,cy,PontoCorte.Tipo.ANTES_DA_EXECUCAO,regraAntecede.getMethodBodies()));
                    } else {
                        for(Regra regraPrecedencia : regrasPrecedencia){ //Captura Around
                            if(regraPrecedencia.m1 == regraAntecede.m1 && regraPrecedencia.m2 == regraAntecede.m2 && regraPrecedencia.getMethodBodies().size() > 1 & regraPrecedencia.confidencia > org.caampi4j.main.Main.CONFIDENCIA_PONTO_CORTE){
                                //Encontrou regra around
                                Metodo m = astModel.metodos.get(regraAntecede.m2);
                                if(m != null){
                                    grupos.get(m.getIdGroup()-1).pontosCorte.add(new PontoCorte(cy,cx,PontoCorte.Tipo.DURANTE_A_EXECUCAO,regraAntecede.getMethodBodies()));
                                }
                            }
                        }
                        Metodo m = astModel.metodos.get(cy);
                        grupos.get(m.getIdGroup()-1).pontosCorte.add(new PontoCorte(cy,cx,PontoCorte.Tipo.ANTES_DA_CHAMADA,regraAntecede.getMethodBodies()));
                        //grupo.pontosCorte.add(new PontoCorte(cy,cx,PontoCorte.Tipo.ANTES_DA_CHAMADA));
                    }
                }
                
             
            }

            for(Regra regraPrecede : regrasPrecedencia){
                int cx = regraPrecede.m1;
                int cy = regraPrecede.m2;
                if(cx == cy){
                    continue;
                }
                if(regraPrecede.confidencia >= org.caampi4j.main.Main.CONFIDENCIA_PONTO_CORTE  && regraPrecede.getMethodBodies().size() > 1){
                    if(cy == Regra.FIM){
                        grupo.pontosCorte.add(new PontoCorte(cx,cy,PontoCorte.Tipo.APOS_A_EXECUCAO,regraPrecede.getMethodBodies()));
                    } else {
                        Metodo m = astModel.metodos.get(cy);
                        grupos.get(m.getIdGroup()-1).pontosCorte.add(new PontoCorte(cy,cx,PontoCorte.Tipo.APOS_A_CHAMADA,regraPrecede.getMethodBodies()));
                        //grupo.pontosCorte.add(new PontoCorte(cy,cx,PontoCorte.Tipo.APOS_A_CHAMADA));
                    }
                }
             
            }
            
        }
    }
}
