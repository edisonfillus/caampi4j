package org.caampi4j.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.caampi4j.ast.processor.ASTAnalyzerController;
import org.caampi4j.ast.processor.CalculoFanIn;
import org.caampi4j.ast.processor.FilterAnalyserController;
import org.caampi4j.clustering.ClusteringChameleon;
import org.caampi4j.clustering.ClusteringHierarquico;
import org.caampi4j.clustering.ClusteringKmedoids;
import org.caampi4j.clustering.distance.MedidaDistancia;
import org.caampi4j.clustering.distance.MedidaDistanciaGMG;
import org.caampi4j.clustering.distance.MedidaDistanciaHBZH;
import org.caampi4j.clustering.distance.MedidaDistanciaPACO;
import org.caampi4j.clustering.distance.MedidaDistanciaSOND;
import org.caampi4j.clustering.quality.CCC;
import org.caampi4j.clustering.quality.DISP;
import org.caampi4j.clustering.quality.DIV2;
import org.caampi4j.clustering.quality.Espalhamento;
import org.caampi4j.clustering.quality.GFIRank;
import org.caampi4j.clustering.quality.GFRank;
import org.caampi4j.clustering.quality.GSIRank;
import org.caampi4j.clustering.quality.IdealGroups;
import org.caampi4j.clustering.quality.Interconectividade;
import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.ASTModel;
import org.caampi4j.model.Metodo;
import org.caampi4j.rule.AnaliseRegras;
import org.caampi4j.rule.ExtracaoRegras;
import org.caampi4j.rule.PontoCorte;
import org.caampi4j.rule.Regra;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class to verify java files using custom annotation processor. The
 * files to be verified can be supplied to this class as comma-separated
 * argument.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class Main {

 
    
    public static String SOURCE_CODE_DIR; 
    public static final String shmetis = "D:\\Mestrado\\Implementacao\\AST\\src\\clustering\\hypergraph\\shmetis\\shmetis.exe";
    
    public static int FANIN_MIN = 2;
    public static double DIST_MIN = 0.70d;
    public static boolean ANALISAR_ESTRUTURAS_DADOS = true;
    public static Medida MEDIDA_DISTANCIA = Medida.SOND;
    public static Agrupamento METODO_CLUSTERING = Agrupamento.hierarquico;
    public static Software SOFTWARE = Software.HSQLDB;
    public static double CONFIDENCIA_PONTO_CORTE = 0.75d;
    
    public static int CHAMELEON_K_VIZINHOS = 3;
    public static int CHAMELEON_MIN_SIZE = 5;
    public static double CHAMELEON_MIN_SIMILARITY = 1.20d;
    

    public static boolean SHOW_AST_TREE = true;
    public static boolean SHOW_FAN_IN = false;
    public static boolean SHOW_CLUSTERING_PROCESS = false;
    public static boolean IDENTIFICAR_PONTO_CORTE = false;
    public static boolean ORDENAR_GRUPOS = false;
    public static boolean CALC_DISP_DIV = true;
    
    
    //public static double SOND_SCATTER = 1.00d; public static double SOND_OPERATIONS = 0.00; public static double SOND_NAMES = 0.00d;
    //public static double SOND_SCATTER = 0.00d; public static double SOND_OPERATIONS = 1.00; public static double SOND_NAMES = 0.00d;
    //public static double SOND_SCATTER = 0.00d; public static double SOND_OPERATIONS = 0.00; public static double SOND_NAMES = 1.00d;
    
    public static double SOND_SCATTER = 0.40d; public static double SOND_OPERATIONS = 0.40; public static double SOND_NAMES = 0.20d;
    //public static double SOND_SCATTER = 0.50d; public static double SOND_OPERATIONS = 0.30; public static double SOND_NAMES = 0.20d;
    //public static double SOND_SCATTER = 0.45d; public static double SOND_OPERATIONS = 0.45; public static double SOND_NAMES = 0.10d;
    //public static double SOND_SCATTER = 0.35d; public static double SOND_OPERATIONS = 0.35; public static double SOND_NAMES = 0.30d;
    //public static double SOND_SCATTER = 0.40d; public static double SOND_OPERATIONS = 0.35; public static double SOND_NAMES = 0.25d;
    
   
    //public static double SOND_METHOD_NAME = 1.00d; public static double SOND_CLASS_NAME = 0.00d; public static double SOND_PARAMETER_RETURN = 0.00d;
    //public static double SOND_METHOD_NAME = 0.50d; public static double SOND_CLASS_NAME = 0.40d; public static double SOND_PARAMETER_RETURN = 0.10d;
    public static double SOND_METHOD_NAME = 0.50d; public static double SOND_CLASS_NAME = 0.30d; public static double SOND_PARAMETER_RETURN = 0.20d;
    //public static double SOND_METHOD_NAME = 0.60d; public static double SOND_CLASS_NAME = 0.20d; public static double SOND_PARAMETER_RETURN = 0.20d;
    //public static double SOND_METHOD_NAME = 0.40d; public static double SOND_CLASS_NAME = 0.40d; public static double SOND_PARAMETER_RETURN = 0.20d;
    
    


    public enum Software {
        JHOTDRAW,
        TOMCAT,
        HSQLDB
    }

    public enum Agrupamento {

        k_medoids,
        hierarquico,
        CHAMELEON
    }

    public enum Medida {

        PACO,
        GMG,
        HBZH,
        SOND
    }
    static MedidaDistancia medida = null;

    /**
     * The main method accepts the file(s) for verification. Multiple files can
     * be verified by passing the absolute path of the files as comma-separated
     * argument. This method invokes the custom annotation processor to process
     * the annnotations in the supplied file(s). Verification results will be
     * printed on to the console.
     * 
     * @param args
     *            The java source files to be verified.
     */
    public static void main(String[] args) {

        if(SOFTWARE == Software.JHOTDRAW){
            SOURCE_CODE_DIR = "D:\\Mestrado\\Implementacao\\AST\\ProgramasAnalise\\JHotDraw54b1\\src\\";
        } else if (SOFTWARE == Software.TOMCAT){
            SOURCE_CODE_DIR = "D:\\Mestrado\\Implementacao\\AST\\ProgramasAnalise\\Tomcat 5.5.17\\src\\";
        } else if (SOFTWARE == Software.HSQLDB){
            SOURCE_CODE_DIR = "D:\\Mestrado\\Implementacao\\AST\\ProgramasAnalise\\hsqldb\\src\\";
        }

        if (MEDIDA_DISTANCIA == Medida.PACO) {
            medida = new MedidaDistanciaPACO();
        } else if (MEDIDA_DISTANCIA == Medida.GMG) {
            medida = new MedidaDistanciaGMG();
        } else if (MEDIDA_DISTANCIA == Medida.HBZH) {
            medida = new MedidaDistanciaHBZH();
        } else if (MEDIDA_DISTANCIA == Medida.SOND) {
            medida = new MedidaDistanciaSOND();
        }

        ResultadoAgrupamento resultado = new ResultadoAgrupamento();
        resultado.ANALISAR_ESTRUTURAS_DADOS = ANALISAR_ESTRUTURAS_DADOS;
        resultado.DIST_MIN = DIST_MIN;
        resultado.FANIN_MIN = FANIN_MIN;
        resultado.MEDIDA_DISTANCIA = MEDIDA_DISTANCIA;
        resultado.METODO_CLUSTERING = METODO_CLUSTERING;
        resultado.SOFTWARE = SOFTWARE;

        /*
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().activationDepth(30);
        configuration.common().updateDepth(30);
        ObjectContainer db = Db4oEmbedded.openFile(configuration,"db4o.bd");
        
        
        ObjectSet cacheBD = db.queryByExample(resultado);
        if(cacheBD.hasNext()){
        resultado = (ResultadoAgrupamento) cacheBD.next();
        resultado.astModel.populaMapas();
        //    db.delete(resultado);
        } else {
        resultado = clustering(resultado);
        resultado.astModel.populaListas();
        db.store(resultado);
        }
        
         */
        resultado = clustering(resultado);


        List<Metodo> metodosCCC = new ArrayList<Metodo>();
        List<Group> gruposCCC = new ArrayList<Group>();
        if(SOFTWARE == Software.JHOTDRAW){
            gruposCCC = IdealGroups.jhotdrawIdealGroups(resultado.astModel);
            for (Group C : gruposCCC) {
                metodosCCC.addAll(C.metodos);
            }
        } else if(SOFTWARE == Software.TOMCAT){
            gruposCCC = IdealGroups.tomcatIdealGroups(resultado.astModel);
            for (Group C : gruposCCC) {
                metodosCCC.addAll(C.metodos);
            }
        } else if (SOFTWARE == Software.HSQLDB){
            gruposCCC = IdealGroups.hsqldbIdealGroups(resultado.astModel);
            for (Group C : gruposCCC) {
                metodosCCC.addAll(C.metodos);
            }
        }

        if (CALC_DISP_DIV) {

            double disp = DISP.calcular(resultado.grupos, gruposCCC);
            //double div = DIV.calcular(resultado.grupos, IdealGroups.jhotdrawIdealGroups(resultado.astModel));
            double div2 = DIV2.calcular(resultado.grupos, gruposCCC);

            DecimalFormat fmt = new DecimalFormat("0.00000000");
            
            //System.out.println("##### TEX = " + fmt.format(disp).replace(",", ".") + " & " + fmt.format(div2).replace(",", ".") + " & " + fmt.format((1-disp)+(1-div2)).replace(",", ".") + "\\\\");
            
            System.out.println("##### DISP = " + disp);
            //System.out.println("##### DIV = " + div);
            System.out.println("##### DIV = " + div2);
            System.out.println("##### DIF = " + ((1-disp)+(1-div2)));
            System.out.println("##### QTD GRUPOS = " + resultado.grupos.size());
            System.out.println("##### TEMPO = " + resultado.tempo / 1000 + "s");

        }

        if (ORDENAR_GRUPOS) {
            System.out.println("##### 5. ORDENACAO #####");
            for (int i = 0; i < resultado.grupos.size(); i++) {
                Group grupo = resultado.grupos.get(i);
                System.out.println("K" + grupo.idGroup + ";" + CCC.indice(grupo, metodosCCC) + ";" + GFIRank.calcular(grupo, medida) + ";" + grupo.toString());
            }
        }

        if (IDENTIFICAR_PONTO_CORTE) {

            System.out.println("##### 6. IDENTIFICACAO PONTOS DE CORTE ");
            ExtracaoRegras.execute(resultado.astModel);
            AnaliseRegras.execute(resultado.astModel, resultado.grupos);

            
            System.out.println("+ Regras Classificadas");
            for (Group grupo : resultado.grupos) {
                for (Metodo m : grupo.metodos) {
                    for (Regra r : m.getRegrasAntecedencia().values()) {
                        if (r.confidencia >= Main.CONFIDENCIA_PONTO_CORTE && r.getMethodBodies().size() > 1 && r.m1 != r.m2) {
                            System.out.println("  " + r.m1 + ";" + r.getMethodBodies().size() + ";" + r.confidencia + ";" + r.toString());
                        }
                    }
                    for (Regra r : m.getRegrasPrecedencia().values()) {
                        if (r.confidencia >= Main.CONFIDENCIA_PONTO_CORTE && r.getMethodBodies().size() > 1  && r.m1 != r.m2) {
                            System.out.println("  " + r.m1 + ";" + r.getMethodBodies().size() + ";" + r.confidencia + ";" + r.toString());
                        }
                    }
                }
            }
                
            System.out.println("+ Pontos de Corte Identificados");
            for (Group grupo : resultado.grupos) {
                if(grupo.pontosCorte.size() > 0) {
                    for (PontoCorte ptc : grupo.pontosCorte) {
                        System.out.println("  K" + grupo.idGroup + ";" + CCC.indice(grupo, metodosCCC) + ";" + GSIRank.calcular(grupo, medida) + ";" + ptc.toString());
                    }
                }
                
            }

        }


    }

    private static ResultadoAgrupamento clustering(ResultadoAgrupamento resultado) {
        //Extração da Arvore de Sintaxe Abstrata + Relação de Invocações
        System.out.println("##### 1. COMPUTAÇÃO #####");
        ASTAnalyzerController controller = new ASTAnalyzerController();
        List<File> files = getFilesFromDirectory(SOURCE_CODE_DIR);
        controller.invokeProcessor(files);

        System.out.println("##### 2. FILTRO DE MÉTODOS #####");
        //Filtro
        FilterAnalyserController fac = new FilterAnalyserController();
        ASTModel astModel = null;

        astModel = fac.filterInvocations(ASTAnalyzerController.listJavaClass);
        //SerializerUtil.serialize(astModel, "C:\\Users\\Edison\\Documents\\NetBeansProjects\\ast_model.ser");
        //astModel = (ASTModel) SerializerUtil.deserialize("C:\\Users\\Edison\\Documents\\NetBeansProjects\\ast_model.ser");



        //Fan In
        System.out.println("##### 3. FAN IN #####");
        CalculoFanIn.calculoFanIn(astModel.invocacoes.values());

        //Filtro Fan In
        fac.filterByFanIn(FANIN_MIN, astModel);

        if (SHOW_FAN_IN) {
            for (Metodo m : astModel.metodos.values()) {
                System.out.println(m.getFanIn().size() + ";" + m.toString());
            }
        }


        System.out.println("##### 4. AGRUPAMENTO #####");

        long startTime = System.currentTimeMillis();


        List<Group> grupos = null;
        if (METODO_CLUSTERING == Agrupamento.k_medoids) {
            ClusteringKmedoids paco = new ClusteringKmedoids();
            grupos = paco.execute(astModel, medida);
        } else if (METODO_CLUSTERING == Agrupamento.hierarquico) {
            ClusteringHierarquico haco = new ClusteringHierarquico();
            grupos = haco.execute(astModel, medida);
        } else if (METODO_CLUSTERING == Agrupamento.CHAMELEON) {
            ClusteringChameleon amuca = new ClusteringChameleon();
            try {
                grupos = amuca.execute(astModel, medida);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        long ellapsed = System.currentTimeMillis() - startTime;
        resultado.tempo = ellapsed;

        //Numera grupos e vincula métodos
        int countIdGroup = 0;
        for (Group grupo : grupos) {
            countIdGroup++;
            grupo.idGroup = countIdGroup;
            for (Metodo m : grupo.metodos) {
                m.setIdGroup(countIdGroup);
            }
        }
        resultado.astModel = astModel;
        resultado.grupos = grupos;


        return resultado;
    }

    private static List<File> getFilesFromDirectory(String dir) {
        List<File> list = new ArrayList<File>();
        File d = new File(dir);
        for (File f : d.listFiles()) {
            if (f.isFile() && f.getName().toLowerCase().endsWith(".java")) {
                list.add(f);
                continue;
            }
            if (f.isDirectory()) {
                list.addAll(getFilesFromDirectory(f.getPath()));
            }
        }

        return list;
    }
}
