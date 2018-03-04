/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.caampi4j.clustering.quality;

import java.util.ArrayList;
import java.util.List;

import org.caampi4j.clustering.solution.Group;
import org.caampi4j.model.ASTModel;

/**
 *
 * @author Edison
 */
public class IdealGroups {
    
    
    public static List<Group> hsqldbIdealGroups(ASTModel astModel){
        List<Group> hsqldbCCC = new ArrayList<Group>();
        
        Group precondition1 = new Group(); //org.hsqldb.jdbc.jdbcConnection.aj > PreconditionAspect
        precondition1.metodos.add(astModel.metodos.get(1112));//void org.hsqldb.jdbc.jdbcConnection.checkClosed()
        precondition1.metodos.add(astModel.metodos.get(1557));//void org.hsqldb.jdbc.jdbcResultSet.checkClosed()
        precondition1.metodos.add(astModel.metodos.get(1686));//void org.hsqldb.jdbc.jdbcStatement.checkClosed()
        hsqldbCCC.add(precondition1);
        
        Group exception1 = new Group(); //org.hsqldb.jdbc.jdbcResultSet.aj > ExceptionHandlingAspect
        exception1.metodos.add(astModel.metodos.get(3939));//org.hsqldb.HsqlException org.hsqldb.Trace.error(int,java.lang.Object)
        exception1.metodos.add(astModel.metodos.get(3938));//org.hsqldb.HsqlException org.hsqldb.Trace.error(int,int,java.lang.Object)
        hsqldbCCC.add(exception1);
        
        
        Group exception2 = new Group(); //org.hsqldb.jdbc.jdbcResultSet.aj,org.hsqldb.jdbc.jdbcStatement.aj, org.hsqldb.jdbc.jdbcPreparedStatement.aj > ExceptionHandlingAspect
        exception2.metodos.add(astModel.metodos.get(1696));//java.sql.SQLException org.hsqldb.jdbc.Util.sqlException(org.hsqldb.HsqlException)
        exception2.metodos.add(astModel.metodos.get(1697));//java.sql.SQLException org.hsqldb.jdbc.Util.sqlException(int)
        exception2.metodos.add(astModel.metodos.get(1698));//java.sql.SQLException org.hsqldb.jdbc.Util.sqlException(int,java.lang.String)
        hsqldbCCC.add(exception2);
  
        Group exception3 = new Group(); //org.hsqldb.util.DatabaseManagerSwing.aj > SwingThreadSafetyAspect
        exception3.metodos.add(astModel.metodos.get(4069));//void org.hsqldb.util.CommonSwing.errorMessage(java.lang.Exception)
        hsqldbCCC.add(exception3);
    
        
        Group authorization1 = new Group(); //org.hsqldb.DatabaseCommandInterpreter.aj > AuthorizationAspect
        authorization1.metodos.add(astModel.metodos.get(690));//boolean org.hsqldb.Grantee.isAdmin()
        hsqldbCCC.add(authorization1);
        
        Group authorization2 = new Group(); //org.hsqldb.Parser.aj > AuthorizationAspect
        authorization2.metodos.add(astModel.metodos.get(686));//boolean org.hsqldb.Grantee.isAccessible(java.lang.Object.int)
        hsqldbCCC.add(authorization2);
        
        
        
        Group access1 = new Group(); //org.hsqldb.DatabaseCommandInterpreter.aj e org.hsqldb.Parser.aj > AccessControlAspect
        access1.metodos.add(astModel.metodos.get(3528));//boolean org.hsqldb.Session.isReadOnly()
        access1.metodos.add(astModel.metodos.get(3574));//boolean org.hsqldb.SessionInterface.isReadOnly()
        hsqldbCCC.add(access1);
        
        Group access2 = new Group(); //org.hsqldb.DatabaseCommandInterpreter.aj e org.hsqldb.CompiledStatementExecutor.aj > AccessControlAspect
        access2.metodos.add(astModel.metodos.get(186));//boolean org.hsqldb.Database.isFilesReadOnly()
        hsqldbCCC.add(access2);
        

        
        
        Group concurrent1 = new Group(); //org.hsqldb.util.DatabaseManagerSwing.aj > SwingThreadSafetyAspect
        concurrent1.metodos.add(astModel.metodos.get(4198));//void org.hsqldb.util.DatabaseManagerSwing.backgroundIt(java.lang.Runnable.java.lang.String)
        hsqldbCCC.add(concurrent1);
        
        Group tracing1 = new Group(); //org.hsqldb.aspects.TracingRedirectOutputAspect.aj
        tracing1.metodos.add(astModel.metodos.get(3952));//void org.hsqldb.Trace.printSystemOut(java.lang.String)
        hsqldbCCC.add(tracing1);
        
        Group access3 = new Group(); //org.hsqldb.Table.aj > AccessControlAspect
        access3.metodos.add(astModel.metodos.get(3793));//void org.hsqldb.Table.enforceNullConstraints(java.lang.Object)
        access3.metodos.add(astModel.metodos.get(3796));//void org.hsqldb.Table.enforceFieldValueLimits(java.lang.Object.int)
        hsqldbCCC.add(access3);

        Group access4 = new Group(); // org.hsqldb.TableWorks.aj > AccessControlAspect
        access4.metodos.add(astModel.metodos.get(3771));//boolean org.hsqldb.Table.isEmpty(org.hsqldb.Session)
        hsqldbCCC.add(access4);
        
        Group access5 = new Group(); //org.hsqldb.Parser.aj > AccessControlAspect
        access5.metodos.add(astModel.metodos.get(3695));//boolean org.hsqldb.Table.isView()
        hsqldbCCC.add(access5);
        
        Group access6 = new Group(); //org.hsqldb.Parser.aj > AccessControlAspect
        access6.metodos.add(astModel.metodos.get(3698));//boolean org.hsqldb.Table.isDataReadOnly()
        hsqldbCCC.add(access6);
        
        
        
        
        return hsqldbCCC;

        
        
    }
            
    public static List<Group> tomcatIdealGroups(ASTModel astModel){
        List<Group> tomcatCCC = new ArrayList<Group>();
        
        
        Group visitor1 = new Group(); //Seed Identificado FanInResults
        visitor1.metodos.add(astModel.metodos.get(8531));//void org.apache.jasper.compiler.Node$Visitor.visitBody(org.apache.jasper.compiler.Node)
        visitor1.metodos.add(astModel.metodos.get(8834));//void org.apache.jasper.compiler.SmapUtil$SmapGenVisitor.visitBody(org.apache.jasper.compiler.Node)
        visitor1.metodos.add(astModel.metodos.get(8941));//void org.apache.jasper.compiler.TextOptimizer$TextCatVisitor.visitBody(org.apache.jasper.compiler.Node)    
        tomcatCCC.add(visitor1);

        Group lifecycle1 = new Group(); //Seed Identificado FanInResults
        lifecycle1.metodos.add(astModel.metodos.get(3150));//void org.apache.catalina.core.StandardEngine.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3365));//void org.apache.catalina.core.StandardWrapper.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3704));//void org.apache.catalina.ha.context.ReplicatedContext.stop()
        lifecycle1.metodos.add(astModel.metodos.get(2632));//void org.apache.catalina.core.ContainerBase.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3065));//void org.apache.catalina.core.StandardContext.stop()
        lifecycle1.metodos.add(astModel.metodos.get(5757));//void org.apache.catalina.startup.Catalina.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3218));//void org.apache.catalina.core.StandardPipeline.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3285));//void org.apache.catalina.core.StandardService.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3256));//void org.apache.catalina.core.StandardServer.stop()
        lifecycle1.metodos.add(astModel.metodos.get(5865));//void org.apache.catalina.startup.Embedded.stop()
        lifecycle1.metodos.add(astModel.metodos.get(409));//void org.apache.catalina.authenticator.SSLAuthenticator.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4419));//void org.apache.catalina.loader.WebappClassLoader.stop()
        lifecycle1.metodos.add(astModel.metodos.get(5265));//void org.apache.catalina.session.JDBCStore.stop()
        lifecycle1.metodos.add(astModel.metodos.get(1122));//void org.apache.catalina.cluster.session.SimpleTcpReplicationManager.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3831));//void org.apache.catalina.ha.session.BackupManager.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4207));//void org.apache.catalina.ha.session.SimpleTcpReplicationManager.stop()
        lifecycle1.metodos.add(astModel.metodos.get(5414));//void org.apache.catalina.session.StandardManager.stop()
        lifecycle1.metodos.add(astModel.metodos.get(5386));//void org.apache.catalina.session.PersistentManagerBase.stop()
        lifecycle1.metodos.add(astModel.metodos.get(830));//void org.apache.catalina.cluster.session.DeltaManager.stop()
        lifecycle1.metodos.add(astModel.metodos.get(3919));//void org.apache.catalina.ha.session.DeltaManager.stop()
        lifecycle1.metodos.add(astModel.metodos.get(1752));//void org.apache.catalina.connector.Connector.stop()
        lifecycle1.metodos.add(astModel.metodos.get(5510));//void org.apache.catalina.session.StoreBase.stop()
        lifecycle1.metodos.add(astModel.metodos.get(7687));//void org.apache.catalina.valves.FastCommonAccessLogValve.stop()
        lifecycle1.metodos.add(astModel.metodos.get(325));//void org.apache.catalina.authenticator.AuthenticatorBase.stop()
        lifecycle1.metodos.add(astModel.metodos.get(381));//void org.apache.catalina.authenticator.SingleSignOn.stop()
        lifecycle1.metodos.add(astModel.metodos.get(7753));//void org.apache.catalina.valves.SemaphoreValve.stop()
        lifecycle1.metodos.add(astModel.metodos.get(995));//void org.apache.catalina.cluster.session.JvmRouteBinderValve.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4096));//void org.apache.catalina.ha.session.JvmRouteBinderValve.stop()
        lifecycle1.metodos.add(astModel.metodos.get(7651));//void org.apache.catalina.valves.ExtendedAccessLogValve.stop()
        lifecycle1.metodos.add(astModel.metodos.get(7611));//void org.apache.catalina.valves.AccessLogValve.stop()
        lifecycle1.metodos.add(astModel.metodos.get(7715));//void org.apache.catalina.valves.JDBCAccessLogValve.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4469));//void org.apache.catalina.loader.WebappLoader.stop()
        lifecycle1.metodos.add(astModel.metodos.get(1526));//void org.apache.catalina.cluster.tcp.SimpleTcpCluster.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4303));//void org.apache.catalina.ha.tcp.SimpleTcpCluster.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4362));//void org.apache.catalina.Lifecycle.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4967));//void org.apache.catalina.realm.RealmBase.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4917));//void org.apache.catalina.realm.JNDIRealm.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4857));//void org.apache.catalina.realm.JDBCRealm.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4933));//void org.apache.catalina.realm.MemoryRealm.stop()
        lifecycle1.metodos.add(astModel.metodos.get(5002));//void org.apache.catalina.realm.UserDatabaseRealm.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4786));//void org.apache.catalina.realm.DataSourceRealm.stop()
        lifecycle1.metodos.add(astModel.metodos.get(4825));//void org.apache.catalina.realm.JAASRealm.stop()
        tomcatCCC.add(lifecycle1);

        Group lifecycle2 = new Group(); //Seed Identificado FanInResults
        lifecycle2.metodos.add(astModel.metodos.get(3195));//void org.apache.catalina.core.StandardHost.start()
        lifecycle2.metodos.add(astModel.metodos.get(3149));//void org.apache.catalina.core.StandardEngine.start()
        lifecycle2.metodos.add(astModel.metodos.get(3364));//void org.apache.catalina.core.StandardWrapper.start()
        lifecycle2.metodos.add(astModel.metodos.get(3703));//void org.apache.catalina.ha.context.ReplicatedContext.start()
        lifecycle2.metodos.add(astModel.metodos.get(2631));//void org.apache.catalina.core.ContainerBase.start()
        lifecycle2.metodos.add(astModel.metodos.get(3062));//void org.apache.catalina.core.StandardContext.start()
        lifecycle2.metodos.add(astModel.metodos.get(1751));//void org.apache.catalina.connector.Connector.start()
        lifecycle2.metodos.add(astModel.metodos.get(324));//void org.apache.catalina.authenticator.AuthenticatorBase.start()
        lifecycle2.metodos.add(astModel.metodos.get(994));//void org.apache.catalina.cluster.session.JvmRouteBinderValve.start()
        lifecycle2.metodos.add(astModel.metodos.get(4095));//void org.apache.catalina.ha.session.JvmRouteBinderValve.start()
        lifecycle2.metodos.add(astModel.metodos.get(5509));//void org.apache.catalina.session.StoreBase.start()
        lifecycle2.metodos.add(astModel.metodos.get(7686));//void org.apache.catalina.valves.FastCommonAccessLogValve.start()
        lifecycle2.metodos.add(astModel.metodos.get(7610));//void org.apache.catalina.valves.AccessLogValve.start()
        lifecycle2.metodos.add(astModel.metodos.get(7714));//void org.apache.catalina.valves.JDBCAccessLogValve.start()
        lifecycle2.metodos.add(astModel.metodos.get(7650));//void org.apache.catalina.valves.ExtendedAccessLogValve.start()
        lifecycle2.metodos.add(astModel.metodos.get(380));//void org.apache.catalina.authenticator.SingleSignOn.start()
        lifecycle2.metodos.add(astModel.metodos.get(7752));//void org.apache.catalina.valves.SemaphoreValve.start()
        lifecycle2.metodos.add(astModel.metodos.get(5413));//void org.apache.catalina.session.StandardManager.start()
        lifecycle2.metodos.add(astModel.metodos.get(825));//void org.apache.catalina.cluster.session.DeltaManager.start()
        lifecycle2.metodos.add(astModel.metodos.get(3914));//void org.apache.catalina.ha.session.DeltaManager.start()
        lifecycle2.metodos.add(astModel.metodos.get(1121));//void org.apache.catalina.cluster.session.SimpleTcpReplicationManager.start()
        lifecycle2.metodos.add(astModel.metodos.get(3829));//void org.apache.catalina.ha.session.BackupManager.start()
        lifecycle2.metodos.add(astModel.metodos.get(4206));//void org.apache.catalina.ha.session.SimpleTcpReplicationManager.start()
        lifecycle2.metodos.add(astModel.metodos.get(1518));//void org.apache.catalina.cluster.tcp.SimpleTcpCluster.start()
        lifecycle2.metodos.add(astModel.metodos.get(4300));//void org.apache.catalina.ha.tcp.SimpleTcpCluster.start()
        lifecycle2.metodos.add(astModel.metodos.get(4418));//void org.apache.catalina.loader.WebappClassLoader.start()
        lifecycle2.metodos.add(astModel.metodos.get(5756));//void org.apache.catalina.startup.Catalina.start()
        lifecycle2.metodos.add(astModel.metodos.get(4468));//void org.apache.catalina.loader.WebappLoader.start()
        lifecycle2.metodos.add(astModel.metodos.get(5864));//void org.apache.catalina.startup.Embedded.start()
        lifecycle2.metodos.add(astModel.metodos.get(5385));//void org.apache.catalina.session.PersistentManagerBase.start()
        lifecycle2.metodos.add(astModel.metodos.get(3217));//void org.apache.catalina.core.StandardPipeline.start()
        lifecycle2.metodos.add(astModel.metodos.get(3255));//void org.apache.catalina.core.StandardServer.start()
        lifecycle2.metodos.add(astModel.metodos.get(3284));//void org.apache.catalina.core.StandardService.start()
        lifecycle2.metodos.add(astModel.metodos.get(408));//void org.apache.catalina.authenticator.SSLAuthenticator.start()
        lifecycle2.metodos.add(astModel.metodos.get(5264));//void org.apache.catalina.session.JDBCStore.start()
        lifecycle2.metodos.add(astModel.metodos.get(4361));//void org.apache.catalina.Lifecycle.start()
        lifecycle2.metodos.add(astModel.metodos.get(4966));//void org.apache.catalina.realm.RealmBase.start()
        lifecycle2.metodos.add(astModel.metodos.get(4932));//void org.apache.catalina.realm.MemoryRealm.start()
        lifecycle2.metodos.add(astModel.metodos.get(5001));//void org.apache.catalina.realm.UserDatabaseRealm.start()
        lifecycle2.metodos.add(astModel.metodos.get(4785));//void org.apache.catalina.realm.DataSourceRealm.start()
        lifecycle2.metodos.add(astModel.metodos.get(4824));//void org.apache.catalina.realm.JAASRealm.start()
        lifecycle2.metodos.add(astModel.metodos.get(4856));//void org.apache.catalina.realm.JDBCRealm.start()
        lifecycle2.metodos.add(astModel.metodos.get(4916));//void org.apache.catalina.realm.JNDIRealm.start()
        tomcatCCC.add(lifecycle2);
        
      
        Group observer1 = new Group(); //Seed Identificado FanInResults
        observer1.metodos.add(astModel.metodos.get(4358));//void org.apache.catalina.Lifecycle.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(4415));//void org.apache.catalina.loader.WebappClassLoader.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(2628));//void org.apache.catalina.core.ContainerBase.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(3252));//void org.apache.catalina.core.StandardServer.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(3214));//void org.apache.catalina.core.StandardPipeline.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(3281));//void org.apache.catalina.core.StandardService.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(377));//void org.apache.catalina.authenticator.SingleSignOn.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(1744));//void org.apache.catalina.connector.Connector.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(5410));//void org.apache.catalina.session.StandardManager.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(822));//void org.apache.catalina.cluster.session.DeltaManager.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(3911));//void org.apache.catalina.ha.session.DeltaManager.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(1514));//void org.apache.catalina.cluster.tcp.SimpleTcpCluster.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(4296));//void org.apache.catalina.ha.tcp.SimpleTcpCluster.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(4463));//void org.apache.catalina.loader.WebappLoader.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(5861));//void org.apache.catalina.startup.Embedded.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(4963));//void org.apache.catalina.realm.RealmBase.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(5503));//void org.apache.catalina.session.StoreBase.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(321));//void org.apache.catalina.authenticator.AuthenticatorBase.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(5382));//void org.apache.catalina.session.PersistentManagerBase.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(7683));//void org.apache.catalina.valves.FastCommonAccessLogValve.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(7647));//void org.apache.catalina.valves.ExtendedAccessLogValve.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(7607));//void org.apache.catalina.valves.AccessLogValve.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(7709));//void org.apache.catalina.valves.JDBCAccessLogValve.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(7749));//void org.apache.catalina.valves.SemaphoreValve.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(991));//void org.apache.catalina.cluster.session.JvmRouteBinderValve.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(4092));//void org.apache.catalina.ha.session.JvmRouteBinderValve.addLifecycleListener(org.apache.catalina.LifecycleListener)
        observer1.metodos.add(astModel.metodos.get(7406));//void org.apache.catalina.util.LifecycleSupport.addLifecycleListener(org.apache.catalina.LifecycleListener)
        tomcatCCC.add(observer1);

        
        
        Group observer2 = new Group(); //Seed Identificado FanInResults
        observer2.metodos.add(astModel.metodos.get(2644));//void org.apache.catalina.core.ContainerBase.fireContainerEvent(java.lang.String.java.lang.Object)
        tomcatCCC.add(observer2);

        
        Group observer3 = new Group(); //Seed Identificado FanInResults
        observer3.metodos.add(astModel.metodos.get(7408));//void org.apache.catalina.util.LifecycleSupport.fireLifecycleEvent(java.lang.String.java.lang.Object)        
        tomcatCCC.add(observer3);

        
        Group observer4 = new Group(); //Seed Identificado FanInResults
        observer4.metodos.add(astModel.metodos.get(7409));//void org.apache.catalina.util.LifecycleSupport.removeLifecycleListener(org.apache.catalina.LifecycleListener)
        tomcatCCC.add(observer4);

        Group contract1 = new Group(); //Seed Identificado FanInResults
        contract1.metodos.add(astModel.metodos.get(5530));//boolean org.apache.catalina.Session.isValid()
        contract1.metodos.add(astModel.metodos.get(5436));//boolean org.apache.catalina.session.StandardSession.isValid()
        contract1.metodos.add(astModel.metodos.get(905));//boolean org.apache.catalina.cluster.session.DeltaSession.isValid()
        contract1.metodos.add(astModel.metodos.get(4004));//boolean org.apache.catalina.ha.session.DeltaSession.isValid()
        tomcatCCC.add(contract1);

        Group contract2 = new Group(); //Seed Identificado FanInResults
        contract2.metodos.add(astModel.metodos.get(5046));//boolean org.apache.catalina.security.SecurityUtil.isPackageProtectionEnabled()
        tomcatCCC.add(contract2);
        
        
        Group contract3 = new Group(); //Seed Identificado FanInResults
        contract3.metodos.add(astModel.metodos.get(2157));//boolean org.apache.catalina.connector.Response.isCommitted()
        contract3.metodos.add(astModel.metodos.get(2213));//boolean org.apache.catalina.connector.ResponseFacade.isCommitted()
        tomcatCCC.add(contract3);
        
        
        Group contract4 = new Group(); //Seed Identificado FanInResults
        contract4.metodos.add(astModel.metodos.get(6228));//org.apache.catalina.storeconfig.StoreDescription org.apache.catalina.storeconfig.StoreRegistry.findDescription(java.lang.String)
        tomcatCCC.add(contract4);

       
        Group chain1 = new Group(); //Seed Identificado FanInResults
        chain1.metodos.add(astModel.metodos.get(7578));//void org.apache.catalina.Valve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7765));//void org.apache.catalina.valves.ValveBase.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(3380));//void org.apache.catalina.core.StandardWrapperValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(3127));//void org.apache.catalina.core.StandardContextValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(3160));//void org.apache.catalina.core.StandardEngineValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(3204));//void org.apache.catalina.core.StandardHostValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7719));//void org.apache.catalina.valves.PersistentValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(1444));//void org.apache.catalina.cluster.tcp.ReplicationValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(4240));//void org.apache.catalina.ha.tcp.ReplicationValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(981));//void org.apache.catalina.cluster.session.JvmRouteBinderValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(4082));//void org.apache.catalina.ha.session.JvmRouteBinderValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7616));//void org.apache.catalina.valves.ErrorReportValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7755));//void org.apache.catalina.valves.SemaphoreValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7729));//void org.apache.catalina.valves.RequestDumperValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7636));//void org.apache.catalina.valves.ExtendedAccessLogValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7708));//void org.apache.catalina.valves.JDBCAccessLogValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7597));//void org.apache.catalina.valves.AccessLogValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7675));//void org.apache.catalina.valves.FastCommonAccessLogValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(313));//void org.apache.catalina.authenticator.AuthenticatorBase.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(384));//void org.apache.catalina.authenticator.SingleSignOn.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7737));//void org.apache.catalina.valves.RequestFilterValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7723));//void org.apache.catalina.valves.RemoteAddrValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        chain1.metodos.add(astModel.metodos.get(7726));//void org.apache.catalina.valves.RemoteHostValve.invoke(org.apache.catalina.connector.Request.org.apache.catalina.connector.Response)
        tomcatCCC.add(chain1);
 
        Group command1 = new Group(); //Seed Identificado FanInResults
        command1.metodos.add(astModel.metodos.get(10));//void org.apache.catalina.ant.AbstractCatalinaTask.execute()
        command1.metodos.add(astModel.metodos.get(11));//void org.apache.catalina.ant.AbstractCatalinaTask.execute(java.lang.String)
        command1.metodos.add(astModel.metodos.get(284));//void org.apache.catalina.ant.StartTask.execute()
        command1.metodos.add(astModel.metodos.get(288));//void org.apache.catalina.ant.StopTask.execute()
        command1.metodos.add(astModel.metodos.get(264));//void org.apache.catalina.ant.ReloadTask.execute()
        command1.metodos.add(astModel.metodos.get(280));//void org.apache.catalina.ant.SessionsTask.execute()
        command1.metodos.add(astModel.metodos.get(276));//void org.apache.catalina.ant.ServerinfoTask.execute()
        command1.metodos.add(astModel.metodos.get(292));//void org.apache.catalina.ant.UndeployTask.execute()
        command1.metodos.add(astModel.metodos.get(55));//void org.apache.catalina.ant.InstallTask.execute()
        command1.metodos.add(astModel.metodos.get(260));//void org.apache.catalina.ant.ListTask.execute()
        command1.metodos.add(astModel.metodos.get(250));//void org.apache.catalina.ant.JMXQueryTask.execute()
        command1.metodos.add(astModel.metodos.get(246));//void org.apache.catalina.ant.JMXGetTask.execute()
        command1.metodos.add(astModel.metodos.get(258));//void org.apache.catalina.ant.JMXSetTask.execute()
        command1.metodos.add(astModel.metodos.get(274));//void org.apache.catalina.ant.RolesTask.execute()
        command1.metodos.add(astModel.metodos.get(268));//void org.apache.catalina.ant.RemoveTask.execute()
        command1.metodos.add(astModel.metodos.get(272));//void org.apache.catalina.ant.ResourcesTask.execute()
        command1.metodos.add(astModel.metodos.get(47));//void org.apache.catalina.ant.DeployTask.execute()
        command1.metodos.add(astModel.metodos.get(83));//void org.apache.catalina.ant.JKStatusUpdateTask.execute()
        tomcatCCC.add(command1);
        
        Group consistent1 = new Group(); //Seed Identificado FanInResults
        consistent1.metodos.add(astModel.metodos.get(4603));//java.lang.String org.apache.catalina.mbeans.MBeanUtils.createManagedName(java.lang.Object)
        tomcatCCC.add(consistent1);
        
        Group visitor2 = new Group(); //Seed Identificado FanInResults
        visitor2.metodos.add(astModel.metodos.get(8523));//void org.apache.jasper.compiler.Node$Nodes.visit(org.apache.jasper.compiler.Node$Visitor)
        tomcatCCC.add(visitor2);
        
        
        Group visitor3 = new Group(); //Seed Identificado FanInResults
        visitor3.metodos.add(astModel.metodos.get(8995));//void org.apache.jasper.compiler.Validator$ValidateVisitor$NamedAttributeVisitor.doVisit(org.apache.jasper.compiler.Node)
        visitor3.metodos.add(astModel.metodos.get(7997));//void org.apache.jasper.compiler.ErrorDispatcher$ErrorVisitor.doVisit(org.apache.jasper.compiler.Node)
        visitor3.metodos.add(astModel.metodos.get(8858));//void org.apache.jasper.compiler.SmapUtil$PreScanVisitor.doVisit(org.apache.jasper.compiler.Node)
        visitor3.metodos.add(astModel.metodos.get(8530));//void org.apache.jasper.compiler.Node$Visitor.doVisit(org.apache.jasper.compiler.Node)
        visitor3.metodos.add(astModel.metodos.get(8935));//void org.apache.jasper.compiler.TextOptimizer$TextCatVisitor.doVisit(org.apache.jasper.compiler.Node)
        tomcatCCC.add(visitor3);
        
        Group composite1 = new Group(); //Seed Identificado FanInResults
        composite1.metodos.add(astModel.metodos.get(2265));//org.apache.catalina.Container org.apache.catalina.Container.findChild(java.lang.String)
        composite1.metodos.add(astModel.metodos.get(2621));//org.apache.catalina.Container org.apache.catalina.core.ContainerBase.findChild(java.lang.String)
        tomcatCCC.add(composite1);
              
        
        Group composite2 = new Group(); //Seed Identificado FanInResults
        composite2.metodos.add(astModel.metodos.get(2977));//void org.apache.catalina.core.StandardContext.addChild(org.apache.catalina.Container)
        composite2.metodos.add(astModel.metodos.get(3320));//void org.apache.catalina.core.StandardWrapper.addChild(org.apache.catalina.Container)
        composite2.metodos.add(astModel.metodos.get(3144));//void org.apache.catalina.core.StandardEngine.addChild(org.apache.catalina.Container)
        composite2.metodos.add(astModel.metodos.get(3189));//void org.apache.catalina.core.StandardHost.addChild(org.apache.catalina.Container)
        composite2.metodos.add(astModel.metodos.get(2262));//void org.apache.catalina.Container.addChild(org.apache.catalina.Container)
        composite2.metodos.add(astModel.metodos.get(2617));//void org.apache.catalina.core.ContainerBase.addChild(org.apache.catalina.Container)
        tomcatCCC.add(composite2);

        Group composite3 = new Group(); //Seed Identificado FanInResults
        composite3.metodos.add(astModel.metodos.get(2269));//void org.apache.catalina.Container.removeChild(org.apache.catalina.Container)
        composite3.metodos.add(astModel.metodos.get(2625));//void org.apache.catalina.core.ContainerBase.removeChild(org.apache.catalina.Container)
        composite3.metodos.add(astModel.metodos.get(3035));//void org.apache.catalina.core.StandardContext.removeChild(org.apache.catalina.Container)
        tomcatCCC.add(composite3);
        
        Group composite4 = new Group(); //Seed Identificado FanInResults
        composite4.metodos.add(astModel.metodos.get(2266));//org.apache.catalina.Container[] org.apache.catalina.Container.findChildren()
        composite4.metodos.add(astModel.metodos.get(2622));//org.apache.catalina.Container[] org.apache.catalina.core.ContainerBase.findChildren()
        tomcatCCC.add(composite4);
        
        
        
        Group consistent2 = new Group(); //Seed Identificado FanInResults
        consistent2.metodos.add(astModel.metodos.get(2463));//java.lang.Object org.apache.catalina.core.ApplicationContextFacade.doPrivileged(org.apache.catalina.core.ApplicationContext.java.lang.String.java.lang.Object)
        consistent2.metodos.add(astModel.metodos.get(2464));//java.lang.Object org.apache.catalina.core.ApplicationContextFacade.doPrivileged(java.lang.String.java.lang.Object)
        tomcatCCC.add(consistent2);
        
        Group consistent3 = new Group(); //Seed Identificado FanInResults
        consistent3.metodos.add(astModel.metodos.get(10268));//java.lang.String org.apache.naming.SelectorContext.parseName(java.lang.String)
        consistent3.metodos.add(astModel.metodos.get(10269));//javax.naming.Name org.apache.naming.SelectorContext.parseName(javax.naming.Name)
        tomcatCCC.add(consistent3);
 
        
        Group consistent4 = new Group(); //Seed Identificado FanInResults
        consistent4.metodos.add(astModel.metodos.get(10119));//javax.naming.Name org.apache.naming.resources.ProxyDirContext.parseName(javax.naming.Name)
        consistent4.metodos.add(astModel.metodos.get(10118));//java.lang.String org.apache.naming.resources.ProxyDirContext.parseName(java.lang.String)
        tomcatCCC.add(consistent4);
        
        Group consistent5 = new Group(); //Seed Identificado FanInResults
        consistent5.metodos.add(astModel.metodos.get(10124));//boolean org.apache.naming.resources.ProxyDirContext.cacheUnload(java.lang.String)
        tomcatCCC.add(consistent5);

                
        Group consistent6 = new Group(); //Seed Identificado FanInResults
        consistent6.metodos.add(astModel.metodos.get(7868));//void org.apache.jasper.compiler.Dumper$DumpVisitor.printString(java.lang.String.char.java.lang.String)
        consistent6.metodos.add(astModel.metodos.get(7867));//void org.apache.jasper.compiler.Dumper$DumpVisitor.printString(java.lang.String)
        tomcatCCC.add(consistent6);

        Group consistent7 = new Group(); //Seed Identificado FanInResults
        consistent7.metodos.add(astModel.metodos.get(7870));//void org.apache.jasper.compiler.Dumper$DumpVisitor.printAttributes(java.lang.String.org.xml.sax.Attributes.java.lang.String)
        tomcatCCC.add(consistent7);
        
        Group consistent8 = new Group(); //Seed Identificado FanInResults
        consistent8.metodos.add(astModel.metodos.get(7871));//void org.apache.jasper.compiler.Dumper$DumpVisitor.dumpBody(org.apache.jasper.compiler.Node)
        tomcatCCC.add(consistent8);
        
        Group consistent9 = new Group(); //Seed Identificado FanInResults
        consistent9.metodos.add(astModel.metodos.get(7475));//java.lang.String org.apache.catalina.util.RequestUtil.filter(java.lang.String)
        tomcatCCC.add(consistent9);

        Group consistent10 = new Group(); //Seed Identificado FanInResults
        consistent10.metodos.add(astModel.metodos.get(9463));//boolean org.apache.jasper.security.SecurityUtil.isPackageProtectionEnabled()
        tomcatCCC.add(consistent10);
        
        Group consistent11 = new Group(); //Seed Identificado FanInResults
        consistent11.metodos.add(astModel.metodos.get(8855));//void org.apache.jasper.compiler.SmapUtil$SmapGenVisitor.doSmap(org.apache.jasper.compiler.Node)
        tomcatCCC.add(consistent11);

        Group consistent12 = new Group(); //Seed Identificado FanInResults
        consistent12.metodos.add(astModel.metodos.get(8724));//org.apache.jasper.compiler.Parser.parseOptionalBody(org.apache.jasper.compiler.Node.java.lang.String.java.lang.String)
        tomcatCCC.add(consistent12);
        
        Group consistent13 = new Group(); //Seed Identificado FanInResults
        consistent13.metodos.add(astModel.metodos.get(8689));//org.xml.sax.Attributes org.apache.jasper.compiler.Parser.parseAttributes()
        tomcatCCC.add(consistent13);

        Group consistent14 = new Group(); //Seed Identificado FanInResults
        consistent14.metodos.add(astModel.metodos.get(8604));//void org.apache.jasper.compiler.PageDataImpl$SecondPassVisitor.appendTag(org.apache.jasper.compiler.Node)
        tomcatCCC.add(consistent14);
        
        Group consistent15 = new Group(); //Seed Identificado FanInResults
        consistent15.metodos.add(astModel.metodos.get(8213));//void org.apache.jasper.compiler.JspUtil.checkAttributes(java.lang.String.org.apache.jasper.compiler.Node.org.apache.jasper.compiler.JspUtil$ValidAttribute.org.apache.jasper.compiler.ErrorDispatcher)}
        tomcatCCC.add(consistent15);
        
        Group consistent16 = new Group(); //Seed Identificado FanInResults
        consistent16.metodos.add(astModel.metodos.get(7993));//void org.apache.jasper.compiler.ErrorDispatcher.dispatch(org.apache.jasper.compiler.Mark.java.lang.String.java.lang.Object.java.lang.Exception)
        tomcatCCC.add(consistent16);
        
        Group redirector1 = new Group(); //Seed Identificado FanInResults
        redirector1.metodos.add(astModel.metodos.get(9590));//java.io.PrintStream org.apache.jasper.util.SystemLogHandler.findStream()
        tomcatCCC.add(redirector1);

        return tomcatCCC;
    }
    
    public static List<Group> jhotdrawIdealGroups(ASTModel astModel){
        List<Group> jhotdrawCCC = new ArrayList<Group>();
        /* Removido.. é um get
        Group adapter1 = new Group();
        adapter1.metodos.add(astModel.metodos.get(1651));//CH.ifa.draw.framework.Figure CH.ifa.draw.framework.Handle.owner()
        adapter1.metodos.add(astModel.metodos.get(1942));//++CH.ifa.draw.framework.Figure CH.ifa.draw.standard.AbstractHandle.owner()
         *            
           

        jhotdrawCCC.add(adapter1);
         */
        Group command1 = new Group();
        command1.metodos.add(astModel.metodos.get(1860));//##void CH.ifa.draw.standard.AbstractCommand.execute()
        command1.metodos.add(astModel.metodos.get(962));//++void CH.ifa.draw.contrib.zoom.ZoomCommand.execute()
        command1.metodos.add(astModel.metodos.get(1143));//++void CH.ifa.draw.figures.GroupCommand.execute()
        command1.metodos.add(astModel.metodos.get(1173));//++void CH.ifa.draw.figures.InsertImageCommand.execute()
        command1.metodos.add(astModel.metodos.get(1437));//++void CH.ifa.draw.figures.UngroupCommand.execute()
        command1.metodos.add(astModel.metodos.get(2007));//++void CH.ifa.draw.standard.AlignCommand.execute()
        command1.metodos.add(astModel.metodos.get(2058));//++void CH.ifa.draw.standard.BringToFrontCommand.execute()
        command1.metodos.add(astModel.metodos.get(2066));//++void CH.ifa.draw.standard.ChangeAttributeCommand.execute()
        command1.metodos.add(astModel.metodos.get(2223));//++void CH.ifa.draw.standard.CopyCommand.execute()
        command1.metodos.add(astModel.metodos.get(2240));//++void CH.ifa.draw.standard.CutCommand.execute()
        command1.metodos.add(astModel.metodos.get(2287));//++void CH.ifa.draw.standard.DeleteCommand.execute()
        command1.metodos.add(astModel.metodos.get(2315));//++void CH.ifa.draw.standard.DuplicateCommand.execute()
        command1.metodos.add(astModel.metodos.get(2461));//++void CH.ifa.draw.standard.PasteCommand.execute()
        command1.metodos.add(astModel.metodos.get(2504));//++void CH.ifa.draw.standard.SelectAllCommand.execute()
        command1.metodos.add(astModel.metodos.get(2530));//++void CH.ifa.draw.standard.SendToBackCommand.execute()
        command1.metodos.add(astModel.metodos.get(2674));//++void CH.ifa.draw.standard.ToggleGridCommand.execute()
        command1.metodos.add(astModel.metodos.get(2841));//++void CH.ifa.draw.util.Command.execute()
        command1.metodos.add(astModel.metodos.get(2976));//++void CH.ifa.draw.util.RedoCommand.execute()
        command1.metodos.add(astModel.metodos.get(3075));//++void CH.ifa.draw.util.UndoableCommand.execute()
        command1.metodos.add(astModel.metodos.get(3143));//++void CH.ifa.draw.util.UndoCommand.execute()
        
        
        jhotdrawCCC.add(command1);
        
        Group composite1 = new Group(); //add() -> GroupCommandUndo.aj
        composite1.metodos.add(astModel.metodos.get(1528));//CH.ifa.draw.framework.Figure CH.ifa.draw.framework.DrawingView.add(CH.ifa.draw.framework.Figure)
        composite1.metodos.add(astModel.metodos.get(2551));//CH.ifa.draw.framework.Figure CH.ifa.draw.standard.StandardDrawing.add(CH.ifa.draw.framework.Figure)
        composite1.metodos.add(astModel.metodos.get(2574));//CH.ifa.draw.framework.Figure CH.ifa.draw.standard.StandardDrawingView.add(CH.ifa.draw.framework.Figure)
        composite1.metodos.add(astModel.metodos.get(1715));//CH.ifa.draw.framework.Figure CH.ifa.draw.samples.javadraw.BouncingDrawing.add(CH.ifa.draw.framework.Figure)
        composite1.metodos.add(astModel.metodos.get(2396));//++CH.ifa.draw.framework.Figure CH.ifa.draw.standard.NullDrawingView.add(CH.ifa.draw.framework.Figure)  --Faltou
        composite1.metodos.add(astModel.metodos.get(1486));//++CH.ifa.draw.framework.Figure CH.ifa.draw.framework.Drawing.add(CH.ifa.draw.framework.Figure) --Faltou (interface)
        jhotdrawCCC.add(composite1);
        /*
        Group composite3 = new Group();
        composite3.metodos.add(astModel.metodos.get(1580));//CH.ifa.draw.framework.FigureEnumeration CH.ifa.draw.framework.Figure.figures()
        composite3.metodos.add(astModel.metodos.get(1896));//CH.ifa.draw.framework.FigureEnumeration CH.ifa.draw.standard.AbstractFigure.figures()
        composite3.metodos.add(astModel.metodos.get(2150));//CH.ifa.draw.framework.FigureEnumeration CH.ifa.draw.standard.CompositeFigure.figures()
        composite3.metodos.add(astModel.metodos.get(1236));//++CH.ifa.draw.framework.FigureEnumeration CH.ifa.draw.figures.NullFigure.figures() --Faltou
        composite3.metodos.add(astModel.metodos.get(2270));//++CH.ifa.draw.framework.FigureEnumeration CH.ifa.draw.standard.DecoratorFigure.figures() --Faltou (interface)
        jhotdrawCCC.add(composite3);
        */
        /*
        Group composite4 = new Group();
        composite4.metodos.add(astModel.metodos.get(1239));//boolean CH.ifa.draw.figures.NullFigure.includes(CH.ifa.draw.framework.Figure)
        composite4.metodos.add(astModel.metodos.get(1585));//boolean CH.ifa.draw.framework.Figure.includes(CH.ifa.draw.framework.Figure)
        composite4.metodos.add(astModel.metodos.get(1902));//boolean CH.ifa.draw.standard.AbstractFigure.includes(CH.ifa.draw.framework.Figure)
        composite4.metodos.add(astModel.metodos.get(2161));//boolean CH.ifa.draw.standard.CompositeFigure.includes(CH.ifa.draw.framework.Figure)
        composite4.metodos.add(astModel.metodos.get(2261));//##boolean CH.ifa.draw.standard.DecoratorFigure.includes(CH.ifa.draw.framework.Figure)
        jhotdrawCCC.add(composite4);
        */
        /*
        Group consistent1 = new Group();//Não refatorado
        consistent1.metodos.add(astModel.metodos.get(24));//void CH.ifa.draw.applet.DrawApplet.toolDone()
        consistent1.metodos.add(astModel.metodos.get(97));//void CH.ifa.draw.application.DrawApplication.toolDone()
        consistent1.metodos.add(astModel.metodos.get(1517));//void CH.ifa.draw.framework.DrawingEditor.toolDone()
        consistent1.metodos.add(astModel.metodos.get(1757));//void CH.ifa.draw.samples.javadraw.JavaDrawViewer.toolDone()
        jhotdrawCCC.add(consistent1);
         */
        Group consistent1 = new Group(); //UndoableCommand.aj
        consistent1.metodos.add(astModel.metodos.get(1861));//boolean CH.ifa.draw.standard.AbstractCommand.isExecutable() 
        consistent1.metodos.add(astModel.metodos.get(2842));//++boolean CH.ifa.draw.util.Command.isExecutable()
        consistent1.metodos.add(astModel.metodos.get(3076));//++boolean CH.ifa.draw.util.UndoableCommand.isExecutable()
        jhotdrawCCC.add(consistent1);
        
        Group consistent2 = new Group();//CommandContracts.aj -> Refatorado totalmente
        consistent2.metodos.add(astModel.metodos.get(1551));//void CH.ifa.draw.framework.DrawingView.checkDamage()
        consistent2.metodos.add(astModel.metodos.get(2419));//void CH.ifa.draw.standard.NullDrawingView.checkDamage()
        consistent2.metodos.add(astModel.metodos.get(2606));//void CH.ifa.draw.standard.StandardDrawingView.checkDamage()
        jhotdrawCCC.add(consistent2);
        /*
        Group consistent3 = new Group();
        consistent3.metodos.add(astModel.metodos.get(1538));//int CH.ifa.draw.framework.DrawingView.selectionCount()
        consistent3.metodos.add(astModel.metodos.get(2406));//int CH.ifa.draw.standard.NullDrawingView.selectionCount()
        consistent3.metodos.add(astModel.metodos.get(2586));//int CH.ifa.draw.standard.StandardDrawingView.selectionCount()
        jhotdrawCCC.add(consistent3);
        */
        /*
        Group consistent5 = new Group(); //Não refatorado
        consistent5.metodos.add(astModel.metodos.get(1953));//void CH.ifa.draw.standard.AbstractTool.activate()
        consistent5.metodos.add(astModel.metodos.get(3112));//++void CH.ifa.draw.util.UndoableTool.activate()
        consistent5.metodos.add(astModel.metodos.get(1675));//++void CH.ifa.draw.framework.Tool.activate()
        consistent5.metodos.add(astModel.metodos.get(1076));//++void CH.ifa.draw.figures.ConnectedTextTool.activate()
        consistent5.metodos.add(astModel.metodos.get(839));//++void CH.ifa.draw.contrib.TextAreaTool.activate()
        consistent5.metodos.add(astModel.metodos.get(1411));//++void CH.ifa.draw.figures.TextTool.activate()
        consistent5.metodos.add(astModel.metodos.get(1953));//++void CH.ifa.draw.standard.AbstractTool.activate()
        consistent5.metodos.add(astModel.metodos.get(2227));//++void CH.ifa.draw.standard.CreationTool.activate()
        consistent5.metodos.add(astModel.metodos.get(2446));//++void CH.ifa.draw.standard.NullTool.activate()
        consistent5.metodos.add(astModel.metodos.get(293));//++void CH.ifa.draw.contrib.dnd.DragNDropTool.activate()
        consistent5.metodos.add(astModel.metodos.get(720));//++void CH.ifa.draw.contrib.PolygonTool.activate()
        consistent5.metodos.add(astModel.metodos.get(1346));//++void CH.ifa.draw.figures.ScribbleTool.activate()
        consistent5.metodos.add(astModel.metodos.get(2303));//++void CH.ifa.draw.standard.DragTracker.activate()
        consistent5.metodos.add(astModel.metodos.get(2370));//++void CH.ifa.draw.standard.HandleTracker.activate()
        jhotdrawCCC.add(consistent5);
        */
        /*
        Group consistent6 = new Group(); //Não Refatorado
        consistent6.metodos.add(astModel.metodos.get(1954));//void CH.ifa.draw.standard.AbstractTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(2304));//++void CH.ifa.draw.standard.DragTracker.deactivate()
        consistent6.metodos.add(astModel.metodos.get(721));//++void CH.ifa.draw.contrib.PolygonTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(1347));//++void CH.ifa.draw.figures.ScribbleTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(294));//++void CH.ifa.draw.contrib.dnd.DragNDropTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(838));//++void CH.ifa.draw.contrib.TextAreaTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(1410));//++void CH.ifa.draw.figures.TextTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(2447));//++void CH.ifa.draw.standard.NullTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(1676));//++void CH.ifa.draw.framework.Tool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(1954));//++void CH.ifa.draw.standard.AbstractTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(748));//++void CH.ifa.draw.contrib.SplitConnectionTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(2195));//++void CH.ifa.draw.standard.ConnectionTool.deactivate()
        consistent6.metodos.add(astModel.metodos.get(3113));//++void CH.ifa.draw.util.UndoableTool.deactivate()
        jhotdrawCCC.add(consistent6);
         */
        /*
        Group consistent7 = new Group(); //Não Refatorado
        consistent7.metodos.add(astModel.metodos.get(1958));//void CH.ifa.draw.standard.AbstractTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(175));//++void CH.ifa.draw.contrib.CompositeFigureCreationTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(2367));//++void CH.ifa.draw.standard.HandleTracker.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(893));//++void CH.ifa.draw.contrib.zoom.AreaTracker.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(2512));//++void CH.ifa.draw.standard.SelectAreaTracker.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(2301));//++void CH.ifa.draw.standard.DragTracker.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(723));//++void CH.ifa.draw.contrib.PolygonTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1349));//++void CH.ifa.draw.figures.ScribbleTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(744));//++void CH.ifa.draw.contrib.SplitConnectionTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(2192));//++void CH.ifa.draw.standard.ConnectionTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(299));//++void CH.ifa.draw.contrib.dnd.DragNDropTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(2520));//++void CH.ifa.draw.standard.SelectionTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1768));//++void CH.ifa.draw.samples.javadraw.URLTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1060));//++void CH.ifa.draw.figures.BorderTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1997));//++void CH.ifa.draw.standard.ActionTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1677));//++void CH.ifa.draw.framework.Tool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(655));//++void CH.ifa.draw.contrib.NestedCreationTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(2228));//++void CH.ifa.draw.standard.CreationTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1072));//++void CH.ifa.draw.figures.ConnectedTextTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(835));//++void CH.ifa.draw.contrib.TextAreaTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1407));//++void CH.ifa.draw.figures.TextTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(201));//++void CH.ifa.draw.contrib.CustomSelectionTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1762));//++void CH.ifa.draw.samples.javadraw.MySelectionTool.mouseDown(java.awt.event.MouseEvent,intint)
        consistent7.metodos.add(astModel.metodos.get(1004));//++void CH.ifa.draw.contrib.zoom.ZoomTool.mouseDown(java.awt.event.MouseEvent,int,int)
        
        jhotdrawCCC.add(consistent7);
         */
        Group consistent3 = new Group(); //Refatorado *Undo.aj
        consistent3.metodos.add(astModel.metodos.get(2413));//void CH.ifa.draw.standard.NullDrawingView.clearSelection()
        consistent3.metodos.add(astModel.metodos.get(2593));//void CH.ifa.draw.standard.StandardDrawingView.clearSelection()
        consistent3.metodos.add(astModel.metodos.get(1545));//void CH.ifa.draw.framework.DrawingView.clearSelection()
        jhotdrawCCC.add(consistent3);
        
        Group undo1 = new Group(); //Refatorado *Undo.aj
        undo1.metodos.add(astModel.metodos.get(3048));//boolean CH.ifa.draw.util.Undoable.undo()
        undo1.metodos.add(astModel.metodos.get(3060));//boolean CH.ifa.draw.util.UndoableAdapter.undo()
        undo1.metodos.add(astModel.metodos.get(3050));//++boolean CH.ifa.draw.util.Undoable.isUndoable()
        undo1.metodos.add(astModel.metodos.get(3062));//++boolean CH.ifa.draw.util.UndoableAdapter.isUndoable()
        undo1.metodos.add(astModel.metodos.get(714));//++boolean CH.ifa.draw.contrib.PolygonScaleHandle$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(851));//++boolean CH.ifa.draw.contrib.TextAreaTool$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(882));//++boolean CH.ifa.draw.contrib.TriangleRotationHandle$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1065));//++boolean CH.ifa.draw.figures.BorderTool$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1080));//++boolean CH.ifa.draw.figures.ConnectedTextTool$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1085));//++boolean CH.ifa.draw.figures.ConnectedTextTool$DeleteUndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1134));//++boolean CH.ifa.draw.figures.FontSizeHandle$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1147));//++]boolean CH.ifa.draw.figures.GroupCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1178));//++boolean CH.ifa.draw.figures.InsertImageCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1299));//++boolean CH.ifa.draw.figures.PolyLineHandle$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1316));//++boolean CH.ifa.draw.figures.RadiusHandle$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1428));//++boolean CH.ifa.draw.figures.TextTool$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(1441));//++boolean CH.ifa.draw.figures.UngroupCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2012));//++boolean CH.ifa.draw.standard.AlignCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2036));//++boolean CH.ifa.draw.standard.ResizeHandle$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2070));//++boolean CH.ifa.draw.standard.ChangeAttributeCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2108));//++boolean CH.ifa.draw.standard.ChangeConnectionHandle$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2218));//++boolean CH.ifa.draw.standard.ConnectionTool$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2244));//++boolean CH.ifa.draw.standard.CutCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2291));//++boolean CH.ifa.draw.standard.DeleteCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2307));//++boolean CH.ifa.draw.standard.DragTracker$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2466));//++boolean CH.ifa.draw.standard.PasteCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2508));//++boolean CH.ifa.draw.standard.SelectAllCommand$UndoActivity.undo()
        undo1.metodos.add(astModel.metodos.get(2534));//++boolean CH.ifa.draw.standard.SendToBackCommand$UndoActivity.undo()
        jhotdrawCCC.add(undo1);
        Group undo2 = new Group();//Refatorado *Undo.aj
        undo2.metodos.add(astModel.metodos.get(3052));//boolean CH.ifa.draw.util.Undoable.isRedoable()
        undo2.metodos.add(astModel.metodos.get(3064));//boolean CH.ifa.draw.util.UndoableAdapter.isRedoable()
        undo2.metodos.add(astModel.metodos.get(3049));//++boolean CH.ifa.draw.util.Undoable.redo()
        undo2.metodos.add(astModel.metodos.get(3061));//++boolean CH.ifa.draw.util.UndoableAdapter.redo()
        undo2.metodos.add(astModel.metodos.get(715));//++boolean CH.ifa.draw.contrib.PolygonScaleHandle$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(852));//++[852]boolean CH.ifa.draw.contrib.TextAreaTool$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(883));//++boolean CH.ifa.draw.contrib.TriangleRotationHandle$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1066));//++boolean CH.ifa.draw.figures.BorderTool$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1081));//++boolean CH.ifa.draw.figures.ConnectedTextTool$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1135));//++boolean CH.ifa.draw.figures.FontSizeHandle$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1148));//++boolean CH.ifa.draw.figures.GroupCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1179));//++boolean CH.ifa.draw.figures.InsertImageCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1300));//++boolean CH.ifa.draw.figures.PolyLineHandle$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1317));//++boolean CH.ifa.draw.figures.RadiusHandle$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1429));//++boolean CH.ifa.draw.figures.TextTool$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1442));//++boolean CH.ifa.draw.figures.UngroupCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2013));//++boolean CH.ifa.draw.standard.AlignCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2037));//++boolean CH.ifa.draw.standard.ResizeHandle$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2071));//++boolean CH.ifa.draw.standard.ChangeAttributeCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2109));//++boolean CH.ifa.draw.standard.ChangeConnectionHandle$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2219));//++boolean CH.ifa.draw.standard.ConnectionTool$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2245));//++boolean CH.ifa.draw.standard.CutCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2292));//++boolean CH.ifa.draw.standard.DeleteCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2308));//++boolean CH.ifa.draw.standard.DragTracker$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2467));//++boolean CH.ifa.draw.standard.PasteCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2509));//++boolean CH.ifa.draw.standard.SelectAllCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(2535));//++boolean CH.ifa.draw.standard.SendToBackCommand$UndoActivity.redo()
        undo2.metodos.add(astModel.metodos.get(1086));//++boolean CH.ifa.draw.figures.ConnectedTextTool$DeleteUndoActivity.redo()
        jhotdrawCCC.add(undo2);
        
        
        /*
        Group adapter1 = new Group();
        adapter1.metodos.add(astModel.metodos.get(1582));//++boolean CH.ifa.draw.framework.Figure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(441));//++boolean CH.ifa.draw.contrib.html.HTMLTextAreaFigure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(2251));//++boolean CH.ifa.draw.standard.DecoratorFigure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(1787));//++boolean CH.ifa.draw.samples.net.NodeFigure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(1900));//++boolean CH.ifa.draw.standard.AbstractFigure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(677));//++boolean CH.ifa.draw.contrib.PolygonFigure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(1279));//++boolean CH.ifa.draw.figures.PolyLineFigure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(257));//++boolean CH.ifa.draw.contrib.DiamondFigure.containsPoint(int,int)
        adapter1.metodos.add(astModel.metodos.get(866));//++boolean CH.ifa.draw.contrib.TriangleFigure.containsPoint(int,int)
        jhotdrawCCC.add(adapter1);
        */
        
        Group command2 = new Group(); //AlignCommand
        command2.metodos.add(astModel.metodos.get(2262));//##void CH.ifa.draw.standard.DecoratorFigure.moveBy(intint) Movido de decorator
        command2.metodos.add(astModel.metodos.get(442));//++void CH.ifa.draw.contrib.html.HTMLTextAreaFigure.moveBy(intint)
        command2.metodos.add(astModel.metodos.get(805));//++void CH.ifa.draw.contrib.TextAreaFigure.moveBy(intint)
        command2.metodos.add(astModel.metodos.get(1361));//++void CH.ifa.draw.figures.TextFigure.moveBy(intint)
        command2.metodos.add(astModel.metodos.get(1571));//++void CH.ifa.draw.framework.Figure.moveBy(intint)
        command2.metodos.add(astModel.metodos.get(1890));//++void void CH.ifa.draw.standard.AbstractFigure.moveBy(intint)
        jhotdrawCCC.add(command2);
        
        
        Group observer1 = new Group(); //Refatorado PersistentTextFigure.aj  Observer
        observer1.metodos.add(astModel.metodos.get(1593));//void CH.ifa.draw.framework.Figure.addFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener)
        observer1.metodos.add(astModel.metodos.get(1594));//void CH.ifa.draw.framework.Figure.removeFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener)
        observer1.metodos.add(astModel.metodos.get(1906));//void CH.ifa.draw.standard.AbstractFigure.addFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener)
        observer1.metodos.add(astModel.metodos.get(1907));//void CH.ifa.draw.standard.AbstractFigure.removeFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener)
        //observer1.metodos.add(astModel.metodos.get(1592));//CH.ifa.draw.framework.FigureChangeListener CH.ifa.draw.framework.Figure.listener()
        //observer1.metodos.add(astModel.metodos.get(1908));//++CH.ifa.draw.framework.FigureChangeListener CH.ifa.draw.standard.AbstractFigure.listener()
        
        jhotdrawCCC.add(observer1);
        
        Group observer4 = new Group(); //Não Refatorado Ligado ao PersistentTextFigure.aj e FigureChangeListener Observer, sempre ocorre around
        observer4.metodos.add(astModel.metodos.get(1371));//void CH.ifa.draw.figures.TextFigure.changed()
        observer4.metodos.add(astModel.metodos.get(1598));//void CH.ifa.draw.framework.Figure.changed()
        observer4.metodos.add(astModel.metodos.get(1597));//void CH.ifa.draw.framework.Figure.willChange()
        observer4.metodos.add(astModel.metodos.get(1913));//void CH.ifa.draw.standard.AbstractFigure.changed()
        observer4.metodos.add(astModel.metodos.get(1912));//void CH.ifa.draw.standard.AbstractFigure.willChange()
        jhotdrawCCC.add(observer4);
        
        
        Group observer2 = new Group(); //Refatorado no AJHotDraw .. SelectionChangedNotification.aj .. Observer
        observer2.metodos.add(astModel.metodos.get(1596));//++void CH.ifa.draw.framework.Figure.invalidate()
        observer2.metodos.add(astModel.metodos.get(1242));//++void CH.ifa.draw.figures.NullFigure.invalidate()
        observer2.metodos.add(astModel.metodos.get(1910));//++CH.ifa.draw.standard.AbstractFigure.invalidate()
        jhotdrawCCC.add(observer2);

        Group observer3 = new Group(); //Refatorado no AJHotDraw .. SelectionChangedNotification, FigureSelectionSubjectRole.aj Observer
        observer3.metodos.add(astModel.metodos.get(1566));//++void CH.ifa.draw.framework.DrawingView.addFigureSelectionListener(CH.ifa.draw.framework.FigureSelectionListener)
        observer3.metodos.add(astModel.metodos.get(1567));//++void CH.ifa.draw.framework.DrawingView.removeFigureSelectionListener(CH.ifa.draw.framework.FigureSelectionListener)
        observer3.metodos.add(astModel.metodos.get(2434));//++void CH.ifa.draw.standard.NullDrawingView.addFigureSelectionListener(CH.ifa.draw.framework.FigureSelectionListener)
        observer3.metodos.add(astModel.metodos.get(2435));//++void CH.ifa.draw.standard.NullDrawingView.removeFigureSelectionListener(CH.ifa.draw.framework.FigureSelectionListener)
        observer3.metodos.add(astModel.metodos.get(2633));//++void CH.ifa.draw.standard.StandardDrawingView.addFigureSelectionListener(CH.ifa.draw.framework.FigureSelectionListener)
        observer3.metodos.add(astModel.metodos.get(2634));//++void CH.ifa.draw.standard.StandardDrawingView.removeFigureSelectionListener(CH.ifa.draw.framework.FigureSelectionListener)
        jhotdrawCCC.add(observer3);

        
        Group persistence1 = new Group(); //Refatorado Persistence*.aj
        //persistence1.metodos.add(astModel.metodos.get(3023));//void CH.ifa.draw.util.StorableOutput.writeInt
        //persistence1.metodos.add(astModel.metodos.get(3022));//void CH.ifa.draw.util.StorableOutput.writeStorable(CH.ifa.draw.util.Storable)
        //persistence1.metodos.add(astModel.metodos.get(3028));//void CH.ifa.draw.util.StorableOutput.writeString(java.lang.String)
        //persistence1.metodos.add(astModel.metodos.get(3027));//++void CH.ifa.draw.util.StorableOutput.writeBoolean(boolean) --Faltou
        persistence1.metodos.add(astModel.metodos.get(3008));//++void CH.ifa.draw.util.Storable.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(1925));//++void CH.ifa.draw.standard.AbstractFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(871));//++void CH.ifa.draw.contrib.TriangleFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(815));//++void CH.ifa.draw.contrib.TextAreaFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(1385));//++void CH.ifa.draw.figures.TextFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(690));//++void CH.ifa.draw.contrib.PolygonFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(1169));//++void CH.ifa.draw.figures.ImageFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(1329));//++void CH.ifa.draw.figures.RectangleFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(1116));//++void CH.ifa.draw.figures.EllipseFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(1343));//++void CH.ifa.draw.figures.RoundRectangleFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(472));//++void CH.ifa.draw.contrib.html.HTMLTextAreaFigure.write(CH.ifa.draw.util.StorableOutput)
        persistence1.metodos.add(astModel.metodos.get(1047));//++void CH.ifa.draw.figures.AttributeFigure.write(CH.ifa.draw.util.StorableOutput)
        jhotdrawCCC.add(persistence1);

        Group persistence2 = new Group(); //Refatorado Persistence*.aj
        //persistence2.metodos.add(astModel.metodos.get(3013));//int CH.ifa.draw.util.StorableInput.readInt()
        //persistence2.metodos.add(astModel.metodos.get(3011));//CH.ifa.draw.util.Storable CH.ifa.draw.util.StorableInput.readStorable()
        //persistence2.metodos.add(astModel.metodos.get(3012));//java.lang.String CH.ifa.draw.util.StorableInput.readString()
        //persistence2.metodos.add(astModel.metodos.get(3017));//++boolean CH.ifa.draw.util.StorableInput.readBoolean() --Faltou
        persistence2.metodos.add(astModel.metodos.get(3009));//++void CH.ifa.draw.util.Storable.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(1926));//++void CH.ifa.draw.standard.AbstractFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(872));//++void CH.ifa.draw.contrib.TriangleFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(1170));//++void CH.ifa.draw.figures.ImageFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(691));//++void CH.ifa.draw.contrib.PolygonFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(1330));//++void CH.ifa.draw.figures.RectangleFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(1117));//++void CH.ifa.draw.figures.EllipseFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(1344));//++void CH.ifa.draw.figures.RoundRectangleFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(1048));//++void CH.ifa.draw.figures.AttributeFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(471));//++void CH.ifa.draw.contrib.html.HTMLTextAreaFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(816));//++void CH.ifa.draw.contrib.TextAreaFigure.read(CH.ifa.draw.util.StorableInput)
        persistence2.metodos.add(astModel.metodos.get(1386));//++void CH.ifa.draw.figures.TextFigure.read(CH.ifa.draw.util.StorableInput)
        jhotdrawCCC.add(persistence2);

        
        
        return jhotdrawCCC;
    }
        
}
