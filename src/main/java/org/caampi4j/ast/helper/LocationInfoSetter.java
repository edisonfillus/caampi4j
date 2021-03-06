package org.caampi4j.ast.helper;

import com.sun.source.tree.CompilationUnitTree;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.tools.JavaFileObject;

import org.caampi4j.ast.common.model.ClassInfo;
import org.caampi4j.ast.common.model.Field;
import org.caampi4j.ast.common.model.LocationInfo;
import org.caampi4j.ast.common.model.Method;
import org.caampi4j.ast.common.util.CodeAnalyzerUtil;

/**
 * Helper class to set the location info of class, methods and fields
 * to the java class model
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class LocationInfoSetter {

    /**
     * Set the location info of class and its methods and fields to 
     * the java class model
     * @param clazzInfo The java class model
     */
    public static void setLocationInfoForElements(ClassInfo clazzInfo) {
        try {
            //Get compilation unit tree
            CompilationUnitTree compileTree = clazzInfo.getSourceTreeInfo().
                                                            getCompileTree();
            //Java file which is being processed
            JavaFileObject file = compileTree.getSourceFile();
            String javaFileContent = file.getCharContent(true).toString();
            //Convert the java file content to character buffer
            CharBuffer buffer = getCharacterBufferOfSource(javaFileContent);
            //Set location info for various elements
            setLocInfoOfClass(clazzInfo, buffer, compileTree);
            setLocInfoOfConstructors(clazzInfo, buffer, compileTree);
            setLocInfoOfMethods(clazzInfo, buffer, compileTree);
            setLocInfoOfVariables(clazzInfo, buffer, compileTree);
        } catch (java.lang.IllegalStateException ex){
          //Logger.getLogger(LocationInfoSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
          Logger.getLogger(LocationInfoSetter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Converts the java file content to character buffer
     * @param javaFile Content of java file being processed
     * @return Character buffer representation of the java source file
     */
    private static CharBuffer getCharacterBufferOfSource(String javaFile) {
        CharBuffer charBuffer = CharBuffer.wrap (javaFile.toCharArray());
        return charBuffer;
    }    

    /**
     * Set the location info of class
     * @param clazzInfo The java class model
     * @param buffer character buffer representation of java source
     * @param compileTree Compilation unit tree
     */
    private static void setLocInfoOfClass(ClassInfo clazzInfo, 
                        CharBuffer buffer, CompilationUnitTree compileTree) {
        String clazzName = CodeAnalyzerUtil.getSimpleNameFromQualifiedName
                                                 (clazzInfo.getName());
        LocationInfo clazzNameLoc = (LocationInfo) clazzInfo.getLocationInfo();
        int startIndex = clazzNameLoc.getStartOffset();
        int endIndex = -1;
        if (startIndex >= 0) {
            String strToSearch = buffer.subSequence(startIndex, 
                                                    buffer.length()).toString();
            Pattern p = Pattern.compile(clazzName);
            Matcher matcher = p.matcher(strToSearch);
            matcher.find();
            startIndex = matcher.start() + startIndex;
            endIndex = startIndex + clazzName.length();
        } 
        clazzNameLoc.setStartOffset(startIndex);
        clazzNameLoc.setEndOffset(endIndex);
        clazzNameLoc.setLineNumber(compileTree.getLineMap().
                                   getLineNumber(startIndex));
    }

    /**
     * Set the location info of constructors
     * @param clazzInfo The java class model
     * @param buffer character buffer representation of java source
     * @param compileTree Compilation unit tree
     */    
    private static void setLocInfoOfConstructors(ClassInfo clazzInfo, 
                        CharBuffer buffer, CompilationUnitTree compileTree) {
        for (Method method : clazzInfo.getConstructors()) {
            LocationInfo constructorNameLoc = (LocationInfo) 
                                                      method.getLocationInfo();
            int startIndex = constructorNameLoc.getStartOffset();
            int endIndex = -1;
            if (startIndex >= 0) {
                String strToSearch = buffer.subSequence(startIndex, 
                                                   buffer.length()).toString();
                Pattern p = Pattern.compile(method.getName());
                Matcher matcher = p.matcher(strToSearch);
                matcher.find();
                startIndex = matcher.start() + startIndex;
                endIndex = startIndex + method.getName().length();
            }
            constructorNameLoc.setStartOffset(startIndex);
            constructorNameLoc.setEndOffset(endIndex);
            constructorNameLoc.setLineNumber(compileTree.getLineMap().
                                                    getLineNumber(startIndex));
        }
    }
    /**
     * Set the location info of methods
     * @param clazzInfo The java class model
     * @param buffer character buffer representation of java source
     * @param compileTree Compilation unit tree
     */
    private static void setLocInfoOfMethods(ClassInfo clazzInfo, 
                        CharBuffer buffer, CompilationUnitTree compileTree) {
        for (Method method : clazzInfo.getMethods()) {
            LocationInfo methodNameLoc = (LocationInfo) 
                                                      method.getLocationInfo();
            int startIndex = methodNameLoc.getStartOffset();
            int endIndex = -1;
            if (startIndex >= 0) {
                String strToSearch = buffer.subSequence(startIndex, 
                                                   buffer.length()).toString();
                Pattern p = Pattern.compile(method.getName());
                Matcher matcher = p.matcher(strToSearch);
                matcher.find();
                startIndex = matcher.start() + startIndex;
                endIndex = startIndex + method.getName().length();
            }
            methodNameLoc.setStartOffset(startIndex);
            methodNameLoc.setEndOffset(endIndex);
            methodNameLoc.setLineNumber(compileTree.getLineMap().
                                                    getLineNumber(startIndex));
        }
    }    
    
      private static void setLocInfoOfVariables(ClassInfo clazzInfo, CharBuffer buffer, CompilationUnitTree compileTree) {
        for (Field field : clazzInfo.getFields()) {
            LocationInfo methodNameLoc = (LocationInfo) field.getLocationInfo();
            int startIndex = methodNameLoc.getStartOffset();
            int endIndex = -1;
            if (startIndex >= 0) {
                String strToSearch = buffer.subSequence(startIndex, buffer.length()).toString();
                Pattern p = Pattern.compile(field.getName());
                Matcher matcher = p.matcher(strToSearch);
                matcher.find();
                startIndex = matcher.start() + startIndex;
                endIndex = startIndex + field.getName().length();
            }
            methodNameLoc.setStartOffset(startIndex);
            methodNameLoc.setEndOffset(endIndex);
            methodNameLoc.setLineNumber(compileTree.getLineMap().getLineNumber(startIndex));
        }
    }
}
