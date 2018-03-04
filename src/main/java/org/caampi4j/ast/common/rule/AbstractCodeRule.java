package org.caampi4j.ast.common.rule;

import java.util.Collection;

import org.caampi4j.ast.common.model.ClassFile;
import org.caampi4j.ast.common.model.ErrorDescription;
import org.caampi4j.ast.common.model.Location;


/**
 * Executes rules on a subject
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 * @param E
 *            the TypeElement being visited
 */
public abstract class AbstractCodeRule<E> implements JavaCodeRule<E> {

	/**
	 * Abstract method to apply a rule
	 * 
	 * @param subject
	 *            the element where the rule will be applied.
	 * @param ctx
	 *            the contexual information of the class being verified
	 * @return Rule violations detected after applying the rule
	 */
	protected abstract Collection apply(ClassFile clazzInfo);

	/**
	 * Executes the rule on the subject
	 * 
	 * @param subject
	 *            the element where the rule will be applied.
	 * @param ctx
	 *            the contexual information of the class being verified
	 * @return Rule violations detected after applying the rule
	 */
	public final Collection execute(ClassFile clazzInfo) {
		return apply(clazzInfo);
	}
    
    public ErrorDescription setErrorDetails(String errorMessage, 
                                            Location errorLoc) {
        ErrorDescription errorDesc = new ErrorDescription();
        errorDesc.setErrorMessages(errorMessage);
        errorDesc.setErrorLocation(errorLoc);
        return errorDesc;
    }
}
