package com.brosinski.eclipse.regex.view;

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
     * @see com.brosinski.eclipse.regex.view.IExpressionLoader#addExpressionLoaderListener(com.brosinski.eclipse.regex.view.IExpressionLoaderListener)
     */
    public void addExpressionLoaderListener(IExpressionLoaderListener loaderListener) {
        listener.add(loaderListener);        
    }

    /* (non-Javadoc)
     * @see com.brosinski.eclipse.regex.view.IExpressionLoader#removeExpressionLoaderListener(com.brosinski.eclipse.regex.view.IExpressionLoaderListener)
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
