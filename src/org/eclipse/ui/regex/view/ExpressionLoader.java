/*******************************************************************************
 * Copyright (c) 2012 Stephan Brosinski
 *  
 * All rights reserved. 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Stephan Brosinski - initial API and implementation
 ******************************************************************************/
package org.eclipse.ui.regex.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExpressionLoader {

    private List listener = new ArrayList();
    
    private static ExpressionLoader instance;
    
    public static ExpressionLoader getInstance() {
        if (instance == null) {
            instance = new ExpressionLoader();
        }
        return instance;
    }
    
    private ExpressionLoader() {}
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.regex.view.IExpressionLoader#addExpressionLoaderListener(org.eclipse.ui.regex.view.IExpressionLoaderListener)
     */
    public void addExpressionLoaderListener(IExpressionLoaderListener loaderListener) {
        listener.add(loaderListener);        
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.regex.view.IExpressionLoader#removeExpressionLoaderListener(org.eclipse.ui.regex.view.IExpressionLoaderListener)
     */
    public void removeExpressionLoaderListener(IExpressionLoaderListener loaderListener) {
        listener.remove(loaderListener);    
    }

    
    public void fireLoadExpression(Expression expression) {
        for (Iterator i = listener.iterator(); i.hasNext();) {
            IExpressionLoaderListener l = (IExpressionLoaderListener) i.next();
            l.loadExpression(expression);
        }
    }
    
    
}
