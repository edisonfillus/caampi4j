/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.ast.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.common.model.MethodInfo;
import org.caampi4j.ast.common.model.MethodInvocationInfo;
import org.caampi4j.ast.common.model.TypeInfo;
import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Classe;
import org.caampi4j.model.Invocacao;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class FilterAnalyserController {
      
     
       public ASTModel filterInvocations(List<ClassInfo> listJavaClass){
           ASTModel astModel = new ASTModel();
           //Captura Classes e métodos
           for(ClassInfo jci : listJavaClass){
               Classe classe = new Classe();
               classe.setNomeCompletoClasse(jci.getFlatName());
               classe.setNomeClasse(jci.getSimpleName());
               classe.setIdClass(jci.getIdClass());
               if(jci.getFlatName().contains("$")){//InnerClass
                   int encIndex = jci.getFlatName().lastIndexOf("$");
                   String encClass = jci.getFlatName().substring(0, encIndex);
                   Classe enclosure = astModel.classes.get(encClass);
                   enclosure.getInnerClasses().add(classe);
                   classe.setInnerClass(true);
                   classe.setEnclosureClass(enclosure);
               }
               for(MethodInfo mi : jci.getMethods()){
                   //if(mi.getName().startsWith("get") || mi.getName().startsWith("set")){
                   //    continue;
                   //}
                   Metodo metodo = new Metodo();
                   metodo.setIdMetodo(mi.getIdMethod());
                   metodo.setClasse(classe);
                   metodo.setNomeMetodo(mi.getName());
                   metodo.setRetorno(mi.getReturnType().getTypeClass());
                   for(TypeInfo param : mi.getParameters().values()){
                       metodo.getParametros().add(param.getTypeClass().toString());
                   }
                   classe.getMetodos().add(metodo);
                   astModel.metodos.put(metodo.getIdMetodo(), metodo);
               }
               astModel.classes.put(classe.getNomeCompletoClasse(),classe);
           }

           //Captura estrutura de herança interna
           for(ClassInfo jci : listJavaClass){
               Classe classe = astModel.classes.get(jci.getFlatName());
               Classe superClass = astModel.classes.get(jci.getNameOfSuperClass());
               if(superClass != null){ //Tem super classe
                   classe.getClassesPai().add(superClass);
                   superClass.getClassesFilhas().add(classe);
               }
               for(String itr : jci.getNameOfInterfaces()){
                   Classe interf = astModel.classes.get(itr);
                   if(interf!=null){
                       classe.getClassesPai().add(interf);
                       interf.getClassesFilhas().add(classe);
                   }
               }
           }
               
           //Captura override de métodos
           for(Classe classe : astModel.classes.values()){
               for(Metodo metodo : classe.getMetodos()){
                   for(Classe pai : classe.getClassesPai()){
                       identificaMetodosHerdados(pai, metodo);
                   }
               }
           }
           
           //Captura invocações
           for(ClassInfo jci : listJavaClass){
               for(MethodInfo mi : jci.getMethods()){
                   //if(mi.getName().startsWith("get") || mi.getName().startsWith("set")){
                   //        continue; //Filtro por método get e set
                   //}
                   for(MethodInvocationInfo mif : mi.getInvocations()){
                       if(!astModel.classes.containsKey(mif.getClazz().getTypeClass())){
                           continue; //Filtro por pacote.. somente internos
                       }
                       Metodo owner = astModel.metodos.get(mi.getIdMethod());
                       Invocacao invocacao = new Invocacao();
                       invocacao.setIdInvocacao(mif.getIdMethodInvocation());
                       Classe invokedClass = astModel.classes.get(mif.getClazz().getTypeClass());
                       List<String> params = new ArrayList<String>();
                       for(TypeInfo t : mif.getParameters()){
                           params.add(t.getTypeClass());
                       }
                       Metodo invokedMethod = invokedClass.findMetodo(mif.getName(), params);
                       if(invokedMethod == null){
                           continue;
                       }
                       invocacao.setOwner(owner);
                       invocacao.setMetodoInvocado(invokedMethod);
                       invokedMethod.getInvocadores().add(invocacao);
                       owner.getInvocacoes().add(invocacao);
                       astModel.invocacoes.put(invocacao.getIdInvocacao(), invocacao);
                   }
               }
           }
           //Remove getters and setters
           for(Classe classe : astModel.classes.values()){
               for(Metodo metodo : classe.getMetodos()){
                  if(metodo.getNomeMetodo().startsWith("get") || metodo.getNomeMetodo().startsWith("set")){
                    astModel.metodos.remove(metodo.getIdMetodo());
                  }
                  if(metodo.getNomeMetodo().equals(metodo.getClasse().getNomeClasse())){
                    astModel.metodos.remove(metodo.getIdMetodo()); //Remove se for chamada a construtor this e super  
                  }
               }
           }
           return astModel;
       }
    
       public void filterByFanIn(int fanInMin, ASTModel astModel){
           List<Integer> methodsToRemove = new ArrayList<Integer>();
           for(Entry<Integer,Metodo> entry : astModel.metodos.entrySet()){
               if(entry.getValue().getFanIn().size() < fanInMin){
                   methodsToRemove.add(entry.getKey());
               }
           }
           for(Integer idMethod : methodsToRemove){
               astModel.metodos.remove(idMethod);
           }
       }
       
       
       public static void identificaMetodosHerdados(Classe atual, Metodo original){
           Metodo m = atual.findMetodo(original.getNomeMetodo(), original.getParametros());
           if(m!=null){
               //Inclui o método identificado
               original.getMetodosSobreescrevidos().add(m);
               m.getMetodosRefinados().add(original);
           }
           for(Classe c : atual.getClassesPai()){
               identificaMetodosHerdados(c, original);
           }
       }
       
       public static void identificaMetodosRefinados(Classe atual, Metodo original){
           Metodo m = atual.findMetodo(original.getNomeMetodo(), original.getParametros());
           if(m!=null){
               //Inclui o método identificado
               original.getMetodosRefinados().add(m);
               m.getMetodosRefinados().add(original);
           }
           for(Classe c : atual.getClassesPai()){
               identificaMetodosHerdados(c, original);
           }
       }

       
       
}

