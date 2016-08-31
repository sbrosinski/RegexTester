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
package com.brosinski.eclipse.regex.view;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyledText;

import com.brosinski.eclipse.regex.RegExPlugin;

public class LiveEval {
	
	private LiveEvalListenerManager liveEvalListenerManager;

    class LiveEvalListener implements ExtendedModifyListener {

        public void modifyText(ExtendedModifyEvent event) {
            if (LiveEval.this.isLiveEval()) LiveEval.this.doEval();
        }
    }

   
    private boolean isLiveEval;

    private LiveEvalListener liveEvalListener;

    private StyledText regEx, searchText;

    private IPreferenceStore prefs = RegExPlugin.getDefault()
            .getPreferenceStore();

    
    public LiveEval(StyledText regEx, StyledText searchText) {
        liveEvalListener = new LiveEvalListener();
        this.regEx = regEx;
        this.searchText = searchText;
        isLiveEval = prefs.getBoolean("EvalSwitch");
    
        prefs.addPropertyChangeListener(new IPropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent event) {
                if (LiveEval.this.regEx.isDisposed()
                        || LiveEval.this.searchText.isDisposed()) { return; }
                String prop = event.getProperty();

                if (prop.equals("EvalSwitch")) {
                    boolean newVal = ((Boolean) event.getNewValue())
                            .booleanValue();
                    if (newVal)
                        LiveEval.this.start();
                    else
                        LiveEval.this.stop();
                }
                if (LiveEval.this.isLiveEval()) updateChangeListeners();
            }
        });
        liveEvalListenerManager = new LiveEvalListenerManager();
    }

    public void addLiveEvalListener(ILiveEvalListener listener) {
    	liveEvalListenerManager.addListener(listener);
    }

    public void removeLiveEvalListener(ILiveEvalListener listener) {
    	liveEvalListenerManager.removeListener(listener);
    }
    
    public boolean isLiveEval() {
        return this.isLiveEval;
    }

    public void start() {
        this.isLiveEval = true;
        updateChangeListeners();
        liveEvalListenerManager.publishEvalActivated();
    }

    public void stop() {
        this.isLiveEval = false;
        removeChangeListeners();
        liveEvalListenerManager.publishEvalDeactivated();
    }
    

  
    private void doEval() {
    	liveEvalListenerManager.publishDoEval();
    	liveEvalListenerManager.publishEvalDone();
    }

    private void removeChangeListeners() {
        regEx.removeExtendedModifyListener(liveEvalListener);
        searchText.removeExtendedModifyListener(liveEvalListener);

    }
    
    private void updateChangeListeners() {
        removeChangeListeners();
        if (prefs.getBoolean("EvalSearch")) {
            searchText.addExtendedModifyListener(liveEvalListener);
        }
        if (prefs.getBoolean("EvalRegEx")) {
            regEx.addExtendedModifyListener(liveEvalListener);
        }
        if (prefs.getBoolean("EvalBoth")) {
            searchText.addExtendedModifyListener(liveEvalListener);
            regEx.addExtendedModifyListener(liveEvalListener);
        }
    }

    
    
}