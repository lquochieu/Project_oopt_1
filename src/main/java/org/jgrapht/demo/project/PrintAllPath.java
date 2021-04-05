package org.jgrapht.demo.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;


public class PrintAllPath 
extends JApplet{public PrintAllPath() throws Exception {
	// TODO Auto-generated constructor stub
}
    private static final String path = "D:\\TTH\\app code\\Visual Studio\\Java\\project.txt";
    private static int[][] e = new int[100][100];
    private static String txt;
    private static String[] atxt;
    private static JFrame frame = new JFrame();
    
    private static ListenableGraph<String, DefaultEdge> g;
    private static LinkedList<String> visited = new LinkedList<String>();
    private static String[] v;
    private static String START;
    private static String END;
    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private static JGraphXAdapter<String, DefaultEdge> jgxAdapter;

    public static void main(String[] args) throws Exception {

        prepare();  
        showFrame();
        PathBetweenTwoVertex();
    }
    ///////////////////////////
    ///////////////////////////
    //read file to take data
    private static void prepare() throws Exception {
    	FileReader fr = null;
        BufferedReader br = null;
        txt = "";
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null) {
            	txt += line;
                line = br.readLine();
            }
            atxt = txt.split("n");
            for(int i = 0; i < atxt.length; ++i) {
                if(atxt[i].length() >= 3) {
                    int a, b;
                    b = Integer.parseInt(String.valueOf(atxt[i].charAt(0)));
                    for(int j = 3; j < atxt[i].length(); ++j) {
                        char str = atxt[i].charAt(j);
                        if(!Character.toString(str).equals(" ")) {
                            a = Integer.parseInt(String.valueOf(str));
                            e[b][a] = 1;
                            e[a][b] = 1;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
	}
   
    ///////////////////////
    //show frame
    private static void showFrame() throws Exception {

    	PrintAllPath applet = new PrintAllPath();
        applet.init();
        
        JPanel buttonJPanel = new JPanel();
        JButton PathButton = new JButton("Bai 1");
        JButton ClearButton = new JButton("Clear");
        JButton AllPathButon = new JButton("Bai 2");
        buttonJPanel.add(PathButton);
        buttonJPanel.add(ClearButton);
        buttonJPanel.setBackground(Color.ORANGE);
     
        JLabel showGraphLabel = new JLabel("We have graph: ");
        
        frame.add(showGraphLabel, BorderLayout.NORTH);
        frame.getContentPane().add(applet, BorderLayout.CENTER);
        frame.getContentPane().add(buttonJPanel, BorderLayout.SOUTH);
        frame.setTitle("Project OOPT");
        frame.setForeground(Color.YELLOW);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        PathButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
        	        frame.getContentPane().add(applet);
        	        frame.add(showGraphLabel, BorderLayout.NORTH);
        	        frame.pack();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        ClearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.getContentPane().removeAll();
				frame.getContentPane().add(buttonJPanel, BorderLayout.SOUTH);
				frame.remove(showGraphLabel);
				frame.setPreferredSize(DEFAULT_SIZE);
				frame.pack();
			}
		});
        
        frame.pack();
        frame.setVisible(true);
    }
    //////////////////////////////////////
    //Show console Homework1
    //////////////////////////////////////
    //deal all path between two vertexs
     private static void PathBetweenTwoVertex() {
     	for(int i = 1; i < atxt.length; ++i) {
     		for(int j = i + 1; j <= atxt.length; ++j) {
     			System.out.println("All path between " + v[i] + " and " + v[j] + " are: ");
     			START = v[i];
     			END = v[j];
     			visited.add(START);
     			depthFirst(visited);
     			visited.removeLast(); 
     			ShortestPath();
     		}
     	}
     }
    //////////////////////////
    //print the shortest path between two vertexs
     private static void ShortestPath() {
    	 DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<String, DefaultEdge>(g);
    	 SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(START);
    	 System.out.println("Shortest path from " + START + " to " + END + " is: ");
    	 System.out.println(iPaths.getPath(END) + "\n");
     }
    //////////////////////////
    //print all path between START vertex and END vertex
    private static void printAllPath(LinkedList<String> visited) {
    	for (String node : visited) {
            System.out.print(node);
            if(!node.equals(visited.getLast()))
            System.out.print(" -> ");
        }
        System.out.println();
    }
    /////////////////
    //find all vertexs connect with 'vt' vertex
    public static LinkedList<String> adjcentsNode(String vt) {
    	LinkedList<String> a = new LinkedList<String>();
    	for(String v : g.vertexSet()) {
    		if(g.containsEdge(v, vt)) {
    			a.add(v);
    		}
    	}
    	return a;
    }
    //////////////////////////
    //find all path between two vertexs
    public static void depthFirst(LinkedList<String> visited) {
    	LinkedList<String> nodes = adjcentsNode(visited.getLast());
    	for(String node : nodes) {
    		if(visited.contains(node)) continue;
    		if(node.equals(END)) {
    			visited.add(node);
    			printAllPath(visited);
    			visited.removeLast();
    		}
    	}
    	
    	for(String node : nodes) {
    		if(visited.contains(node) || node.equals(END)) {
    			continue;
    		}
				visited.add(node);
				depthFirst(visited);
				visited.removeLast();
    	}
    }
    
    @SuppressWarnings("deprecation")
	public void init()
    {
        // create a JGraphT graph
        g = new DefaultListenableGraph<>(GraphTypeBuilder
				.undirected()
				.allowingMultipleEdges(false)
				.allowingSelfLoops(false)
				.vertexSupplier(SupplierUtil.createStringSupplier())
				.edgeSupplier(SupplierUtil.createDefaultEdgeSupplier())
				.buildGraph());
        
        
        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.getViewport().setBackground(Color.YELLOW);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);
        
        v = new String[100];
        for(int i = 1; i <= atxt.length; ++i) {
        	v[i] = "v" + (i);
        	g.addVertex(v[i]);
        }
        
        // add some sample data (graph manipulated via JGraphX)
        for(int i = 1; i <= atxt.length; ++i) {
        	for(int j = 1; j <= atxt.length; ++j) {
        		if(e[i][j] == 1) {
        			g.addEdge(v[i], v[j]);
        		}
        	}
        }
        
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
