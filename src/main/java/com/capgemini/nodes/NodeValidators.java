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
	public static final String NULL_PARAM = "One of params is null";

	private static final int ID_MAX_LENGTH = 4;
	private static final int DESCRIPTION_MAX_LENGTH = 128;
	private static final int INITIAL_ROOT_COLOR = -1;

	private static Map<String, Integer> colorMap;
	private static Map<String, List<String>> directChildren;
	private static int rootColor;
	private static List<Node> nodes;

	public static void validateMethod(List<Node> nodesToValidate) throws Exception {
		initializeFields(nodesToValidate);
		basicCheckAndColorMapAndRootColorInitialization();
		checkIfRootExists();
		mergeColorMapAndCheckPredecessorCorrectness();
		checkColorMapConsistency();
		fillDirectChildrenMap();
		checkNumberOfChildren();
	}

	private static void basicCheckAndColorMapAndRootColorInitialization() throws Exception {
		for (int i = 0; i < nodes.size(); i++) {
			Node currentNode = nodes.get(i);

			checkForNulls(currentNode);
			checkIdLength(currentNode);
			checkDescriptionLength(currentNode);
			fillInitialColorMapForThisNodeAndCheckIfIsRoot(currentNode, i);
		}
	}

	private static void checkNumberOfChildren() throws Exception {
		for (Entry<String, List<String>> childrenEntry : directChildren.entrySet()) {
			checkIfHasMoreThanTwoChildren(childrenEntry);
			checkIfHasTwoChildrenAndIsPenultimate(childrenEntry);
		}
	}

	private static void checkIfHasMoreThanTwoChildren(Entry<String, List<String>> childrenEntry) throws Exception {
		if (childrenEntry.getValue().size() > 2) {
			throw new Exception(TOO_MANY_CHILDREN);
		}
	}

	private static void checkIfHasTwoChildrenAndIsPenultimate(Entry<String, List<String>> childrenEntry)
			throws Exception {
		if (childrenEntry.getValue().size() == 2) {
			for (String childId : childrenEntry.getValue()) {
				if (directChildren.get(childId) != null) {
					throw new Exception(TOO_MANY_CHILDREN);
				}
			}
		}
	}

	private static void fillDirectChildrenMap() {
		for (Node node : nodes) {
			String predId = node.getPredecessorId();

			if (!predId.equals(IS_ROOT)) {
				if (!directChildren.containsKey(predId)) {
					directChildren.put(predId, new ArrayList<>());
				}
				directChildren.get(predId).add(node.getId());
			}
		}
	}

	private static void checkColorMapConsistency() throws Exception {
		// check, if every node is connected to root
		for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
			if (!entry.getValue().equals(rootColor)) {
				throw new Exception(INCONSISTENT_NODES);
			}
		}
	}

	private static void mergeColorMapAndCheckPredecessorCorrectness() throws Exception {
		for (Node node : nodes) {
			int currentColor = colorMap.get(node.getId());
			if (currentColor != rootColor) {
				int newColor = getColorOfPredecessor(node);
				changeColorOfChildren(currentColor, newColor);
			}
		}
	}

	private static Integer getColorOfPredecessor(Node node) throws Exception {
		Integer newColor = colorMap.get(node.getPredecessorId());
		if (newColor == null) { // predecessor not found in color map
			throw new Exception(NOT_EXISTING_PREDECESSOR);
		}
		return newColor;
	}

	private static void changeColorOfChildren(int currentChildColor, int newChildColor) {
		for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
			if (entry.getValue().equals(currentChildColor)) {
				entry.setValue(newChildColor);
			}
		}
	}

	private static void initializeFields(List<Node> nodesToInitialize) {
		nodes = nodesToInitialize;
		colorMap = new HashMap<>();
		directChildren = new HashMap<>();
		rootColor = INITIAL_ROOT_COLOR;
	}

	private static void checkIfRootExists() throws Exception {
		if (rootColor == -1) {
			throw new Exception(NO_ROOT);
		}
	}

	private static void fillInitialColorMapForThisNodeAndCheckIfIsRoot(Node currentNode, int currentColor)
			throws Exception {

		if (colorMap.get(currentNode.getId()) != null) {
			throw new Exception(DUPLICATED_ID);
		}
		colorMap.put(currentNode.getId(), currentColor);

		if (currentNode.getPredecessorId().equals(IS_ROOT)) {
			if (rootColor != INITIAL_ROOT_COLOR) { // was changed before, so
													// another root exists
				throw new Exception(INCONSISTENT_NODES);
			}
			rootColor = currentColor;
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