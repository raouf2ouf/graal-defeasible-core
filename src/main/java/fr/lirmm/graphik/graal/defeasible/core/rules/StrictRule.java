package fr.lirmm.graphik.graal.defeasible.core.rules;

import java.util.HashSet;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.InMemoryAtomSet;
import fr.lirmm.graphik.graal.api.core.Rule;
import fr.lirmm.graphik.graal.core.DefaultRule;
import fr.lirmm.graphik.graal.defeasible.core.Authorable;
import fr.lirmm.graphik.util.stream.CloseableIteratorWithoutException;

public class StrictRule extends DefaultRule implements Authorable {
	private HashSet<String> authors;
	
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
	
	
	public HashSet<String> getAuthors() {
		return authors;
	}

	public void setAuthors(HashSet<String> authors) {
		this.authors = authors;
	}
	
	
	/**
     * Verifies if two StrictRules are equivalent or not.
     * @param obj the object to test
     * @return true if the objects are equal, false otherwise.
     */
	@Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof StrictRule)) { return false; }
        
        StrictRule other = (StrictRule) obj;
        // They must have the same body
        CloseableIteratorWithoutException<Atom> itBodyOther = other.getBody().iterator();
        while(itBodyOther.hasNext()) {
        	if(!this.getBody().contains(itBodyOther.next())) {
        		return false;
        	}
        }
        CloseableIteratorWithoutException<Atom> itBodyMe = this.getBody().iterator();
        while(itBodyMe.hasNext()) {
        	if(!other.getBody().contains(itBodyMe.next())) {
        		return false;
        	}
        }
        
        // They must have the same head
        CloseableIteratorWithoutException<Atom> itHeadOther = other.getHead().iterator();
        while(itHeadOther.hasNext()) {
        	if(!this.getHead().contains(itHeadOther.next())) {
        		return false;
        	}
        }
        CloseableIteratorWithoutException<Atom> itHeadMe = this.getHead().iterator();
        while(itHeadMe.hasNext()) {
        	if(!other.getHead().contains(itHeadMe.next())) {
        		return false;
        	}
        }
       
        return true;
    }
}
