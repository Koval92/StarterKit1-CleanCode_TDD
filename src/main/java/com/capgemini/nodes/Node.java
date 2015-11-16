package com.capgemini.nodes;

/**
 * Created by ldrygala on 2015-02-09.
 */
public class Node {
	public Node(String id, String predecessorId, String description) {
		this.id = id;
		this.predecessorId = predecessorId;
		this.description = description;
	}

	public String getId() {
		return id;
	}
	public String getPredecessorId() {
		return predecessorId;
	}
	public String getDescription() {
		return description;
	}

	private String id;
	private String predecessorId;
	private String description;
}
