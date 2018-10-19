package fr.lirmm.graphik.graal.defeasible.core;

import java.util.List;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.AtomSet;
import fr.lirmm.graphik.graal.api.core.Predicate;
import fr.lirmm.graphik.graal.api.core.Rule;
import fr.lirmm.graphik.graal.api.core.RuleSet;
import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.api.io.ParseException;
import fr.lirmm.graphik.graal.core.DefaultAtom;
import fr.lirmm.graphik.graal.core.DefaultNegativeConstraint;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.core.ruleset.LinkedListRuleSet;
import fr.lirmm.graphik.graal.core.term.DefaultTermFactory;
import fr.lirmm.graphik.graal.defeasible.core.atoms.FlexibleAtom;
import fr.lirmm.graphik.graal.defeasible.core.io.DlgpDefeasibleParser;
import fr.lirmm.graphik.graal.defeasible.core.preferences.AlternativePreference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.Preference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.RulePreference;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeasibleRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeaterRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.PreferenceRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.StrictRule;

public class LogicalObjectsFactory {
	
	private static final LogicalObjectsFactory INSTANCE = new LogicalObjectsFactory();
	
	private static final String RULE_PREFERENCE_SYMBOL = ">>";
	private static final String ALTERNATIVE_PREFERENCE_SYMBOL = ">";
	
	private static final Predicate RULE_PREFERENCE_PREDICATE = new Predicate(RULE_PREFERENCE_SYMBOL,2);
	private static final Predicate ALTERNATIVE_PREFERENCE_PREDICATE = new Predicate(ALTERNATIVE_PREFERENCE_SYMBOL,2);
	
	//--------------------------------------------
	// Public Methods
	//--------------------------------------------
	/**
	 * Instanticate this factory only once
	 * @return an instance of the factory
	 */
	public static LogicalObjectsFactory instance() {
		return INSTANCE;
	}
	
	/**
	 * Creates a strict atom from a predicate and a list of terms
	 * @param predicate a predicate
	 * @param list a list of terms
	 * @return the created atom.
	 */
	public Atom createStrictAtom(Predicate predicate, List<Term> list) {
		return new DefaultAtom(predicate, list);
	}
	
	/**
	 * Creates a defeasible atom from a predicate and a list of terms
	 * @param predicate a predicate
	 * @param list a list of terms
	 * @return the created atom.
	 */
	public FlexibleAtom createDefeasibleAtom(Predicate predicate, List<Term> list) {
		return new FlexibleAtom(predicate, list);
	}
	
	/**
	 * Creates a preference on rules from two terms (constants) representing the rules labels
	 * @param term1 the constant representing the superior rule
	 * @param term2 the constant representing the inferior rule
	 * @return a preference on rules
	 */
	public RulePreference createRulePreference(Term term1, Term term2) {
		return new RulePreference(term1, term2);
	}
	/**
	 * Creates a preference on alternatives from two terms (constants) representing the alternatives
	 * @param term1 the term representing the superior alternative
	 * @param term2 the term representing the inferior alternative
	 * @return a preference on rules
	 */
	public AlternativePreference createAlternativePreference(Term term1, Term term2) {
		return new AlternativePreference(term1, term2);
	}
	
	/**
	 * Creates a strict rule from a label, body, and head
	 * @param label string representing the label of the rule
	 * @param body a List of atoms representing the body
	 * @param head a list of atoms representing the head
	 * @return a strict rule
	 */
	public StrictRule createStrictRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new StrictRule(label, body, head);
	}
	/**
	 * Creates a defeasible rule from a label, body, and head
	 * @param label string representing the label of the rule
	 * @param body a List of atoms representing the body
	 * @param head a list of atoms representing the head
	 * @return a defeasible rule
	 */
	public DefeasibleRule createDefeasibleRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new DefeasibleRule(label, body, head);
	}
	/**
	 * Creates a defeater rule from a label, body, and head
	 * @param label string representing the label of the rule
	 * @param body a List of atoms representing the body
	 * @param head a list of atoms representing the head
	 * @return a defeater rule
	 */
	public DefeaterRule createDefeaterRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new DefeaterRule(label, body, head);
	}
	/**
	 * Creates a preference rule from a label, body, and head
	 * @param label string representing the label of the rule
	 * @param body a List of atoms representing the body
	 * @param head a preference representing the head
	 * @return a preference rule
	 */
	public PreferenceRule createPreferenceRule(String label, LinkedListAtomSet body, Preference head) {
		return new PreferenceRule(label, body, head);
	}
	/**
	 * Creates a preference rule from a label, body, and head
	 * @param label string representing the label of the rule
	 * @param body a List of atoms representing the body
	 * @param head a list containing the preference representing the head
	 * @return a preference rule
	 */
	public PreferenceRule createPreferenceRule(String label, LinkedListAtomSet body, LinkedListAtomSet head) {
		return new PreferenceRule(label, body, head);
	}
	
	
	
	/**
	 * Returns the TOP atom T
	 * @return the top atom
	 */
	public Atom getTOPAtom() {
		return new FlexibleAtom(Predicate.TOP, DefaultTermFactory.instance().createConstant("true"));
	}
	
	/**
	 * Returns the predicate used for preference on rules
	 * @return the predicate used for preference on rules
	 */
	public Predicate getRulePreferencePredicate() {
		return RULE_PREFERENCE_PREDICATE;
	}
	/**
	 * Returns the predicate used for preference on alternatives
	 * @return the predicate used for preference on alternatives
	 */
	public Predicate getAlternativePreferencePredicate() {
		return ALTERNATIVE_PREFERENCE_PREDICATE;
	}
	
	/**
	 * Return a set of rules for computing the preference from transitivity
	 * @return a set of transitive rules for computing preferences.
	 */
	public RuleSet getPreferenceTransitivityRules() {
		RuleSet rules = new LinkedListRuleSet();
		
		try {
			Preference p1 = DlgpDefeasibleParser.parsePreference("X " + RULE_PREFERENCE_SYMBOL + " Y .");
			Preference p2 = DlgpDefeasibleParser.parsePreference("Y " + RULE_PREFERENCE_SYMBOL + " Z .");
			Preference p3 = DlgpDefeasibleParser.parsePreference("X " + RULE_PREFERENCE_SYMBOL + " Z .");
			Rule r = new StrictRule();
			r.getBody().add(p1);
			r.getBody().add(p2);
			r.getHead().add(p3);
			
			rules.add(r);
			p1 = DlgpDefeasibleParser.parsePreference("X " + ALTERNATIVE_PREFERENCE_SYMBOL + " Y .");
			p2 = DlgpDefeasibleParser.parsePreference("Y " + ALTERNATIVE_PREFERENCE_SYMBOL + " Z .");
			p3 = DlgpDefeasibleParser.parsePreference("X " + ALTERNATIVE_PREFERENCE_SYMBOL + " Z .");
			r = new StrictRule();
			r.getBody().add(p1);
			r.getBody().add(p2);
			r.getHead().add(p3);
			
			rules.add(r);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return rules;
	}
	
	public RuleSet getNegativeConstraintsOnAlternativePreferences() {
		RuleSet rules = new LinkedListRuleSet();
		
		AlternativePreference p1;
		try {
			p1 = DlgpDefeasibleParser.parseAlternativePreference("X " + ALTERNATIVE_PREFERENCE_SYMBOL + " Y .");
		
			AlternativePreference p2 = DlgpDefeasibleParser.parseAlternativePreference("Y " + ALTERNATIVE_PREFERENCE_SYMBOL + " X .");
			AtomSet body = new LinkedListAtomSet();
			body.add(p1);
			body.add(p2);
			Rule r = new DefaultNegativeConstraint(body.iterator());
			
			rules.add(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rules;
	}
}
