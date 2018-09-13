package fr.lirmm.graphik.graal.defeasible.core;

import java.util.List;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.Predicate;
import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.core.DefaultAtom;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.core.term.DefaultTermFactory;
import fr.lirmm.graphik.graal.defeasible.core.atoms.FlexibleAtom;
import fr.lirmm.graphik.graal.defeasible.core.preferences.AlternativePreference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.Preference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.RulePreference;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeasibleRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeaterRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.PreferenceRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.StrictRule;

public class LogicalObjectsFactory {
	
	private static final LogicalObjectsFactory INSTANCE = new LogicalObjectsFactory();
	
	private static final Predicate RULE_PREFERENCE_PREDICATE = new Predicate(">>",2);
	private static final Predicate ALTERNATIVE_PREFERENCE_PREDICATE = new Predicate(">",2);
	
	
	public static LogicalObjectsFactory instance() {
		return INSTANCE;
	}
	
	public Atom createStrictAtom(Predicate predicate, List<Term> list) {
		return new DefaultAtom(predicate, list);
	}

	public FlexibleAtom createDefeasibleAtom(Predicate predicate, List<Term> list) {
		return new FlexibleAtom(predicate, list);
	}
	
	public RulePreference createRulePreference(Term term1, Term term2) {
		return new RulePreference(term1, term2);
	}
	
	public AlternativePreference createAlternativePreference(Term term1, Term term2) {
		return new AlternativePreference(term1, term2);
	}
	
	public StrictRule createStrictRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new StrictRule(label, body, head);
	}
	
	public DefeasibleRule createDefeasibleRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new DefeasibleRule(label, body, head);
	}
	
	public DefeaterRule createDefeaterRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new DefeaterRule(label, body, head);
	}
	
	public PreferenceRule createPreferenceRule(String label, LinkedListAtomSet body, Preference head) {
		return new PreferenceRule(label, body, head);
	}
	
	public PreferenceRule createPreferenceRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new PreferenceRule(label, body, head);
	}
	
	
	
	public Atom getTOPAtom() {
		return new DefaultAtom(Predicate.TOP, DefaultTermFactory.instance().createConstant("true"));
	}
	
	public Predicate getRulePreferencePredicate() {
		return RULE_PREFERENCE_PREDICATE;
	}
	
	public Predicate getAlternativePreferencePredicate() {
		return ALTERNATIVE_PREFERENCE_PREDICATE;
	}
}
