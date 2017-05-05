/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p5
// FILE:             NavigationGraph.java
//
// TEAM:    Team 191
// Authors:
// Author1: (Roberto O'Dogherty, rodogherty@wisc.edu, o-dogherty, 001)
// Author2: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
// Author3: (Aleysha Becker, ambecker5@wisc.edu, aleysha, 001)
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * This class is called on by the MapApp.java class; it contains all the functions
 * necessary for the program to run.
 * <p>Bugs: no known bugs
 *
 * @author Roberto O, Vanessa C, Aleysha B
 */

public class NavigationGraph implements GraphADT<Location, Path> {
	//holds all content from file
	private ArrayList<GraphNode<Location, Path>> graphNodes;
	//holds the immutable variables in the file
	private String[] propertyNames;

	/*
	 * Constructs new Navigation Graph to represent locations and paths
	 * @param String array of edge names and their properties
	 *
	 */
	public NavigationGraph(String[] edgePropertyNames) {

		graphNodes = new ArrayList<GraphNode<Location,Path>>();

		//These are all temporary variable holders before calling the
		//Constructors for other classes

		String[] tempInfo;
		Location tempSource, tempDest;
		List <Double> tempProperties = new ArrayList<Double>();
		int numberOfProperties = (edgePropertyNames[0].split(" ").length) -2;
		propertyNames = new String[numberOfProperties];

		for(int l = 0; l < numberOfProperties; l++){

			propertyNames[l] = edgePropertyNames[0].split(" ")[2+l];
			//System.out.print(Arrays.toString(propertyNames));

		}


		//every iteration corresponds to one new path.
		for(int i = 1; i < edgePropertyNames.length; i++){

			//split info
			tempInfo = edgePropertyNames[i].split(" ");

			//set up temporary Locations for the path
			tempSource = new Location(tempInfo[0]);
			tempDest = new Location(tempInfo[1]);

			tempProperties = new ArrayList<Double>();

			//set up temporary properties
			//every iteration corresponds to a property
			for(int l = 0; l < numberOfProperties; l++){

				try{
					tempProperties.add( Double.parseDouble( tempInfo[2+l]) );
				}catch(Exception e){
					throw new IllegalArgumentException();
				}

			}


			//adds graphNodes to graphnodeList
			GraphNode<Location, Path> source = addVertexHelper(new GraphNode<Location, Path>(tempSource, 0));
			GraphNode<Location, Path> dest = addVertexHelper(new GraphNode<Location, Path>(tempDest, 0));

			//add paths to the graph node
			source.addOutEdge(new Path(source.getVertexData(), dest.getVertexData(), tempProperties));



		}



	}


	/**
	 * Returns a Location object given its name
	 *
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {

		if(name == null){
			throw new IllegalArgumentException();
		}

		Location toReturn = null;

		for(int x = 0; x < graphNodes.size(); x++){

			//Compare the locations inside each vertex
			if(graphNodes.get(x).getVertexData().getName().equals(name) ) {

				toReturn = graphNodes.get(x).getVertexData();

			}
		}

		return toReturn;
	}


	/**
	 * Adds a point to the graph, calls GraphNodeHelper method.
	 * @param vertex - the location to be added
	 * @throws IllegalArgumentException - if vertex is not valid
	 */
	public void addVertex(Location vertex) {

		if(vertex == null){
			throw new IllegalArgumentException();
		}

		//add Vertex from created location
		addVertexHelper(new GraphNode<Location, Path>(vertex, 0));


	}

	/**
	 * Adds graphNode if its a new graph node
	 * will return this graphNode if it is new
	 * or existing one if it already exists
	 * @param toAdd - the GraphNode to be added
	 * @return GraphNode - the GraphNode object to be added into tree
	 *
	 */
	private GraphNode<Location,Path> addVertexHelper(GraphNode<Location,Path> toAdd){

		GraphNode<Location, Path> toReturn = toAdd;

		//iterate through list check if we already have the graph node
		for(int x = 0; x < graphNodes.size(); x++){

			//Compare the locations inside each vertex
			if(graphNodes.get(x).getVertexData().equals(toAdd.getVertexData())){

				toReturn = graphNodes.get(x);
				return toReturn;

			}
		}

		graphNodes.add(toAdd);
		return toReturn;

	}


	/**
	 * This method adds an edge to the graph.
	 *
	 * @param src - the start of edge
	 * @param  dest - the end of the edge
	 * @param edge - the edge to be added between src & dest
	 * @throws IllegalArgumentException - if src, dest, or edge are not valid
	 *
	 */
	public void addEdge(Location src, Location dest, Path edge) {

		//iterate through list check if we already have the graph node
		for(int x = 0; x < graphNodes.size(); x++){

			//Compare the locations inside each vertex
			if(graphNodes.get(x).getVertexData().equals(src)){

				graphNodes.get(x).addOutEdge(edge);
				return;

			}
		}

		GraphNode<Location, Path> toAdd = new GraphNode<Location,Path>(src, 0);
		toAdd.addOutEdge(edge);

		graphNodes.add(toAdd);


	}


	/**
	 * returns a list of the vertices in the graph.
	 * @return toReturn - an ArrayList<Location> of all the vertices in the graph
	 */

	public List<Location> getVertices() {

		ArrayList<Location> toReturn = new ArrayList<Location>();

		//iterate through list
		for(int x = 0; x < graphNodes.size(); x++){

			toReturn.add(graphNodes.get(x).getVertexData());

		}


		return toReturn;
	}


	/**
	 * This method iterates through all edges and checks whether
	 * an edge already exists between two locations
	 * @param src - start location
	 * @param dest - end location
	 * @return null - if no location exists
	 * @return Path - if a path already exists between two locations
	 * @throws IllegalArgumentException - if src or dest are not valid
	 */
	public Path getEdgeIfExists(Location src, Location dest) {

		if(src == null || dest == null){
			throw new IllegalArgumentException();
		}

		//iterate through list check if we already have the graph node
		for(int x = 0; x < graphNodes.size(); x++){

			//Compare the locations inside each vertex
			if(graphNodes.get(x).getVertexData().equals(src)){

				//Iterates through the out edges
				for(int p = 0; p < graphNodes.get(x).getOutEdges().size(); p++){

					if( graphNodes.get(x).getOutEdges().get(p).getDestination().equals(dest) ){

						return graphNodes.get(x).getOutEdges().get(p);
					}

				}

			}
		}

		return null;
	}


	/**
	 * This method returns a list of paths coming out of a location
	 * @param src - the location used to check which edges are attributed to it
	 * @return List<Path> - a list of paths of the outedges coming from location
	 * @return null - if no outedges are coming from location
	 * @throws IllegalArgumentException - if src is not valid
	 */
	public List<Path> getOutEdges(Location src) {

		if(src == null){
			throw new IllegalArgumentException();
		}

		//iterate through list
		for(int x = 0; x < graphNodes.size(); x++){

			//check if it is the location we want
			if(graphNodes.get(x).getVertexData().equals(src)){

				return graphNodes.get(x).getOutEdges();

			}
		}

		return null;


	}


	/**
	 * This method returns a list of locations, which are the adjacent neighbors of
	 * a particular location.
	 * @param vertex - location from which to check for adjacent neighbors
	 * @return list of locations that are the neighbors of the passed location
	 * @throws IllegalArgumentException - if vertex is not a valid location
	 */
	public List<Location> getNeighbors(Location vertex) {



		if(vertex == null){
			throw new IllegalArgumentException();
		}

		ArrayList<Location> toReturn = new ArrayList<Location>();


		//iterate through list check if we already have the graph node
		for(int x = 0; x < graphNodes.size(); x++){

			//Compare the locations inside each vertex
			if(graphNodes.get(x).getVertexData().equals(vertex)){

				//Iterates through the out edges
				//Adding to the list the neighbors that vertex is pointing to
				for(int p = 0; p < graphNodes.get(x).getOutEdges().size(); p++){

					toReturn.add( graphNodes.get(x).getOutEdges().get(p).getDestination() );

				}
			}else{

				//Add to the list the neighbors that point to edge
				//Iterates through the out edges
				for(int p = 0; p < graphNodes.get(x).getOutEdges().size(); p++){

					if( graphNodes.get(x).getOutEdges().get(p).getDestination().equals(vertex) ){

						toReturn.add( graphNodes.get(x).getVertexData());
					}

				}

			}
		}

		return toReturn;
	}


	/**
	 * Compares all possible routes from a start to and end point; it returns a path
	 * of the shortest route from src to dest.
	 * @param src - a start location
	 * @param dest  an end location
	 * @param edgePropertyNames - the types of values to be comparing
	 * @return List<Path> - a list of the shortest path from src to dest
	 */
	public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {

		if(src == null || dest == null || edgePropertyName == null){
			throw new IllegalArgumentException();
		}

		int propertyIndex = 0;

		// find index in edgeProperties array for the property of interest
		for(int i = 0; i < propertyNames.length; i++) {
			if (propertyNames[i].compareTo(edgePropertyName) == 0 ){
				propertyIndex = i;
			}
		}

		//set up Graph Node Wrapper class list
		ArrayList<GraphNodeWrapper> graphNodeWrappers = new ArrayList<GraphNodeWrapper>();
		GraphNodeWrapper start = null;
		GraphNodeWrapper end = null;

		GraphNodeWrapper temp;
		for(int m = 0; m < graphNodes.size(); m++){

			temp = new GraphNodeWrapper(graphNodes.get(m));

			if(temp.getNode().getVertexData().equals(src)){
				start = temp;
			}else if(temp.getNode().getVertexData().equals(dest)){
				end = temp;
			}

			graphNodeWrappers.add(temp);

		}

		if(start == end){
			return new ArrayList<Path>();
		}


		//start of our search
		start.setStarter();
		recursiveAlgorithmToFindPath(start, end, graphNodeWrappers, propertyIndex);


		ArrayList<Path> toReturn = new ArrayList<Path>();

		//set up by starting with the end looking at the previous etc.

			temp = end;
			GraphNode<Location, Path> tempNode;

		while(true){

			if(temp == start){
				break;
			}


			tempNode = temp.getPrevious().getNode();



			for(int x =0; x < tempNode.getOutEdges().size(); x++){

				if(temp.getNode().getVertexData().equals(tempNode.getOutEdges().get(x).getDestination())){

					toReturn.add(tempNode.getOutEdges().get(x));
					break;

				}

			}

			temp = temp.getPrevious();

		}

		Collections.reverse(toReturn);

		return toReturn;

	}

	/**
	 * Recursive algorithm to find the shortest path
	 * @param starter - a start location
	 * @param ender  an end location
	 * @param graphNodeWrappers - a list of wrapers to iterate through
	 * @param propertyIndex - the index to be used
	 */

	private void recursiveAlgorithmToFindPath(GraphNodeWrapper starter, GraphNodeWrapper ender,
			ArrayList<GraphNodeWrapper> graphNodeWrappers, int propertyIndex){

		if (starter == ender){
			return;
		}

		GraphNodeWrapper next;

		starter.changeVisited();

		for(int l = 0; l < starter.getNode().getOutEdges().size(); l++){

			//System.out.println(starter.getNode().getVertexData().getName());

			//setting up the next node
			next = findNode(starter.getNode().getOutEdges().get(l).getDestination(),graphNodeWrappers);

			//System.out.print("...." + next.getNode().getVertexData().getName());

			next.setDistance(starter.getNode().getOutEdges().get(l).getProperties().get(propertyIndex)
					+ starter.getDistance(), starter);

			//System.out.println(" distance is " + next.getDistance());
			//System.out.println("This one is " + starter.getDistance());


			if(!next.getVisited()){
				recursiveAlgorithmToFindPath(next, ender, graphNodeWrappers, propertyIndex);
			}

		}

		starter.changeVisited();
		return;

	}


	/*
	 * Method iterates through our list of Wrapper nodes and finds the source
	 */
	private GraphNodeWrapper findNode(Location findMe, ArrayList<GraphNodeWrapper> graphNodeWrappers){

		for(int m = 0; m < graphNodeWrappers.size(); m++){

			if(graphNodeWrappers.get(m).getNode().getVertexData().equals(findMe)){
				return graphNodeWrappers.get(m);
			}

		}

		return null;
	}

	/**
	 * returns an array holding all of the property edge names in the graph
	 * @return propertyNames - the global variable string array holding the EdgePropertyNames
	 */
	public String[] getEdgePropertyNames() {
		return propertyNames;
	}
/**
	 * Returns a String of entire contents of graph, formatted in correct style
	 * @return ret - a string of entire graph and properties
	 */
	public String toString() {

		String ret = "";

		for(int x = 0; x < graphNodes.size(); x++){
			for(int l = 0; l < graphNodes.get(x).getOutEdges().size(); l++){
				ret += graphNodes.get(x).getOutEdges().get(l).getSource() + " ";

				for(int m = 0; m < graphNodes.get(x).getOutEdges().get(l).getProperties().size(); m++){
					ret += graphNodes.get(x).getOutEdges().get(l).getProperties().get(m) + " ";
				}
				ret += graphNodes.get(x).getOutEdges().get(l).getDestination();
				if(l+1==graphNodes.get(x).getOutEdges().size()){
					ret += "\n";
				}else{
					ret += ", ";
				}
			}
		}

		return ret;

	}
}
