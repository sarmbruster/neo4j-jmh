package org.neo4j.jmh;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Neo4jProcedureBenchmark {

    private GraphDatabaseService db;
    private File neo4jDirectory;
    private org.neo4j.jmh.Procedures procedures;

    @Setup
    public void startNeo4j() {
        try {
            neo4jDirectory = Files.createTempDirectory("neo4j").toFile();
            db = new GraphDatabaseFactory().newEmbeddedDatabase(neo4jDirectory);
//            Procedures procedures = ((GraphDatabaseAPI) db).getDependencyResolver().resolveDependency(Procedures.class);
//            procedures.registerProcedure(TrivialProcedures.class);
//            procedures.registerFunction(TrivialProcedures.class);

            populateGraph();

            procedures = new org.neo4j.jmh.Procedures();
            procedures.db = db;  // set context variable
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateGraph() {
        // intentionally empty
    }

    @TearDown
    public void stopNeo4j() {
        db.shutdown();
        try {
            FileUtils.deleteRecursively(neo4jDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations=2, time=1)
    @Fork(3)
    public void testMethod() {
        procedures.createNodes("Dummy", 10);
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
    }

}
