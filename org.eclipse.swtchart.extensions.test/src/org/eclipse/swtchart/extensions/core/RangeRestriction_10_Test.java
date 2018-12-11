/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.core;

import org.eclipse.swtchart.extensions.core.RangeRestriction;

import junit.framework.TestCase;

public class RangeRestriction_10_Test extends TestCase {

	private RangeRestriction rangeRestriction;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_X | RangeRestriction.ZERO_Y | RangeRestriction.RESTRICT_ZOOM);
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertTrue(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictZoom());
	}

	public void test2() {

		rangeRestriction.setZeroX(false);
		assertFalse(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictZoom());
	}

	public void test3() {

		rangeRestriction.setZeroY(false);
		assertTrue(rangeRestriction.isZeroX());
		assertFalse(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictZoom());
	}

	public void test4() {

		rangeRestriction.setRestrictZoom(false);
		assertTrue(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertFalse(rangeRestriction.isRestrictZoom());
	}
}
