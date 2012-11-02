package com.brosinski.eclipse.regex.view;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Text;

/**
 * Saves the last text value from a widget when focus is lost to {@link IPreferenceStore}.
 * PreferenceStore will be automatically saved by
 * {@link org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)}.
 * <p>
 * The current version support only {@link StyledText}, but it can be easily extended, if
 * needed.
 * @author Andreas Groll
 */
public class SaveLastValueToPreferenceStoreFocusAdapter extends FocusAdapter {

	private final IPreferenceStore preferenceStore;
	private final String preferenceKey;

	/**
	 * Constructor.
	 * @param preferenceStore PreferenceStore to store value.
	 * @param preferenceKey Name of the key to store value.
	 */
	public SaveLastValueToPreferenceStoreFocusAdapter(final IPreferenceStore preferenceStore,
		final String preferenceKey) {

		this.preferenceStore = preferenceStore;
		this.preferenceKey = preferenceKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void focusLost(final FocusEvent e) {

		// Init
		String value = null;

		// Current version supports only StyledText and Text, but can be extended, if needed
		if (e.widget instanceof StyledText) {
			StyledText styledText = (StyledText)e.widget;
			value = styledText.getText();
		} else if (e.widget instanceof Text) {
			Text text = (Text)e.widget;
			value = text.getText();
		} else {
			// Unusupported widget type
			return;
		}

		// Put to preference store
		preferenceStore.setValue(preferenceKey, value == null ? "" : value);
	}
}
