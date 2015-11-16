package com.capgemini.nodes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class NodeValidatorsTest {

	@Test
	public void shouldThrowNothingForSingleCorrectNode() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node validNode = new Node("1a", NodeValidators.IS_ROOT, "simple description");
		nodes.add(validNode);
		// when
		try {
			NodeValidators.validateMethod(nodes);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		// then
	}

	@Test
	public void shouldThrowForNullId() {
		// give
		List<Node> nodes = new ArrayList<>();
		Node nullIdNode = new Node(null, NodeValidators.IS_ROOT, "");
		nodes.add(nullIdNode);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail();
		} catch (Exception e) {
			assertEquals(NodeValidators.NULL_PARAM, e.getMessage());
		}
		// then
	}

	@Test
	public void shouldThrowForNullPredecessorId() {
		// give
		List<Node> nodes = new ArrayList<>();
		Node nullIdNode = new Node("1", null, "");
		nodes.add(nullIdNode);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail();
		} catch (Exception e) {
			assertEquals(NodeValidators.NULL_PARAM, e.getMessage());
		}
		// then
	}

	@Test
	public void shouldThrowForNullDescription() {
		// give
		List<Node> nodes = new ArrayList<>();
		Node nullIdNode = new Node("1", NodeValidators.IS_ROOT, null);
		nodes.add(nullIdNode);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail();
		} catch (Exception e) {
			assertEquals(NodeValidators.NULL_PARAM, e.getMessage());
		}
		// then
	}

	@Test()
	public void shouldThrowForTooLongDescription() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node validNode = new Node("1", NodeValidators.IS_ROOT, "");
		nodes.add(validNode);
		Node nodeWithTooLongDescription = new Node("2", "1",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
						+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		nodes.add(nodeWithTooLongDescription);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.TOO_LONG_DESCRIPTION, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForTooLongId() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node validNode = new Node("1234", NodeValidators.IS_ROOT, "");
		nodes.add(validNode);
		Node nodeWithTooLongId = new Node("2a432", "1", "");
		nodes.add(nodeWithTooLongId);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.TOO_LONG_ID, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForNotDistinctId() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node rootNode = new Node("root", NodeValidators.IS_ROOT, "");
		nodes.add(rootNode);
		Node validNode = new Node("1", "root", "");
		nodes.add(validNode);
		Node nodeWithTheSameId = new Node("1", "1", "");
		nodes.add(nodeWithTheSameId);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.DUPLICATED_ID, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForCycle() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node node1 = new Node("1", "3", "");
		nodes.add(node1);
		Node node2 = new Node("2", "1", "");
		nodes.add(node2);
		Node node3 = new Node("3", "2", "");
		nodes.add(node3);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.NO_ROOT, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForInconsistentCycle() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node node1 = new Node("1", "3", "");
		nodes.add(node1);
		Node node2 = new Node("2", "1", "");
		nodes.add(node2);
		Node node3 = new Node("3", "2", "");
		nodes.add(node3);
		Node node4 = new Node("4", NodeValidators.IS_ROOT, "");
		nodes.add(node4);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.INCONSISTENT_NODES, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForInconsistent() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node node1 = new Node("1", NodeValidators.IS_ROOT, "");
		nodes.add(node1);
		Node node2 = new Node("2", "1", "");
		nodes.add(node2);
		Node node3 = new Node("3", "4", "");
		nodes.add(node3);
		Node node4 = new Node("4", NodeValidators.IS_ROOT, "");
		nodes.add(node4);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.INCONSISTENT_NODES, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForNotExistingPredecessor() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node node1 = new Node("1", NodeValidators.IS_ROOT, "");
		nodes.add(node1);
		Node node2 = new Node("2", "1", "");
		nodes.add(node2);
		Node node3 = new Node("3", "21", "");
		nodes.add(node3);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.NOT_EXISTING_PREDECESSOR, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForTooManyChildrenOfPenultimateNode() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node node1 = new Node("1", NodeValidators.IS_ROOT, "");
		nodes.add(node1);
		Node node2 = new Node("2", "1", "");
		nodes.add(node2);
		Node node3 = new Node("3", "2", "");
		nodes.add(node3);
		Node node4 = new Node("4", "3", "");
		nodes.add(node4);
		Node node5 = new Node("5", "3", "");
		nodes.add(node5);
		Node node6 = new Node("6", "3", "");
		nodes.add(node6);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.TOO_MANY_CHILDREN, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowForTooManyChildrenOfNotPenultimateNode() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node node1 = new Node("1", NodeValidators.IS_ROOT, "");
		nodes.add(node1);
		Node node2 = new Node("2", "1", "");
		nodes.add(node2);
		Node node3 = new Node("3", "2", "");
		nodes.add(node3);
		Node node4 = new Node("4", "3", "");
		nodes.add(node4);
		Node node5 = new Node("5", "2", "");
		nodes.add(node5);
		// when
		try {
			NodeValidators.validateMethod(nodes);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(NodeValidators.TOO_MANY_CHILDREN, e.getMessage());
		}
	}

	@Test()
	public void shouldThrowNothingForValidNodes() {
		// given
		List<Node> nodes = new ArrayList<>();
		Node node1 = new Node("1", NodeValidators.IS_ROOT, "");
		nodes.add(node1);
		Node node2 = new Node("2", "1", "");
		nodes.add(node2);
		Node node3 = new Node("3", "2", "");
		nodes.add(node3);
		Node node4 = new Node("4", "3", "");
		nodes.add(node4);
		Node node5 = new Node("5", "3", "");
		nodes.add(node5);
		// when
		try {
			NodeValidators.validateMethod(nodes);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should throw nothing");
		}
	}
}
