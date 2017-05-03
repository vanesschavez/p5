import java.util.ArrayList;
import java.util.List;

public class NavigationGraph implements GraphADT<Location, Path> {

	//TODO: add the private variables
	private ArrayList<GraphNode<Location, Path>> graphNodes;
	private String[] edgePropertyNames;
	
	
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
		edgePropertyNames = new String[numberOfProperties];
		
		for(int l = 0; l < numberOfProperties; l++){
			
			edgePropertyNames[l] = edgePropertyNames[2+l];
			
		}
		
		
		//every iteration corresponds to one new path.
		for(int i = 1; i < edgePropertyNames.length; i++){
			
			//split info
			tempInfo = edgePropertyNames[i].split(" ");
			
			//set up temporary Locations for the path
			tempSource = new Location(tempInfo[0]);
			tempDest = new Location(tempInfo[1]);
			

			
			
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
		
		//iterate through list check if we already have the graph node
		for(int x = 0; x < graphNodes.size(); x++){
			
			//Compare the locations inside each vertex
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
		boolean found = false;
		
		// find index in edgeProperties array for the property of interest
		int propertyIndex = -1;
		for (int i = 0; i < edgePropertyNames.length; i++) {
			if (edgePropertyNames[i].equals(edgePropertyName)) 
				propertyIndex = i;
		}
		
		GraphNode<Location, Path> current = null;
		ArrayList<Path> toReturn = new ArrayList<Path>(); 
		double[] distances = new double[graphNodes.size()];
		ArrayList<Location> unvisited = new ArrayList<Location>();
		ArrayList<Location> visited = new ArrayList<Location>();
		
		for (int i = 0; i < graphNodes.size(); i++) {
			if (graphNodes.get(i).getVertexData().equals(src)) 
				current = graphNodes.get(i);
		}
		distances[findPositionInGraphNodes(current.getVertexData())] = 0;
		
		while (!found) {
			current = getSmallestNode(unvisited, propertyIndex, distances);
			unvisited.remove(current);
			visited.add(current.getVertexData());
			if (current.getVertexData().equals(dest)) found = true;
			evaluateNeighbors(current, visited, propertyIndex, unvisited, distances);
		}
		
		while (visited.size() > 1) {
			toReturn.add(findEdge(visited.get(visited.size() - 1), visited.get(visited.size() - 2)));
			visited.remove(visited.size() - 2);
		}
		
		return toReturn;
	}
	
	private Path findEdge(Location dest, Location src) {
		for (int i = 0 ; i < graphNodes.get(findPositionInGraphNodes(src)).getOutEdges().size(); i++) {
			if (graphNodes.get(findPositionInGraphNodes(src)).getOutEdges().get(i).getDestination().equals(dest)) {
				return graphNodes.get(findPositionInGraphNodes(src)).getOutEdges().get(i);
			}
		}
		return null;
	}
	
	private GraphNode<Location, Path> getSmallestNode(ArrayList<Location> unvisited, int propertyIndex, double[] distances) {
		Location min = unvisited.get(0);
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
	
	private int findPositionInGraphNodes(Location location) {
		for (int i = 0; i < graphNodes.size(); i ++) {
			if (graphNodes.get(i).equals(location)) return i;
		}
		return -1;
	}

	private void evaluateNeighbors(GraphNode<Location, Path> node, ArrayList<Location> visited, 
			int propertyIndex, ArrayList<Location> unvisited, double[] distances) {
		// make an ArrayList of each of node's unvisited neighbors
		ArrayList<Path> validPaths = new ArrayList<Path>();
		List<Path> paths = node.getOutEdges();
		for (int i = 0; i < paths.size(); i++) {
			for (int j = 0; j < visited.size(); j++) {
				if (!paths.get(i).getDestination().equals(visited.get(j)))
					validPaths.add(paths.get(i));
			}
		}
		for (int i = 0; i < validPaths.size(); i++) {
			Location destination = validPaths.get(i).getDestination();
			double distance = validPaths.get(i).getProperties().get(propertyIndex);
			if (distances[findPositionInGraphNodes(destination)] == 0) 
				distance += distances[findPositionInGraphNodes(destination)];
			if (distances[findPositionInGraphNodes(destination)] > distance)
				distances[findPositionInGraphNodes(destination)] = distance;
			unvisited.add(destination);
		}
		
	}


	@Override
	public String[] getEdgePropertyNames() {
		return edgePropertyNames;
	}

}
