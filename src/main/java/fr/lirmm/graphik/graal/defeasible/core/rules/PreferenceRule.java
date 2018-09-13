package fr.lirmm.graphik.graal.defeasible.core.rules;

import fr.lirmm.graphik.graal.api.core.InMemoryAtomSet;
import fr.lirmm.graphik.graal.core.DefaultRule;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.defeasible.core.preferences.Preference;

public class PreferenceRule extends DefaultRule {
	
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
}
