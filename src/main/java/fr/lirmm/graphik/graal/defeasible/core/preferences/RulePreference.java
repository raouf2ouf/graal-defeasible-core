package fr.lirmm.graphik.graal.defeasible.core.preferences;

import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.core.term.DefaultTermFactory;
import fr.lirmm.graphik.graal.defeasible.core.LogicalObjectsFactory;

@SuppressWarnings("serial")
public class RulePreference extends Preference {
	
	public RulePreference() {
		super(LogicalObjectsFactory.instance().getRulePreferencePredicate());
		
	}
	
	public RulePreference(Term sup, Term inf) {
		super(LogicalObjectsFactory.instance().getRulePreferencePredicate(), sup, inf);
	}

	public RulePreference(String label1, String label2) {
		this(DefaultTermFactory.instance().createConstant(label1), DefaultTermFactory.instance().createConstant(label2));
	}
}
