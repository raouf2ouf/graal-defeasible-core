package fr.lirmm.graphik.graal.defeasible.core.preferences;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.defeasible.core.LogicalObjectsFactory;

@SuppressWarnings("serial")
public class AlternativePreference extends Preference {
	
	public AlternativePreference(Atom a) {
		super(a);
	}
	
	public AlternativePreference() {
		super(LogicalObjectsFactory.instance().getAlternativePreferencePredicate());
		
	}
	
	public AlternativePreference(Term sup, Term inf) {
		super(LogicalObjectsFactory.instance().getAlternativePreferencePredicate(), sup, inf);
	}
}
