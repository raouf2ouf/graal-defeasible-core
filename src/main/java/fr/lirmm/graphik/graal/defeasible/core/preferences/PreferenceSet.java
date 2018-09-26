package fr.lirmm.graphik.graal.defeasible.core.preferences;

import java.util.HashMap;
import java.util.Iterator;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.AtomSet;
import fr.lirmm.graphik.graal.api.core.AtomSetException;
import fr.lirmm.graphik.graal.api.core.RuleSet;
import fr.lirmm.graphik.graal.api.forward_chaining.Chase;
import fr.lirmm.graphik.graal.api.forward_chaining.ChaseException;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.defeasible.core.LogicalObjectsFactory;
import fr.lirmm.graphik.graal.defeasible.core.preferences.Preference.Status;
import fr.lirmm.graphik.graal.forward_chaining.BasicChase;
import fr.lirmm.graphik.util.stream.CloseableIterator;
import fr.lirmm.graphik.util.stream.IteratorException;

@SuppressWarnings("serial")
public class PreferenceSet extends HashMap<String,Preference> { // everypreference is hashed sup > inf
	
	private static RuleSet transitivityRules = LogicalObjectsFactory.instance().getPreferenceTransitivityRules();
	
	public PreferenceSet add(Preference pref) {
		this.put(pref.stringify(), pref);
		return this;
	}
	
	public AtomSet toAtomSet() {
		AtomSet prefs = new LinkedListAtomSet();
		Iterator<Preference> it = this.values().iterator();
		while(it.hasNext()) {
			try {
				prefs.add(it.next());
			} catch (AtomSetException e) {
				e.printStackTrace();
			}
		}
		return prefs;
	}
	
	public Status preferenceStatus(String label1, String label2) {
		boolean isSuperior = this.containsKey(label1 + " > " + label2);
		boolean isInferior = this.containsKey(label2 + " > " + label1);
		
		if(isSuperior && !isInferior) {
			return Status.SUPERIOR;
		} else if(isInferior && !isSuperior) {
			return Status.INFERIOR;
		} else {
			return Status.EQUAL;
		}
	}
	
	// TODO: can be optimized by only adding the new preferences.
	public void applyTransitivityRules() {
		AtomSet preferences = this.toAtomSet();
		
		Chase chase = new BasicChase<AtomSet>(transitivityRules.iterator(), preferences);
		try {
			chase.execute();
		} catch (ChaseException e) {
			e.printStackTrace();
		}
		
		CloseableIterator<Atom> prefIt = preferences.iterator();
		try {
			while(prefIt.hasNext()) {
				this.add(new AlternativePreference(prefIt.next()));
			}
		} catch (IteratorException e) {
			e.printStackTrace();
		}
	}
}
