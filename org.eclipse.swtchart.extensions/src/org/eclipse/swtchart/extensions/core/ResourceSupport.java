/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.core;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.extensions.preferences.PreferenceInitializer;

public class ResourceSupport {

	public static final String ICON_SET_RANGE = "set_range.gif"; // $NON-NLS-1$7
	public static final String ICON_HIDE = "hide.gif"; // $NON-NLS-1$
	public static final String ICON_RESET = "reset.gif"; // $NON-NLS-1$
	public static final String ICON_CHECKED = "checked.gif"; // $NON-NLS-1$
	public static final String ICON_UNCHECKED = "unchecked.gif"; // $NON-NLS-1$
	public static final String ICON_LEGEND = "legend.gif"; // $NON-NLS-1$
	public static final String ICON_POSITION = "position.gif"; // $NON-NLS-1$
	public static final String ICON_SETTINGS = "preferences.gif"; // $NON-NLS-1$
	public static final String ICON_MAPPINGS = "mappings.gif"; // $NON-NLS-1$
	public static final String ICON_DELETE = "delete.gif"; // $NON-NLS-1$
	public static final String ICON_DELETE_ALL = "deleteAll.gif"; // $NON-NLS-1$
	public static final String ARROW_LEFT = "arrowLeft.gif"; // $NON-NLS-1$
	public static final String ARROW_RIGHT = "arrowRight.gif"; // $NON-NLS-1$
	public static final String ARROW_UP = "arrowUp.gif"; // $NON-NLS-1$
	public static final String ARROW_DOWN = "arrowDown.gif"; // $NON-NLS-1$
	public static final String ICON_SERIES_MARKER = "seriesMarker.gif"; // $NON-NLS-1$
	//
	private static final Map<RGB, Color> colorMap = new HashMap<>();
	private static final ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
	private static IPreferenceStore preferenceStore = null;
	private static ImageRegistry imageRegistry = null;

	private ResourceSupport() {

	}

	/**
	 * The color is mapped and disposed by this color support.
	 * Hence, it doesn't need to be disposed manually.
	 * 
	 * @param rgb
	 * @return color
	 */
	public static Color getColor(RGB rgb) {

		Color color = colorMap.get(rgb);
		if(color == null) {
			color = new Color(Display.getDefault(), rgb);
			colorMap.put(rgb, color);
		}
		return color;
	}

	/**
	 * The color is mapped and disposed by this color support.
	 * Hence, it doesn't need to be disposed manually.
	 * 
	 * @param rgb
	 * @return color
	 */
	public static Color getColor(int red, int green, int blue) {

		RGB rgb = new RGB(red, green, blue);
		return getColor(rgb);
	}

	/**
	 * Returns the current preference store.
	 * 
	 * @return {@link IPreferenceStore}
	 */
	public static IPreferenceStore getPreferenceStore() {

		// return null;
		if(preferenceStore == null) {
			String filename = System.getProperty("user.home") + File.separator + ".eclipseswtchartsettings";
			preferenceStore = new PreferenceStore(filename);
			PreferenceInitializer preferenceInitializer = new PreferenceInitializer();
			preferenceInitializer.initializeDefaultPreferences();
		}
		return preferenceStore;
	}

	/**
	 * Returns the given image. There is no need to
	 * dispose this image. It's handled by the
	 * resource support.
	 * 
	 * @param key
	 * @return {@link Image}
	 */
	public static Image getImage(String key) {

		if(imageRegistry == null) {
			imageRegistry = initializeImageRegistry();
		}
		return resourceManager.createImage(imageRegistry.getDescriptor(key));
	}

	@Override
	protected void finalize() throws Throwable {

		for(Color color : colorMap.values()) {
			if(color != null) {
				color.dispose();
			}
		}
		super.finalize();
	}

	private static ImageRegistry initializeImageRegistry() {

		if(Display.getCurrent() == null) {
			throw new SWTException(SWT.ERROR_THREAD_INVALID_ACCESS);
		} else {
			imageRegistry = new ImageRegistry();
		}
		//
		Set<String> imageSet = new HashSet<String>();
		imageSet.add(ICON_SET_RANGE);
		imageSet.add(ICON_HIDE);
		imageSet.add(ICON_RESET);
		imageSet.add(ICON_CHECKED);
		imageSet.add(ICON_UNCHECKED);
		imageSet.add(ICON_LEGEND);
		imageSet.add(ICON_POSITION);
		imageSet.add(ICON_SETTINGS);
		imageSet.add(ICON_MAPPINGS);
		imageSet.add(ICON_DELETE);
		imageSet.add(ICON_DELETE_ALL);
		imageSet.add(ARROW_LEFT);
		imageSet.add(ARROW_RIGHT);
		imageSet.add(ARROW_UP);
		imageSet.add(ARROW_DOWN);
		imageSet.add(ICON_SERIES_MARKER);
		//
		for(String image : imageSet) {
			imageRegistry.put(image, createImageDescriptor(image));
		}
		//
		return imageRegistry;
	}

	/**
	 * Helps to create an image descriptor.
	 * 
	 * @param fileName
	 * @return ImageDescriptor
	 */
	private static ImageDescriptor createImageDescriptor(String fileName) {

		URL url = ResourceSupport.class.getResource("/resources/icons/16x16/" + fileName);
		return ImageDescriptor.createFromURL(url);
	}
}
