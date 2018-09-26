package fr.lirmm.graphik.graal.defeasible.core.preferences;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.core.term.DefaultTermFactory;
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
	
	public AlternativePreference(String label1, String label2) {
		this(DefaultTermFactory.instance().createConstant(label1), DefaultTermFactory.instance().createConstant(label2));
	}
}
