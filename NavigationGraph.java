/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p5
// FILE:             Interval.java
//
// TEAM:    Team 191
// Authors:
// Author1: (Roberto O'Dogherty, rodogherty@wisc.edu, o-dogherty, 001)
// Author2: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
// Author3: (Aleysha Becker, ambecker5@wisc.edu, aleysha, 001)
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.List;

public class NavigationGraph implements GraphADT<Location, Path> {

	//TODO: add the private variables
	private ArrayList<GraphNode<Location, Path>> graphNodes;
	private String[] propertyNames;


	//TODO the constructor method

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

			propertyNames[l] = edgePropertyNames[2+l];

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


	@Override
	public void addVertex(Location vertex) {

		if(vertex == null){
			throw new IllegalArgumentException();
		}

		//add Vertex from created location
		addVertexHelper(new GraphNode<Location, Path>(vertex, 0));


	}

	/*
	 * Adds graphNode if its a new graph node
	 * will return this graphNode if it is new
	 * or existing one if it already exists
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


	@Override
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


	@Override
	public List<Location> getVertices() {

		ArrayList<Location> toReturn = new ArrayList<Location>();

		//iterate through list
		for(int x = 0; x < graphNodes.size(); x++){

			toReturn.add(graphNodes.get(x).getVertexData());

		}


		return toReturn;
	}


	@Override
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


	@Override
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


	@Override
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
				//Adding to the list the neighebors that vertex is pointing to
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


	@Override
	public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {

		//if path is found
		boolean found = false;
		int propertyIndex = 0;

		// find index in edgeProperties array for the property of interest
		(int i = 0; i < propertyNames.length; i++) {
			if (propertyNames[i].equals(edgePropertyName) ){
				propertyIndex = i;
			}
		}

		GraphNode<Location, Path> current = null;
		ArrayList<Path> toReturn = new ArrayList<Path>();
		int[] distances = new int[graphNodes.size()];
		ArrayList<GraphNode<Location,Path>> unvisited = new ArrayList<GraphNode<Location, Path>>();
		ArrayList<GraphNode<Location, Path>> visited = new ArrayList<GraphNode<Location, Path>>();

		for (int i = 0; i < graphNodes.size(); i++) {
			if (graphNodes.get(i).getVertexData().equals(src))
				current = graphNodes.get(i);
		}
		distances[findPositionInGraphNodes(current)] = 0;



		while (!found) {
			current = getSmallestNode(unvisited, propertyIndex, distances);
			unvisited.remove(current);

			visited.add(current);
			if (current.getVertexData().equals(dest) ){
				found = true;
			}
			evaluateNeighbors(current, visited, propertyIndex, unvisited);
		}

		while (visited.size() > 1) {
			toReturn.add( findEdge(visited.get(visited.size() - 1), visited.get(visited.size() - 2)));

		}

		return toReturn;
	}

	private Path findEdge(GraphNode<Location, Path> dest, GraphNode<Location, Path> src) {
		for (int i = 0 ; i < src.getOutEdges().size(); i++) {
			if (src.getOutEdges().get(i).getDestination().equals(dest)) {
				return src.getOutEdges().get(i);
			}
		}
		return null;
	}

	private GraphNode<Location, Path> getSmallestNode(ArrayList<GraphNode<Location, Path>> unvisited, int propertyIndex) {
		GraphNode<Location, Path> min = unvisited.get(0);
		double minDistance = distances[findPositionInGraphNodes(min)];

		// find min
		for (int i = 1; i < unvisited.size(); i++) {
			if (distances[findPositionInGraphNodes(unvisited.get(i))] < minDistance) {
				min = unvisited.get(i);
				minDistance = distances[findPositionInGraphNodes(unvisited.get(i))];
			}
		}
		return graphNodes.get(findPositionInGraphNodes(min));
	}

	private int findPositionInGraphNodes(GraphNode<Location, Path> node) {
		for (int i = 0; i < graphNodes.size(); i ++) {
			if (graphNodes.get(i).equals(location)) return i;
		}
		return -1;
	}


	private void evaluateNeighbors(GraphNode<Location, Path> node, ArrayList<GraphNode<Location, Path>> visited,
			int propertyIndex, ArrayList<GraphNode<Location, Path>> unvisited) {
		// make an ArrayList of each of node's unvisited neighbors
		ArrayList<Path> validPaths = new ArrayList<Path>();
		List<Path> paths = node.getOutEdges();
		for (int i = 0; i < paths.size(); i++) {
			for (int j = 0; j < visited.size(); j++) {
				if (!paths.get(i).getDestination().equals(visited.get(j)))
					validPaths.add(paths.get(i));
			}
		}

		for (int i = 0; i < validPaths.size; i++) {
			GraphNode<Location, Path> destination = validPaths.get(i).getDestination();
			double distance = validPaths.get(i).pathProperties[propertyIndex];
			if (distances[findPositionInGraphNodes(destination)] != null)
				distance += distances[findPositionInGraphNodes(destination);

				if (distances[findPositionInGraphNodes(destination)] > distance)
					distances[findPositionInGraphNodes(destination)] = distance;
				unvisited.add(destination);
		}

	}


	@Override
	public String[] getEdgePropertyNames() {
		return propertyNames;
	}

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
