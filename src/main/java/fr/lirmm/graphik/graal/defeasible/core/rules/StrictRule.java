package fr.lirmm.graphik.graal.defeasible.core.rules;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.InMemoryAtomSet;
import fr.lirmm.graphik.graal.api.core.Rule;
import fr.lirmm.graphik.graal.core.DefaultRule;
import fr.lirmm.graphik.util.stream.CloseableIteratorWithoutException;

public class StrictRule extends DefaultRule {
	
	/**
	 * Constructs an empty strict rule
	 */
	public StrictRule() {
		super();
	}

	/**
	 * Constructs a strict rule from an iterator on body and head conjunctions
	 * @param body an iterator on the atoms in the body
	 * @param head an iterator on the atoms in the head
	 */
	public StrictRule(CloseableIteratorWithoutException<Atom> body, CloseableIteratorWithoutException<Atom> head) {
		super(body, head);
	}

	/**
	 * Constructs a strict rule with a label from an iterator on body and head conjunctions
	 * @param label a rule label
	 * @param body an iterator on the atoms in the body
	 * @param head an iterator on the atoms in the head
	 */
	public StrictRule(String label, CloseableIteratorWithoutException<Atom> body, CloseableIteratorWithoutException<Atom> head) {
		super(label, body, head);
	}

	/**
	 * Constructs a strict rule from two atomsets
	 * @param body an InMemoryAtomSet for body
	 * @param head an InMemoryAtomSet for head
	 */
	public StrictRule(InMemoryAtomSet body, InMemoryAtomSet head) {
		super(body, head);
	}

	/**
	 * Constructs a strict rule with a label from two atomsets
	 * @param label a rule label
	 * @param body an InMemoryAtomSet for body
	 * @param head an InMemoryAtomSet for head
	 */
	public StrictRule(String label, InMemoryAtomSet body, InMemoryAtomSet head) {
		super(label, body, head);
	}

	/**
	 * Constructs a strict rule from a rule
	 * @param rule a rule
	 */
	public StrictRule(Rule rule) {
		super(rule);
	}
}
