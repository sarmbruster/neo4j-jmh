package org.neo4j.jmh;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.neo4j.procedure.Mode.*;

public class Procedures {

    @Context
    public GraphDatabaseService db;

    @Procedure(mode = WRITE)
    public Stream<NodeResult> createNodes(@Name("label") String labelString, @Name("count") long count) {
        Label label = Label.label(labelString);
        return LongStream.range(0, count).mapToObj(i -> new NodeResult(db.createNode(label)));
    }

    public class NodeResult {
        public Node node;
        public NodeResult(Node node) {
            this.node = node;
        }
    }
}
