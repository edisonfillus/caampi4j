/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.distance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.caampi4j.model.Invocacao;
import org.caampi4j.model.Metodo;

/**
 *
 * @author Edison
 */
public class MedidaDistanciaSOND implements MedidaDistancia {

    private static Double[][] cacheArr = new Double[11000][11000];
    private static Map<Integer, Set<Integer>> colCache = new HashMap<Integer, Set<Integer>>();

    @Override
    public double calcularDistancia(Metodo m1, Metodo m2) {

        if (m1.getIdMetodo() == m2.getIdMetodo()) {
            return 0.0d;
        }
        Double resultCache = cacheArr[m1.getIdMetodo()][m2.getIdMetodo()];
        if (resultCache != null) {
            return resultCache;
        }
        resultCache = cacheArr[m2.getIdMetodo()][m1.getIdMetodo()]; //Verifica o inverso
        if (resultCache != null) {
            return resultCache;
        }

        double resultScatter = calculaDistanciaScattering(m1, m2);

        double resultName = calculaDistanciaNome(m1, m2);

        double resultOperations = calculaDistanciaOperacoes(m1, m2);

        //double result = resultScatter * 0.40 + resultTangling * 0.40d + resultName * 0.20d;

        double result = resultScatter * org.caampi4j.main.Main.SOND_SCATTER + resultOperations * org.caampi4j.main.Main.SOND_OPERATIONS + resultName * org.caampi4j.main.Main.SOND_NAMES;
        
        cacheArr[m1.getIdMetodo()][m2.getIdMetodo()] = result;
        return result;
    }

    private static Set<Integer> getCollectionHACOFromM(Metodo m) {
        Set<Integer> cache = colCache.get(m.getIdMetodo());
        if (cache != null) {
            return cache;
        }
        Set<Integer> colM = new HashSet<Integer>();
        colM.add(m.getIdMetodo()); //Método em Si
        colM.add(-m.getClasse().getIdClass()); //Classe em que o método esta contido
        if (m.getClasse().isInnerClass()) {//Se inner classe, classe que está contido
            colM.add(-m.getClasse().getEnclosureClass().getIdClass());
        }
        boolean polimorfismo = true;
        if (polimorfismo) {
            for (Metodo inv : m.getFanIn()) {
                colM.add(inv.getIdMetodo());
                colM.add(-inv.getClasse().getIdClass());
                if (inv.getClasse().isInnerClass()) {
                    colM.add(-inv.getClasse().getEnclosureClass().getIdClass());
                }
            }
        } else {
            for (Invocacao inv : m.getInvocadores()) {
                colM.add(inv.getOwner().getIdMetodo()); //Métodos que invocaram m1
                colM.add(-inv.getOwner().getClasse().getIdClass()); //Classe que invocaram m1
                if (inv.getOwner().getClasse().isInnerClass()) {
                    colM.add(-inv.getOwner().getClasse().getEnclosureClass().getIdClass());
                }
            }
        }
        colCache.put(m.getIdMetodo(), colM);
        return colM;
    }

    private static Set<Integer> getCollectionTanglingFromM(Metodo m) {
        Set<Integer> colM = new HashSet<Integer>();
        boolean CONSIDERAR_POLIMORFISMO = false;
        if (CONSIDERAR_POLIMORFISMO) {
            for (Invocacao inv : m.getInvocacoes()) {
                colM.add(inv.getMetodoInvocado().getIdMetodo());
                colM.add(-inv.getMetodoInvocado().getClasse().getIdClass());
                if (inv.getMetodoInvocado().getClasse().isInnerClass()) {
                    colM.add(-inv.getMetodoInvocado().getClasse().getEnclosureClass().getIdClass());
                }
            }
            for (Metodo mover : m.getMetodosSobreescrevidos()) {
                for (Invocacao inv : mover.getInvocacoes()) {
                    colM.add(inv.getMetodoInvocado().getIdMetodo());
                    colM.add(-inv.getMetodoInvocado().getClasse().getIdClass());
                    if (inv.getMetodoInvocado().getClasse().isInnerClass()) {
                        colM.add(-inv.getMetodoInvocado().getClasse().getEnclosureClass().getIdClass());
                    }
                }
            }
            for (Metodo mref : m.getMetodosRefinados()) {
                for (Invocacao inv : mref.getInvocacoes()) {
                    colM.add(inv.getMetodoInvocado().getIdMetodo());
                    colM.add(-inv.getMetodoInvocado().getClasse().getIdClass());
                    if (inv.getMetodoInvocado().getClasse().isInnerClass()) {
                        colM.add(-inv.getMetodoInvocado().getClasse().getEnclosureClass().getIdClass());
                    }
                }
            }

        } else {
            for (Invocacao inv : m.getInvocacoes()) {
                colM.add(inv.getMetodoInvocado().getIdMetodo()); //Métodos que m invocou
                colM.add(-inv.getMetodoInvocado().getClasse().getIdClass());
                if (inv.getMetodoInvocado().getClasse().isInnerClass()) {
                    colM.add(-inv.getMetodoInvocado().getClasse().getEnclosureClass().getIdClass());
                }
            }
        }
        return colM;
    }

    private double calculaDistanciaScattering(Metodo m1, Metodo m2) {
        Set<Integer> colM1Scatter = getCollectionHACOFromM(m1);
        Set<Integer> colM2Scatter = getCollectionHACOFromM(m2);
        if (colM1Scatter.isEmpty() && colM2Scatter.isEmpty()) {
            return 0.0d;
        }
        Set<Integer> intersectionScatter = new HashSet<Integer>(colM1Scatter);
        intersectionScatter.retainAll(colM2Scatter);
        double resultScatter = 0.0d;
        if (intersectionScatter.isEmpty()) {
            resultScatter = 1.00;
            return resultScatter;
        } else {
            Set<Integer> unionScatter = new HashSet<Integer>(colM1Scatter);
            unionScatter.addAll(colM2Scatter);
            resultScatter = 1.0d - ((double) intersectionScatter.size() / (double) unionScatter.size());
        }
        return resultScatter;
    }

    private double calculaDistanciaOperacoes(Metodo m1, Metodo m2) {
        Set<Integer> colM1Tangling = getCollectionTanglingFromM(m1);
        Set<Integer> colM2Tangling = getCollectionTanglingFromM(m2);
        Set<Integer> intersectionTangling = new HashSet<Integer>(colM1Tangling);
        intersectionTangling.retainAll(colM2Tangling);
        double resultTangling = 0.0d;
        if (colM1Tangling.isEmpty() && colM2Tangling.isEmpty()) {
            resultTangling = 1.00d;
        } else {
            if (intersectionTangling.isEmpty()) {
                resultTangling = 1.00d;
            } else {
                Set<Integer> unionTangling = new HashSet<Integer>(colM1Tangling);
                unionTangling.addAll(colM2Tangling);
                resultTangling = 1.00d - ((double) intersectionTangling.size() / (double) unionTangling.size());
            }
        }
        return resultTangling;

    }

    private double calculaDistanciaNome(Metodo m1, Metodo m2) {
        int maxClassNameDistance = Math.max(m1.getClasse().getNomeClasse().length(), m2.getClasse().getNomeClasse().length());
        int classNameDistance = getLevenshteinDistance(m1.getClasse().getNomeClasse().toLowerCase(), m2.getClasse().getNomeClasse().toLowerCase());
        int maxMethodNameDistance = Math.max(m1.getNomeMetodo().length(), m2.getNomeMetodo().length());
        int methodNameDistance = getLevenshteinDistance(m1.getNomeMetodo().toLowerCase(), m2.getNomeMetodo().toLowerCase());

        double distClassName = 0.0d;
        if (classNameDistance > 0) {
            distClassName = (double) classNameDistance / (double) maxClassNameDistance;
        }

        double distMethodName = 0.0d;
        if (methodNameDistance > 0) {
            distMethodName = (double) methodNameDistance / (double) maxMethodNameDistance;
        }

        double distParametros = 0.0d;
        Set<String> colM1Context = new HashSet<String>();
        colM1Context.addAll(m1.getParametros());
        Set<String> colM2Context = new HashSet<String>();
        colM2Context.addAll(m2.getParametros());
        Set<String> unionContext = new HashSet<String>(colM1Context);
        unionContext.addAll(colM2Context);
        if (unionContext.isEmpty()) {
            distParametros = 0.00;
        } else {
            Set<String> intersectionContext = new HashSet<String>(colM1Context);
            intersectionContext.retainAll(colM2Context);
            distParametros = 1.0d - ((double) intersectionContext.size() / (double) unionContext.size());
        }
        double distRetorno = (m1.getRetorno().equals(m2.getRetorno())) ? 0.00d : 1.00d;

        double distContexto = (distParametros + distRetorno) / 2;



        //double distName = (distClassName + distMethodName) / 2;
        double distName = (distMethodName * org.caampi4j.main.Main.SOND_METHOD_NAME + distClassName * org.caampi4j.main.Main.SOND_CLASS_NAME  + distContexto * org.caampi4j.main.Main.SOND_PARAMETER_RETURN);

        return distName;
    }

    private static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        /*
        The difference between this impl. and the previous is that, rather 
        than creating and retaining a matrix of size s.length()+1 by t.length()+1, 
        we maintain two single-dimensional arrays of length s.length()+1.  The first, d,
        is the 'current working' distance array that maintains the newest distance cost
        counts as we iterate through the characters of String s.  Each time we increment
        the index of String t we are comparing, d is copied to p, the second int[].  Doing so
        allows us to retain the previous cost counts as required by the algorithm (taking 
        the minimum of the cost count to the left, up one, and diagonally up and to the left
        of the current cost count being calculated).  (Note that the arrays aren't really 
        copied anymore, just switched...this is clearly much better than cloning an array 
        or doing a System.arraycopy() each time  through the outer loop.)
        
        Effectively, the difference between the two implementations is this one does not 
        cause an out of memory condition when calculating the LD over two very large strings.  		
         */

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        int p[] = new int[n + 1]; //'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; //placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost				
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now 
        // actually has the most recent cost counts
        return p[n];
    }
}
