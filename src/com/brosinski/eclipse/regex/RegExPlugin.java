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
package com.brosinski.eclipse.regex;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


public class RegExPlugin extends AbstractUIPlugin {

	private static RegExPlugin plugin;
	
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	/**
	 * The constructor.
	 */
	public RegExPlugin() {
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("com.brosinski.eclipse.regex.PluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		resourceBundle = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static RegExPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = RegExPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	

	
	protected void initializeDefaultPluginPreferences() {
		getPreferenceStore().setDefault("Pattern.CANON_EQ",false);
		getPreferenceStore().setDefault("Pattern.CASE_INSENSITIVE",false);
		getPreferenceStore().setDefault("Pattern.COMMENTS",false);
		getPreferenceStore().setDefault("Pattern.DOTALL",false);
		getPreferenceStore().setDefault("Pattern.MULTILINE",false);
		getPreferenceStore().setDefault("Pattern.UNICODE_CASE",false);
		getPreferenceStore().setDefault("Pattern.UNIX_LINES",false);
		getPreferenceStore().setDefault("EvalRegEx",false);
		getPreferenceStore().setDefault("EvalSearch",false);
		getPreferenceStore().setDefault("EvalBoth",true);
		getPreferenceStore().setDefault("EvalSwitch",false);
		
		FontData systemFont = Display.getCurrent().getSystemFont().getFontData()[0];
		
		getPreferenceStore().setDefault("font.regex.name", systemFont.getName());
		getPreferenceStore().setDefault("font.regex.height", systemFont.getHeight());
		getPreferenceStore().setDefault("font.regex.style", systemFont.getStyle());
		getPreferenceStore().setDefault("font.searchtext.name", systemFont.getName());
		getPreferenceStore().setDefault("font.searchtext.height", systemFont.getHeight());
		getPreferenceStore().setDefault("font.searchtext.style", systemFont.getStyle());
		getPreferenceStore().setDefault("font.result.name", systemFont.getName());
		getPreferenceStore().setDefault("font.result.height", systemFont.getHeight());
		getPreferenceStore().setDefault("font.result.style", systemFont.getStyle());
	}

	
	public ImageDescriptor getImageDescriptor(String imageName) {
		return imageDescriptorFromPlugin("com.brosinski.eclipse.regex", "icons/" + imageName);
	}


}
