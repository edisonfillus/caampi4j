package org.caampi4j.ast.common.rule;

import java.util.Collection;

import org.caampi4j.ast.common.model.ClassFile;

/**
 * Interface to execute rule on a subject
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 * @param E
 *            the TypeElement being visited
 */
public interface JavaCodeRule<E> {
	/**
	 * Executes the rule on the subject
	 * 
	 * @param subject
	 *            the element where the rule will be applied.
	 * @param ctx
	 *            the contexual information of the class being verified
	 * @return Rule violations detected after applying the rule
	 */
	public Collection execute(ClassFile clazzInfo);
}
