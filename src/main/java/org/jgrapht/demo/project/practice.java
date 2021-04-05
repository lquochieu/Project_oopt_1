package org.jgrapht.demo.project;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.demo.JGraphXAdapterDemo;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.builder.GraphBuilder;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.util.SupplierUtil;

import com.mxgraph.layout.*;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

public class practice extends JApplet{
	
	private static int SIZE = 10;
	private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private static JGraphXAdapter<String, DefaultEdge> jgxAdapter;
	
	  private static Graph<Integer, DefaultEdge> buildEmptySimpleGraph()
	    {
	        return GraphTypeBuilder
	            .<Integer, DefaultEdge> undirected().allowingMultipleEdges(false)
	            .allowingSelfLoops(false).edgeClass(DefaultEdge.class).weighted(false).buildGraph();
	    }
	  
	public static void main(String[] args) throws URISyntaxException {
		Graph<String, DefaultEdge> graph = GraphTypeBuilder
				.directed()
				.allowingMultipleEdges(true)
				.allowingSelfLoops(true)
				.vertexSupplier(SupplierUtil.createStringSupplier())
				.edgeSupplier(SupplierUtil.createDefaultEdgeSupplier())
				.buildGraph();

		String v0 = graph.addVertex();
		String v1 = graph.addVertex();
		String v2 = graph.addVertex();

		graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);

		for (String v : graph.vertexSet()) {
			System.out.println("vertex: " + v);
		}

		for (DefaultEdge e : graph.edgeSet()) {
			System.out.println("edge: " + e);
		}
		
		
		Graph<URI, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        URI google = new URI("http://www.google.com");
        URI wikipedia = new URI("http://www.wikipedia.org");
        URI jgrapht = new URI("http://www.jgrapht.org");

        // add the vertices
        g.addVertex(google);
        g.addVertex(wikipedia);
        g.addVertex(jgrapht);

        // add edges to create linking structure
        g.addEdge(jgrapht, wikipedia);
        g.addEdge(google, jgrapht);
        g.addEdge(google, wikipedia);
        g.addEdge(wikipedia, google);
        
        for (URI v : g.vertexSet()) {
			System.out.println("vertex: " + v);
		}

		for (DefaultEdge e : g.edgeSet()) {
			System.out.println("edge: " + e);
		}
		
		
		 // Create the VertexFactory so the generator can create vertices
        Supplier<String> vSupplier = new Supplier<String>()
        {
            private int id = 0;

            @Override
            public String get()
            {
                return "v" + id++;
            }
        };

        // Create the graph object
        Graph<String, DefaultEdge> completeGraph =
            new SimpleGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

        // Create the CompleteGraphGenerator object
        CompleteGraphGenerator<String, DefaultEdge> completeGenerator =
            new CompleteGraphGenerator<>(SIZE);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph);

        // Print out the graph to be sure it's really complete
        Iterator<String> iter = new BreadthFirstIterator(completeGraph);
        while (iter.hasNext()) {
            String vertex = iter.next();
            System.out
                .println(
                    "Vertex " + vertex + " is connected to: "
                        + completeGraph.edgesOf(vertex).toString());
        }
        
        
        Graph<Integer, DefaultEdge> kite = new GraphBuilder<>(buildEmptySimpleGraph())
                .addEdgeChain(1, 2, 3, 4, 1).addEdge(2, 4).addEdge(3, 5).buildAsUnmodifiable();
        
        for(Integer v : kite.vertexSet()) {

        	System.out.println("Vertex: " + v);
        }
        
        for(DefaultEdge e : kite.edgeSet()) {
        	System.out.println("Edge: " + e);
        }
        Graph<String, DefaultEdge> directedGraph =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
            directedGraph.addVertex("a");
            directedGraph.addVertex("b");
            directedGraph.addVertex("c");
            directedGraph.addVertex("d");
            directedGraph.addVertex("e");
            directedGraph.addVertex("f");
            directedGraph.addVertex("g");
            directedGraph.addVertex("h");
            directedGraph.addVertex("i");
            directedGraph.addEdge("a", "b");
            directedGraph.addEdge("b", "d");
            directedGraph.addEdge("d", "c");
            directedGraph.addEdge("c", "a");
            directedGraph.addEdge("e", "d");
            directedGraph.addEdge("e", "f");
            directedGraph.addEdge("f", "g");
            directedGraph.addEdge("g", "e");
            directedGraph.addEdge("h", "e");
            directedGraph.addEdge("i", "h");
            directedGraph.addEdge("a", "h");
            directedGraph.addEdge("b", "i");
            
         // computes all the strongly connected components of the directed graph
            StrongConnectivityAlgorithm<String, DefaultEdge> scAlg =
                new KosarajuStrongConnectivityInspector<>(directedGraph);
            List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphs =
                scAlg.getStronglyConnectedComponents();

            // prints the strongly connected components
            System.out.println("Strongly connected components:");
            for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
                System.out.println(stronglyConnectedSubgraphs.get(i));
            }
            System.out.println();

            // Prints the shortest path from vertex i to vertex c. This certainly
            // exists for our particular directed graph.
            System.out.println("Shortest path from i to c:");
            DijkstraShortestPath<String, DefaultEdge> dijkstraAlg =
                new DijkstraShortestPath<>(directedGraph);
            SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths("i");
            System.out.println(iPaths.getPath("c") + "\n");

            // Prints the shortest path from vertex c to vertex i. This path does
            // NOT exist for our particular directed graph. Hence the path is
            // empty and the result must be null.
            System.out.println("Shortest path from c to i:");
            SingleSourcePaths<String, DefaultEdge> cPaths = dijkstraAlg.getPaths("c");
            System.out.println(cPaths.getPath("i"));
            // @example:main:end
            
            
            practice applet = new practice();
            applet.init();

            JFrame frame = new JFrame();
            frame.getContentPane().add(applet);
            frame.setTitle("JGraphT Adapter to JGraphX Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
	}
	
	public void init()
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g =
            new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.getViewport().setBackground(Color.yellow);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);
        

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add some sample data (graph manipulated via JGraphX)
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v1);
        g.addEdge(v4, v3);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
    }
}
