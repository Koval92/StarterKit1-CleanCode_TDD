package com.capgemini.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// @formatter:off
/**
 * Created by ldrygala on 2015-02-09.
 * <p/>
 * Write validate for
 * <ul>
 *     <li>node id should have 4 characters</li>
 *     <li>node description can have maximal 128 characters</li>
 *     <li>no cycle</li>
 *     <li>only penultimate can have two subsequent</li>
 * </ul>
 */
// @formatter:on

public class NodeValidators {
	public static final String TOO_LONG_ID = "Id is too long";
	public static final String TOO_LONG_DESCRIPTION = "Description is too long";
	public static final String DUPLICATED_ID = "Id already exists";
	public static final String NO_ROOT = "Root doesn't exist ";
	public static final String NOT_EXISTING_PREDECESSOR = "Wrong predecessor";
	public static final String IS_ROOT = "ROOT";
	public static final String INCONSISTENT_NODES = "Inconsistent";
	public static final String TOO_MANY_CHILDREN = "Too many children or at wrong level";
	private static final int ID_MAX_LENGTH = 4;
	private static final int INITIAL_ROOT_COLOR = -1;
	private static final int DESCRIPTION_MAX_LENGTH = 128;
	public static final String NULL_PARAM = "One of params is null";

	public static void validateMethod(List<Node> nodes) throws Exception {
		Map<String, Integer> colorMap = new HashMap<>();
		Map<String, List<String>> directChildren = new HashMap<>();
		int rootColor = INITIAL_ROOT_COLOR;

		for (int i = 0; i < nodes.size(); i++) {
			Node currentNode = nodes.get(i);

			checkForNulls(currentNode);
			checkIdLength(currentNode);
			checkDescriptionLength(currentNode);
			
			// check, if root ID already exists
			if (colorMap.get(currentNode.getId()) != null) {
				throw new Exception(DUPLICATED_ID);
			}
			colorMap.put(currentNode.getId(), i);
			
			// find root, and if only only exists
			if (currentNode.getPredecessorId().equals(IS_ROOT)) {
				if (rootColor != INITIAL_ROOT_COLOR) { // was changed before, so another root exists
					throw new Exception(INCONSISTENT_NODES);
				}
				rootColor = i;
			}
		}

		if (rootColor == -1) {
			throw new Exception(NO_ROOT);
		}

		// color every node (and its children) by color of its parent
		for (Node node : nodes) {
			int currentColor = colorMap.get(node.getId());
			if (currentColor != rootColor) {
				Integer newColor = colorMap.get(node.getPredecessorId());
				if (newColor == null) { // predecessor not found in color map
					throw new Exception(NOT_EXISTING_PREDECESSOR);
				}
				for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
					if (entry.getValue().equals(currentColor)) {
						entry.setValue(newColor);
					}
				}
			}
		}

		// check, if every node is connected to root
		for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
			if (!entry.getValue().equals(rootColor)) {
				throw new Exception(INCONSISTENT_NODES);
			}
		}
		
		for (Node node : nodes) {
			String predId = node.getPredecessorId();
			
			if(!predId.equals(IS_ROOT)) {
				if(!directChildren.containsKey(predId)) {
					directChildren.put(predId, new ArrayList<>());
				}
				directChildren.get(predId).add(node.getId());
			}
		}
		
		// check number of children
		for (Entry<String, List<String>> childrenEntry : directChildren.entrySet()) {
			if(childrenEntry.getValue().size() > 2) {
				throw new Exception(TOO_MANY_CHILDREN);
			}
			if(childrenEntry.getValue().size() == 2) {
				System.out.println(childrenEntry.getKey());
				for (String childId : childrenEntry.getValue()) {
					System.out.println("\t" + childId);
					if(directChildren.get(childId) != null) {
						throw new Exception(TOO_MANY_CHILDREN);
					}
				}
			}
		}
	}

	private static void checkForNulls(Node currentNode) throws Exception {
		if (currentNode.getId() == null) {
			throw new Exception(NULL_PARAM);
		}
		if (currentNode.getPredecessorId() == null) {
			throw new Exception(NULL_PARAM);
		}
		if (currentNode.getDescription() == null) {
			throw new Exception(NULL_PARAM);
		}
	}

	private static void checkDescriptionLength(Node node) throws Exception {
		if (node.getDescription().length() > DESCRIPTION_MAX_LENGTH) {
			throw new Exception(TOO_LONG_DESCRIPTION);
		}
	}

	private static void checkIdLength(Node node) throws Exception {
		if (node.getId().length() > ID_MAX_LENGTH) {
			throw new Exception(TOO_LONG_ID);
		}
	}
}
