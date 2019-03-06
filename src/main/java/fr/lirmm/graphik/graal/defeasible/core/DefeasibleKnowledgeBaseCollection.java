package fr.lirmm.graphik.graal.defeasible.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import fr.lirmm.graphik.graal.api.core.AtomSet;
import fr.lirmm.graphik.graal.api.core.AtomSetException;
import fr.lirmm.graphik.graal.api.core.Rule;
import fr.lirmm.graphik.graal.api.core.RuleSet;
import fr.lirmm.graphik.graal.api.forward_chaining.Chase;
import fr.lirmm.graphik.graal.api.forward_chaining.ChaseException;
import fr.lirmm.graphik.graal.api.forward_chaining.RuleApplier;
import fr.lirmm.graphik.graal.api.io.ParseException;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.core.ruleset.LinkedListRuleSet;
import fr.lirmm.graphik.graal.defeasible.core.preferences.Preference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.PreferenceSet;
import fr.lirmm.graphik.graal.forward_chaining.SccChase;

public class DefeasibleKnowledgeBaseCollection implements Collection<DefeasibleKnowledgeBase> {
	private Collection<DefeasibleKnowledgeBase> kbs;
	private AtomSet saturatedFacts;
	
	public DefeasibleKnowledgeBaseCollection() {
		this.kbs = new LinkedList<DefeasibleKnowledgeBase>();
	}
	
	public DefeasibleKnowledgeBaseCollection(Collection<DefeasibleKnowledgeBase> kbs) {
		this.kbs = kbs;
	}
	
	public DefeasibleKnowledgeBaseCollection(DefeasibleKnowledgeBase kb) {
		this();
		this.kbs.add(kb);
	}
	
	//--------------------------------------------
	// Getters and Setters
	//--------------------------------------------
	/* Facts */
	/**
	 * Returns the initial set of facts
	 * @return the initial set of facts
	 * @throws AtomSetException 
	 */
	public AtomSet getFacts() throws AtomSetException {
		AtomSet facts = new LinkedListAtomSet();
		for(DefeasibleKnowledgeBase kb: this.kbs) {
			facts.addAll(kb.getFacts());
		}
		return facts;
	}
	
	/* Preferences */
	/**
	 * Returns the set of preference on rules
	 * @return the set of preference on rules
	 */
	public PreferenceSet getRulePreferences() {
		PreferenceSet prefs = new PreferenceSet();
		for(DefeasibleKnowledgeBase kb: kbs) {
			for(Preference p: kb.getRulePreferences().values())
			prefs.add(p);
		}
		return prefs;
	}
	/**
	 * Returns the set of preference on alternatives
	 * @return the set of preference on alternatives
	 */
	public PreferenceSet getAlternativePreferences() {
		PreferenceSet prefs = new PreferenceSet();
		for(DefeasibleKnowledgeBase kb: kbs) {
			for(Preference p: kb.getAlternativePreferences().values())
			prefs.add(p);
		}
		return prefs;
	}
	
	/* Rules */
	/**
	 * Returns the set of strict rules
	 * @return the set of strict rules
	 */
	public RuleSet getStrictRules() {
		RuleSet rules = new LinkedListRuleSet();
		for(DefeasibleKnowledgeBase kb: this.kbs) {
			rules.addAll(kb.getStrictRules().iterator());
		}
		return rules;
	}
	/**
	 * Returns the set of defeasible rules
	 * @return the set of defeasible rules
	 */
	public RuleSet getDefeasibleRules() {
		RuleSet rules = new LinkedListRuleSet();
		for(DefeasibleKnowledgeBase kb: this.kbs) {
			rules.addAll(kb.getDefeasibleRules().iterator());
		}
		return rules;
	}
	/**
	 * Returns the set of defeater rules
	 * @return the set of defeater rules
	 */
	public RuleSet getDefeaterRules() {
		RuleSet rules = new LinkedListRuleSet();
		for(DefeasibleKnowledgeBase kb: this.kbs) {
			rules.addAll(kb.getDefeaterRules().iterator());
		}
		return rules;
	}
	/**
	 * Returns the set of preference rules
	 * @return the set of preference rules
	 */
	public RuleSet getPreferenceRules() {
		RuleSet rules = new LinkedListRuleSet();
		for(DefeasibleKnowledgeBase kb: this.kbs) {
			rules.addAll(kb.getPreferenceRules().iterator());
		}
		return rules;
	}
	/**
	 * Returns the set of negative constraints
	 * @return the set of negative constraints
	 */
	public RuleSet getNegativeConstraints() {
		RuleSet rules = new LinkedListRuleSet();
		for(DefeasibleKnowledgeBase kb: this.kbs) {
			rules.addAll(kb.getNegativeConstraints().iterator());
		}
		return rules;
	}
	/**
	 * Returns a set containing all strict, defeasible, and defeater rules
	 * @return the set of strict rules
	 */
	public RuleSet getRules() {
		RuleSet rules = new LinkedListRuleSet();
		for(DefeasibleKnowledgeBase kb: this.kbs) {
			rules.addAll(kb.getRules().iterator());
		}
		return rules;
	}
	
	public AtomSet getSaturatedFacts() {
		return this.saturatedFacts;
	}
	
	public AtomSet saturate(RuleApplier<Rule, AtomSet> ruleApplier) throws ChaseException, AtomSetException {
		this.saturatedFacts = getFacts();
		Chase chase = new SccChase<AtomSet>(this.getRules().iterator(), this.saturatedFacts, ruleApplier);
		chase.execute();
		return this.saturatedFacts;
	}
	
	//--------------------------------------------
	// Methods
	//--------------------------------------------
	public void addKnowledgeBase(String dlgp, String author) throws ParseException, AtomSetException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase(author);
		kb.add(dlgp);
		this.kbs.add(kb);
	}
	
	
	
	@Override
	public boolean add(DefeasibleKnowledgeBase arg0) {
		return this.kbs.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends DefeasibleKnowledgeBase> arg0) {
		return this.kbs.addAll(arg0);
	}

	@Override
	public void clear() {
		this.kbs.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return this.kbs.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return this.kbs.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.kbs.isEmpty();
	}

	@Override
	public Iterator<DefeasibleKnowledgeBase> iterator() {
		return this.kbs.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		return this.kbs.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return this.kbs.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return this.kbs.retainAll(arg0);
	}

	@Override
	public int size() {
		return this.kbs.size();
	}

	@Override
	public Object[] toArray() {
		return this.kbs.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return this.kbs.toArray(arg0);
	}

}
