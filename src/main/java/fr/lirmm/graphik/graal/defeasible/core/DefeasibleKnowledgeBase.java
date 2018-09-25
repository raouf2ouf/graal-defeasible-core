package fr.lirmm.graphik.graal.defeasible.core;

import java.io.Reader;
import java.io.StringReader;

import fr.lirmm.graphik.graal.api.core.AtomSet;
import fr.lirmm.graphik.graal.api.core.AtomSetException;
import fr.lirmm.graphik.graal.api.core.NegativeConstraint;
import fr.lirmm.graphik.graal.api.core.Rule;
import fr.lirmm.graphik.graal.api.core.RuleSet;
import fr.lirmm.graphik.graal.api.forward_chaining.Chase;
import fr.lirmm.graphik.graal.api.forward_chaining.ChaseException;
import fr.lirmm.graphik.graal.api.forward_chaining.RuleApplier;
import fr.lirmm.graphik.graal.api.io.ParseException;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.core.ruleset.LinkedListRuleSet;
import fr.lirmm.graphik.graal.defeasible.core.atoms.FlexibleAtom;
import fr.lirmm.graphik.graal.defeasible.core.io.DlgpDefeasibleParser;
import fr.lirmm.graphik.graal.defeasible.core.preferences.AlternativePreference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.PreferenceSet;
import fr.lirmm.graphik.graal.defeasible.core.preferences.RulePreference;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeasibleRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeaterRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.PreferenceRule;
import fr.lirmm.graphik.graal.forward_chaining.BasicChase;
import fr.lirmm.graphik.graal.forward_chaining.SccChase;

public class DefeasibleKnowledgeBase {
	
	protected RuleSet strictRules;
	protected RuleSet defeasibleRules;
	protected RuleSet defeaterRules;
	protected RuleSet negativeConstraints;
	protected RuleSet preferenceRules;
	
	protected PreferenceSet rulePreferences;
	protected PreferenceSet alternativePreferences;
	protected LinkedListAtomSet facts;
	protected LinkedListAtomSet saturatedFacts;
	
	
	//--------------------------------------------
	// Constructors
	//--------------------------------------------
	
	/**
	 * Creates an empty knowledge base
	 */
	public DefeasibleKnowledgeBase() {
		this.strictRules = new LinkedListRuleSet();
		this.defeasibleRules = new LinkedListRuleSet();
		this.defeaterRules = new LinkedListRuleSet();
		this.preferenceRules = new LinkedListRuleSet();
		this.negativeConstraints = new LinkedListRuleSet();
		
		this.facts = new LinkedListAtomSet();
		this.saturatedFacts = new LinkedListAtomSet();
		
		this.rulePreferences = new PreferenceSet();
		this.alternativePreferences = new PreferenceSet();
	}
	
	//--------------------------------------------
	// Getters and Setters
	//--------------------------------------------
	
	/* Facts */
	/**
	 * Returns the initial set of facts
	 * @return the initial set of facts
	 */
	public AtomSet getFacts() {
		return this.facts;
	}
	/**
	 * Returns the saturated set of facts
	 * @return the saturated set of facts
	 */
	public AtomSet getStaturatedFacts() {
		return this.saturatedFacts;
	}
	
	/* Preferences */
	/**
	 * Returns the set of preference on rules
	 * @return the set of preference on rules
	 */
	public PreferenceSet getRulePreferences() {
		return this.rulePreferences;
	}
	/**
	 * Returns the set of preference on alternatives
	 * @return the set of preference on alternatives
	 */
	public PreferenceSet getAlternativePreferences() {
		return this.alternativePreferences;
	}
	
	/* Rules */
	/**
	 * Returns the set of strict rules
	 * @return the set of strict rules
	 */
	public RuleSet getStrictRules() {
		return this.strictRules;
	}
	/**
	 * Returns the set of defeasible rules
	 * @return the set of defeasible rules
	 */
	public RuleSet getDefeasibleRules() {
		return this.defeasibleRules;
	}
	/**
	 * Returns the set of defeater rules
	 * @return the set of defeater rules
	 */
	public RuleSet getDefeaterRules() {
		return this.defeaterRules;
	}
	/**
	 * Returns the set of preference rules
	 * @return the set of preference rules
	 */
	public RuleSet getPreferenceRules() {
		return this.preferenceRules;
	}
	/**
	 * Returns the set of negative constraints
	 * @return the set of negative constraints
	 */
	public RuleSet getNegativeConstraints() {
		return this.negativeConstraints;
	}
	/**
	 * Returns a set containing all strict, defeasible, and defeater rules
	 * @return the set of strict rules
	 */
	public RuleSet getRules() {
		RuleSet rules = new LinkedListRuleSet();
		rules.addAll(this.strictRules.iterator());
		rules.addAll(this.defeasibleRules.iterator());
		rules.addAll(this.defeaterRules.iterator());
		return rules;
	}
	
	//--------------------------------------------
	// Public Methods
	//--------------------------------------------
	
	/* Facts */	
	/**
	 * Adds a fact (atom) to the knoweldge base
	 * @param a a FlexibleAtom atom
	 * @throws AtomSetException in case there is a problem with the atomset
	 */
	public void addFact(FlexibleAtom a) throws AtomSetException {
		this.facts.add(a);
		this.saturatedFacts.add(a);
	}
	/**
	 * Parses and adds a fact to the knoweldge base
	 * @param a a dlgp string representing the atom
	 * @throws AtomSetException in case there is a problem with the atomset
	 */
	public void addFact(String a) throws ParseException, AtomSetException {
		this.addFact((FlexibleAtom) DlgpDefeasibleParser.parseAtom(a));
	}
	
	/* Preferences */
	/**
	 * Adds a preference on rules to the set of preferences
	 * @param pref a preference on rules
	 */
	public void addRulePreference(RulePreference pref) {
		this.rulePreferences.add(pref);
	}
	/**
	 * Parses and adds a preference on rules to the set of preferences
	 * @param pref a string representing a preference on rules
	 */
	public void addRulePreference(String s) throws ParseException, AtomSetException {
		this.addRulePreference(DlgpDefeasibleParser.parseRulePreference(s));
	}
	/**
	 * Adds a preference on alternatives to the set of preferences
	 * @param pref a preference on alternatives
	 */
	public void addAlternativePreference(AlternativePreference pref) {
		this.alternativePreferences.add(pref);
	}
	/**
	 * Parses and adds a preference on alternatives to the set of preferences
	 * @param s a string representing preference on alternatives
	 */
	public void addAlternativePreference(String s) throws ParseException, AtomSetException {
		this.addAlternativePreference(DlgpDefeasibleParser.parseAlternativePreference(s));
	}
	
	
	
	/* Rules */
	/**
	 * Adds a strict rule to the set of strict rules.
	 * @param r a strict rule
	 */
	public void addStrictRule(Rule r) {
		this.strictRules.add(r);
	}
	/**
	 * Parses and adds a strict rule to the set of strict rules.
	 * @param s a string representing a strict rule
	 */
	public void addStrictRule(String s) throws ParseException, AtomSetException {
		this.addStrictRule(DlgpDefeasibleParser.parseRule(s));
	}
	/**
	 * Adds a defeasible rule to the set of defeasible rules.
	 * @param r a defeasible rule
	 */
	public void addDefeasibleRule(DefeasibleRule r) {
		this.defeasibleRules.add(r);
	}
	/**
	 * Parses and adds a defeasible rule to the set of defeasible rules.
	 * @param s a string representing a defeasible rule
	 */
	public void addDefeasibleRule(String s) throws ParseException, AtomSetException {
		this.addDefeasibleRule(DlgpDefeasibleParser.parseDefeasibleRule(s));
	}
	/**
	 * Adds a defeater rule to the set of defeater rules.
	 * @param r a defeater rule
	 */
	public void addDefeaterRule(DefeaterRule r) {
		this.defeaterRules.add(r);
	}
	/**
	 * Parses and adds a defeater rule to the set of defeater rules.
	 * @param s a defeater rule
	 */
	public void addDefeaterRule(String s) throws ParseException, AtomSetException {
		this.addDefeaterRule(DlgpDefeasibleParser.parseDefeaterRule(s));
	}
	/**
	 * Adds a Negative constraint rule to the set of negative constraints.
	 * @param r a negative constraint rule
	 */
	public void addNegativeConstraint(NegativeConstraint r) {
		this.negativeConstraints.add(r);
	}
	/**
	 * Adds a Negative constraint rule to the set of negative constraints.
	 * @param s a string representing a negative constraint rule
	 */
	public void addNegativeConstraint(String s) throws ParseException, AtomSetException {
		this.addNegativeConstraint(DlgpDefeasibleParser.parseNegativeConstraint(s));
	}
	/**
	 * Adds a Preference rule to the set of preference rules.
	 * @param r a preference rule
	 */
	public void addPreferenceRule(PreferenceRule r) {
		this.preferenceRules.add(r);
	}
	/**
	 * Parses and adds a Preference rule to the set of preference rules.
	 * @param s a string representing a preference rule
	 */
	public void addPreferenceRule(String s) throws ParseException, AtomSetException {
		this.addStrictRule(DlgpDefeasibleParser.parsePreferenceRule(s));
	}
	

	/**
	 * Parses objects (e.g. atom, rule, etc.) from a reader and adds it to the knowledge base.
	 * @param reader
	 * @throws ParseException * @throws ParseException if input format error.
	 * @throws AtomSetException if the structure used to store atoms throws an error.
	 */
	public void add(Reader reader) throws ParseException, AtomSetException {
		DlgpDefeasibleParser parser = new DlgpDefeasibleParser(reader);
		while (parser.hasNext()) {
			Object o = parser.next();
			if (o instanceof NegativeConstraint) {
				this.addNegativeConstraint((NegativeConstraint) o);
			} else if (o instanceof DefeasibleRule) {
				this.addDefeasibleRule((DefeasibleRule) o);
			} else if (o instanceof DefeaterRule) {
				this.addDefeaterRule((DefeaterRule) o);
			} else if (o instanceof PreferenceRule) {
				this.addPreferenceRule((PreferenceRule) o);
			} else if (o instanceof Rule) {
				this.addStrictRule((Rule) o);
			} else if (o instanceof RulePreference) {
				this.addRulePreference((RulePreference) o);
			} else if (o instanceof AlternativePreference) {
				this.addAlternativePreference((AlternativePreference) o);
			} else if (o instanceof FlexibleAtom) {
				this.addFact((FlexibleAtom) o);
			}
		}
		parser.close();
	}
	
	/**
	 * Parses the content of a string and adds the parsed objects to the knowledge base.
	 * @param str a string containing a DLGP description of the objects to parse. 
	 * @throws ParseException if input format error.
	 * @throws AtomSetException if the structure used to store atoms throws an error.
	 */
	public void add(String str) throws ParseException, AtomSetException {
		this.add(new StringReader(str));
	}
	
	/**
	 * Executes a chase given a rule applier.
	 * @param ruleApplier a rule applier
	 * @throws ChaseException if the chase throws an error
	 */
	public void staturate(RuleApplier<Rule, AtomSet> ruleApplier) throws ChaseException {
		Chase chase = new SccChase<AtomSet>(this.getRules().iterator(), this.saturatedFacts, ruleApplier);
		chase.execute();
	}
	
	/**
	 * Generates Alternatives given a rule applier.
	 * @param ruleApplier a rule applier
	 * @throws ChaseException if the chase throws an error
	 */
	public void generateAlternatives(RuleApplier<Rule, AtomSet> ruleApplier) throws ChaseException {
		Chase chase = new BasicChase<AtomSet>(this.getPreferenceRules().iterator(), this.saturatedFacts, ruleApplier);
		chase.execute();
	}
	
	/**
	 * Generates Alternatives using transitive rules given a rule applier.
	 */
	public void generateTransitiveAlternatives() {
		this.alternativePreferences.applyTransitivityRules();
	}
	
}
