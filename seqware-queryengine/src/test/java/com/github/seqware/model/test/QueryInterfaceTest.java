package com.github.seqware.model.test;

import com.github.seqware.impl.test.SimplePersistentBackEndTest;
import com.github.seqware.queryengine.factory.Factory;
import com.github.seqware.queryengine.factory.ModelManager;
import com.github.seqware.queryengine.kernel.RPNStack;
import com.github.seqware.queryengine.kernel.RPNStack.Constant;
import com.github.seqware.queryengine.kernel.RPNStack.Operation;
import com.github.seqware.queryengine.model.Feature;
import com.github.seqware.queryengine.model.FeatureSet;
import com.github.seqware.queryengine.model.QueryFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests of {@link com.github.seqware.queryengine.model.QueryInterface}
 *
 * @author dyuen
 */
public class QueryInterfaceTest {

    private static FeatureSet aSet, bSet;
    private static Feature a1, a2, a3, a4;

    @BeforeClass
    public static void setupTests() {
        ModelManager mManager = Factory.getModelManager();
        aSet = mManager.buildFeatureSet().setReference(mManager.buildReference().setName("Dummy_ref").build()).build();
        // create and store some features
        a1 = mManager.buildFeature().setId("chr16").setStart(1000000).setStop(1000100).setStrand(Feature.Strand.NEGATIVE).setType("type1").setScore(100.0).setSource("Program A").setPragma("pragma").setPhase(".").build();
        a2 = mManager.buildFeature().setId("chr16").setStart(1000001).setStop(1000101).setStrand(Feature.Strand.POSITIVE).setType("type2").setScore(80.0).setSource("Program A").setPragma("pragma").setPhase(".").build();
        a3 = mManager.buildFeature().setId("chr16").setStart(1000002).setStop(1000102).setStrand(Feature.Strand.NOT_STRANDED).setType("type2").setScore(80.0).setSource("Program B").setPragma("pragma").setPhase(".").build();
        a4 = mManager.buildFeature().setId("chr16").setStart(1000003).setStop(1000103).setStrand(Feature.Strand.UNKNOWN).setType("type3").setScore(50.0).setSource("Program B").setPragma("pragma").setPhase(".").build();
        aSet.add(a1);
        aSet.add(a2);
        aSet.add(a3);
        aSet.add(a4);
        bSet = FeatureStoreInterfaceTest.diverseBSet(mManager);
        //TODO: this test was somewhat invalid, no flush ... causes error ... we may want a new test case with a nice clean error message
        mManager.flush();
    }

    @Test
    public void testFeatureCreationAndIterate() {
        // get a FeatureSet from the back-end
        QueryFuture future = Factory.getQueryInterface().getFeatures(0, aSet);
        // check that Features are present match
        FeatureSet result = future.get();
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        for (Feature f : result) {
            if (f.equals(a1)) {
                b1 = true;
            } else if (f.equals(a2)) {
                b2 = true;
            } else if (f.equals(a3)) {
                b3 = true;
            }
        }
        Assert.assertTrue(b1 && b2 && b3);
    }

    @Test
    public void testTypeQuery() {
        // get a FeatureSet from the back-end
        QueryFuture future = Factory.getQueryInterface().getFeaturesByAttributes(0, aSet, new RPNStack(
                new Constant("type1"), "type", Operation.EQUAL));
        // check that Features are present match
        FeatureSet result = future.get();
        for (Feature f : result) {
            Assert.assertTrue(f.getType().equals("type1"));
        }
        Assert.assertTrue(result.getCount() == 1);
    }

    @Test
    public void complexQueryTest() {
        // this version of complexQueryTest is model-agnostic and will run on all back-ends
        ModelManager mManager = Factory.getModelManager();
        try {
            mManager.persist(bSet);
        } catch (Exception e) {
            Logger.getLogger(SimplePersistentBackEndTest.class.getName()).log(Level.SEVERE, "Exception", e);
            junit.framework.Assert.assertTrue("Backend could not store the given FeatureSet.", false);
        }

        QueryFuture queryFuture = Factory.getQueryInterface().getFeaturesByAttributes(1, bSet, new RPNStack(
                new Constant("chr16"),
                "id",
                Operation.EQUAL));
        FeatureSet resultSet = queryFuture.get();
        junit.framework.Assert.assertTrue("Setting a query constraints with 1 operation on 'id' failed.", resultSet.getCount() == 10);

        queryFuture = Factory.getQueryInterface().getFeaturesByAttributes(1, bSet, new RPNStack(
                new Constant(Feature.Strand.NEGATIVE),
                "strand",
                Operation.EQUAL));
        resultSet = queryFuture.get();
        junit.framework.Assert.assertTrue("Setting a query constraints with 1 operation on 'strand' failed.", resultSet.getCount() == 3);

        queryFuture = Factory.getQueryInterface().getFeaturesByAttributes(1, bSet, new RPNStack(
                new Constant(Feature.Strand.NEGATIVE),
                "strand",
                Operation.EQUAL,
                new Constant("chr16"),
                "id",
                Operation.EQUAL,
                Operation.AND));
        resultSet = queryFuture.get();
        junit.framework.Assert.assertTrue("Setting a query constraints with 3 operations failed.", resultSet.getCount() == 2);
    }
}
