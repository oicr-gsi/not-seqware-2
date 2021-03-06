/*
 * Copyright (C) 2012 SeqWare
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.seqware.common.hibernate;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * WorkflowReportTest class.
 * </p>
 * 
 * @author mtaschuk
 * @version $Id: $Id
 * @since 0.13.3
 */
public class WorkflowReportTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowReportTest.class);

    /**
     * <p>
     * Constructor for WorkflowReportTest.
     * </p>
     */
    public WorkflowReportTest() {
    }

    /**
     * <p>
     * setUpClass.
     * </p>
     * 
     * @throws java.lang.Exception
     *             if any.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * <p>
     * tearDownClass.
     * </p>
     * 
     * @throws java.lang.Exception
     *             if any.
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * <p>
     * setUp.
     * </p>
     */
    @Before
    public void setUp() {
    }

    /**
     * <p>
     * tearDown.
     * </p>
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of fromWorkflow method, of class WorkflowReport.
     */
    @Test
    public void testFromWorkflow() {
        LOGGER.info("WorkflowReportTest:fromWorkflow");
        Integer workflowSWA = null;
        WorkflowReport instance = new WorkflowReport();
        instance.fromWorkflow(workflowSWA);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
