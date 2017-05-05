import java.util.ArrayList;

class GraphNodeWrapper {

	private GraphNode<Location,Path> node;
	private Double distance;
	private GraphNodeWrapper previous;
	private boolean visited;
	
	public GraphNodeWrapper(GraphNode<Location,Path> node){
		
		this.node = node;
		distance = 0.0;
		visited = false;
		
	}
	
	
	/*
	 * Returns true if this node led to the shortest path so far
	 */
	public boolean setDistance(Double distance, GraphNodeWrapper caller){
		
		if(this.distance == 0.0){
			
			this.distance = distance;
			return true;
			
		}else if(distance< this.distance){
			this.distance = distance;
			previous = caller;
			return true;
		}
			
		return false;
		
	}
	
	public GraphNodeWrapper getPrevious(){
		return previous;
	}
	
	public GraphNode<Location,Path> getNode(){
		return node;
	}
	
	public Double getDistance(){
		return distance;
	}
	
	public boolean getVisited(){
		return visited;
	}
	
	public void changeVisited(){
		visited = !visited;
	}
	
}
