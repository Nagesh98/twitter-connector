/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation.testcases;

import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.modules.twitter.automation.TwitterTestParent;
import twitter4j.Place;

import static org.junit.Assert.*;

public class CreatePlaceTestCases extends TwitterTestParent {


    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("createPlaceTestData");
    }

    @Test
    public void testCreatePlace() {
        try {
            Place place = runFlowAndGetPayload("create-place-default-values");
            assertNotNull(place.getId());
            assertEquals(place.getName(), getTestRunMessageValue("placeName"));

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @Test
    public void testCreatePlaceParametrized() {
        try {
            Place place = runFlowAndGetPayload("create-place-parametrized");
            assertNotNull(place.getId());
            assertEquals(place.getName(), getTestRunMessageValue("placeName"));
            assertEquals(place.getStreetAddress(), getTestRunMessageValue("streetAddress"));

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }


}
