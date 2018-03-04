/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edison
 */
public class Classe implements Serializable{
    int idClass;
    String nomeCompletoClasse;
    String nomeClasse;
    boolean innerClass;
    List<Metodo> metodos = new ArrayList<Metodo>();
    List<Classe> innerClasses = new ArrayList<Classe>();
    Classe superClass;
    List<Classe> classesPai = new ArrayList<Classe>();
    List<Classe> classesFilhas = new ArrayList<Classe>();
    Classe enclosureClass;

    public List<Classe> getClassesFilhas() {
        return classesFilhas;
    }

    public void setClassesFilhas(List<Classe> classesFilhas) {
        this.classesFilhas = classesFilhas;
    }

    public List<Classe> getClassesPai() {
        return classesPai;
    }

    public void setClassesPai(List<Classe> classesPai) {
        this.classesPai = classesPai;
    }

    public Classe getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Classe superClass) {
        this.superClass = superClass;
    }

        
    public Classe getEnclosureClass() {
        return enclosureClass;
    }


    public void setEnclosureClass(Classe enclosureClass) {
        this.enclosureClass = enclosureClass;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public boolean isInnerClass() {
        return innerClass;
    }

    public void setInnerClass(boolean innerClass) {
        this.innerClass = innerClass;
    }

    public List<Classe> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(List<Classe> innerClasses) {
        this.innerClasses = innerClasses;
    }

    public List<Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(List<Metodo> metodos) {
        this.metodos = metodos;
    }
    
    public Metodo findMetodo(String name, List<String> params) {
        for(Metodo m : metodos){
            if (m.getNomeMetodo().equals(name)){
                if(m.getParametros().size() == params.size()){
                    if(params.isEmpty()){
                        return m;
                    }
                    for(int i = 0 ; i < params.size() ; i++){
                        if(params.get(i).equals(m.getParametros().get(i))){
                            return m;
                        }
                    }
                }
            }
        }
        return null;
    }
    


    public String getNomeCompletoClasse() {
        return nomeCompletoClasse;
    }

    public void setNomeCompletoClasse(String nomeClasse) {
        this.nomeCompletoClasse = nomeClasse;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }
    
     
       
   


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Classe other = (Classe) obj;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idClass;
        hash = 97 * hash + (this.nomeCompletoClasse != null ? this.nomeCompletoClasse.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return nomeCompletoClasse;
    }
    
    
    
}
