package fr.lirmm.graphik.graal.defeasible.core.rules;

import java.util.HashSet;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.InMemoryAtomSet;
import fr.lirmm.graphik.graal.core.DefaultRule;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.defeasible.core.Authorable;
import fr.lirmm.graphik.graal.defeasible.core.preferences.Preference;
import fr.lirmm.graphik.util.stream.CloseableIteratorWithoutException;

public class PreferenceRule extends DefaultRule implements Authorable {
	private HashSet<String> authors;
	
	/**
	 * Constructs a labeled preference rule from an atomset body and a preference
	 * @param label a label for the rule
	 * @param body a list of the atoms in the body
	 * @param pref a preference
	 */
	public PreferenceRule(String label, InMemoryAtomSet body, Preference pref) {
		super();
		this.setLabel(label);
		this.setBody(body);
		LinkedListAtomSet head = new LinkedListAtomSet();
		head.add(pref);
		this.setHead(head);
	}
	
	/**
	 * Constructs a labeled preference rule from an atomset body and head
	 * @param label a label for the rule
	 * @param body a list of the atoms in the body
	 * @param head a list containing one atom that is a preference
	 */
	public PreferenceRule(String label, InMemoryAtomSet body, InMemoryAtomSet head) {
		super(label, body, head);
	}
	
	
	
	public HashSet<String> getAuthors() {
		return authors;
	}

	public void setAuthors(HashSet<String> authors) {
		this.authors = authors;
	}
	
	/**
     * Verifies if two PreferenceRules are equivalent or not.
     * @param obj the object to test
     * @return true if the objects are equal, false otherwise.
     */
	@Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof PreferenceRule)) { return false; }
        
        PreferenceRule other = (PreferenceRule) obj;
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
