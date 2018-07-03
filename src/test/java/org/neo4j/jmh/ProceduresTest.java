package org.neo4j.jmh;


import org.junit.Rule;
import org.junit.Test;
import org.neo4j.graphdb.Result;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.helpers.collection.Iterators;

import static org.junit.Assert.assertEquals;

public class ProceduresTest {

    @Rule
    public Neo4jRule db = new Neo4jRule().withProcedure(Procedures.class);

    @Test
    public void shouldCreateNodesProcedureWork() {
        assertEquals(0, countNodes());
        Result result = db.getGraphDatabaseService().execute("call org.neo4j.jmh.createNodes('Dummy', 10) yield node return node");
        assertEquals(10, Iterators.count(result));
        assertEquals( 10, countNodes());
    }

    private long countNodes() {
        Result result = db.getGraphDatabaseService().execute("MATCH (n) RETURN COUNT(*) AS count");
        return Iterators.single(result.columnAs("count"));
    }
}
