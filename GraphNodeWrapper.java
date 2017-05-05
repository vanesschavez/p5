/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p5
// FILE:             GraphNodeWrapper.java
//
// TEAM:    Team 191
// Authors:
// Author1: (Roberto O'Dogherty, rodogherty@wisc.edu, o-dogherty, 001)
// Author2: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
/**
 * Wrapper class to help with FindShortestPath method in NavigationGraph
 *
 */
class GraphNodeWrapper {


	private GraphNode<Location,Path> node; //node
	private Double distance; //total shortest distance
	private GraphNodeWrapper previous; //previous node to compare
	private boolean visited; //checks if nodes have been visited
	private boolean starter; //to make sure start doesn't change value
	/**
	 * instantiates all variables
	 * @param node - represents the graphnode to be checked
	 *
	 */
	public GraphNodeWrapper(GraphNode<Location,Path> node){
		//instantiates all variables
		this.node = node;
		distance = 0.0;
		visited = false;
		starter = false;

	}


	/**
	 * Returns true if this node led to the shortest path so far
	 * @param distance - distance to be checked
	 * @param caller - wrapper to compare
	 * @return whether or not has led to shortest path
	 */
	public boolean setDistance(Double distance, GraphNodeWrapper caller){
		//so start can't be changed
		if(starter){
			return false;
		}
		//checks if first time visited
		if(this.distance == 0.0){

			this.distance = distance;
			previous = caller;

			return true;

			//compares the distances to update shortest total distance
		}else if(distance< this.distance){
			this.distance = distance;
			previous = caller;
			return true;
		}

		return false;

	}
	/**
	 * returns previous GraphNodeWrapper
	 */
	public GraphNodeWrapper getPrevious(){
		return previous;
	}
	/**
	 * Returns the current graphNode
	 */
	public GraphNode<Location,Path> getNode(){
		return node;
	}
	/**
	 * Returns the current shortest distance
	 */
	public Double getDistance(){
		return distance;
	}
	/**
	 * Returns if node has been visited or not
	 */
	public boolean getVisited(){
		return visited;
	}
	/**
	 * changes so that particular node has been visited
	 */
	public void changeVisited(){
		visited = !visited;
	}
	/**
	 * sets the start as true so can't change value
	 */
	public void setStarter(){
		starter = true;
	}

}
