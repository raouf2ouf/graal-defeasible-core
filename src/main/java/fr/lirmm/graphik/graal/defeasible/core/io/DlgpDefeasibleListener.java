/*
 * Copyright (C) Inria Sophia Antipolis - Méditerranée / LIRMM
 * (Université de Montpellier & CNRS) (2014 - 2017)
 *
 * Contributors :
 *
 * Clément SIPIETER <clement.sipieter@inria.fr>
 * Mélanie KÖNIG
 * Swan ROCHER
 * Jean-François BAGET
 * Michel LECLÈRE
 * Marie-Laure MUGNIER <mugnier@lirmm.fr>
 *
 *
 * This file is part of Graal <https://graphik-team.github.io/graal/>.
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package fr.lirmm.graphik.graal.defeasible.core.io;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.ConjunctiveQuery;
import fr.lirmm.graphik.graal.api.core.Constant;
import fr.lirmm.graphik.graal.api.core.InMemoryAtomSet;
import fr.lirmm.graphik.graal.api.core.Predicate;
import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.api.core.Variable;
import fr.lirmm.graphik.graal.api.io.ParseError;
import fr.lirmm.graphik.graal.core.DefaultNegativeConstraint;
import fr.lirmm.graphik.graal.core.FreshVarSubstitution;
import fr.lirmm.graphik.graal.core.atomset.LinkedListAtomSet;
import fr.lirmm.graphik.graal.core.factory.DefaultConjunctiveQueryFactory;
import fr.lirmm.graphik.graal.core.term.DefaultTermFactory;
import fr.lirmm.graphik.graal.defeasible.core.LogicalObjectsFactory;
import fr.lirmm.graphik.graal.defeasible.core.atoms.FlexibleAtom;
import fr.lirmm.graphik.graal.defeasible.core.io.parser.ParserListener;
import fr.lirmm.graphik.graal.defeasible.core.preferences.AlternativePreference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.Preference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.RulePreference;
import fr.lirmm.graphik.graal.io.dlp.Directive;
import fr.lirmm.graphik.util.Prefix;
import fr.lirmm.graphik.util.stream.CloseableIteratorWithoutException;
import fr.lirmm.graphik.util.stream.InMemoryStream;

/**
 * @author hamhec
 *
 */
class DlgpDefeasibleListener implements ParserListener {
	
	private List<Term> answerVars;
	private LinkedListAtomSet atomSet = null;
	private LinkedListAtomSet atomSet2 = null;
	private FlexibleAtom atom;
	private String label;
	private Preference pref;
	private InMemoryStream<Object> set;
	private LogicalObjectsFactory factory;
	
	DlgpDefeasibleListener(InMemoryStream<Object> buffer, LogicalObjectsFactory factory) {
		this.set = buffer;
		this.factory = factory;
	}

	public DlgpDefeasibleListener(InMemoryStream<Object> buffer) {
		this(buffer, LogicalObjectsFactory.instance());
	}

	
	/**
	 * {@inheritDoc}
	 */
	public void startsObject(OBJECT_TYPE objectType, String name) {
		this.label = (name == null) ? "" : name;
			
		this.atomSet = new LinkedListAtomSet();
		this.atomSet2 = null;
		
		if(OBJECT_TYPE.PREFERENCE.equals(objectType)) {
			// parsing a new preference, no need to keep track of the old one
			this.pref = null;
		} else if(OBJECT_TYPE.QUERY.equals(objectType)) {
			this.answerVars = new LinkedList<Term>();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void declarePrefix(String prefix, String ns) {
		this.set.write(new Prefix(prefix.substring(0, prefix.length() - 1),
				ns));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void declareBase(String base) {
		this.set.write(new Directive(Directive.Type.BASE, base));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void declareTop(String top) {
		this.set.write(new Directive(Directive.Type.TOP, top));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void declareUNA() {
		this.set.write(new Directive(Directive.Type.UNA, null));
	}

	/**
	 * {@inheritDoc}
	 */
	public void directive(String text) {
		this.set.write(new Directive(Directive.Type.COMMENT, text));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void createsAtom(Object predicate, Object[] terms) {
		List<Term> list = new LinkedList<Term>();
		for (Object t : terms) {
			list.add(createTerm(t));
		}
		atom = new FlexibleAtom(createPredicate(predicate, terms.length),list);
		this.atomSet.add(atom);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void createsEquality(Object term1, Object term2) {
		atom = new FlexibleAtom(Predicate.EQUALITY, createTerm(term1), createTerm(term2));
		this.atomSet.add(atom);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void createsPreference(Object term1, Object term2, OBJECT_TYPE objectType) {
		Preference preference = null;
		switch(objectType) {
		case RULE_PREFERENCE:
			preference = new RulePreference(createTerm(term1), createTerm(term2));
			break;
		
		case ALTERNATIVE_PREFERENCE:
			preference = new AlternativePreference(createTerm(term1), createTerm(term2));
			break;
			
		default: break;
		}
		this.pref = preference;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void answerTermList(Object[] terms) {
		for (Object t : terms) {
			this.answerVars.add(createTerm(t));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void endsConjunction(OBJECT_TYPE objectType) {
		switch (objectType) {
		case QUERY:
			Set<Variable> bodyVars = this.atomSet.getVariables();
			for(Term t : this.answerVars) {
				if(t.isVariable() && !bodyVars.contains(t)) {
					throw new ParseError("There is at least one variable in the answer list which does not appear in the query body.");
				}
			}
			this.createQuery(DefaultConjunctiveQueryFactory.instance().create(this.label, this.atomSet, this.answerVars));
			break;
			
		case NEG_CONSTRAINT:
			this.createNegConstraint(new DefaultNegativeConstraint(this.label,
					this.atomSet));
			break;
			
		case RULE: case DEFEASIBLE_RULE: case DEFEATER_RULE:
			if (this.atomSet2 == null) {
				// We are parsing the conjunction of the head of the rule
				this.atomSet2 = this.atomSet;
				this.atomSet = new LinkedListAtomSet();
			} else {
				// We are parsing the conjunctio nof the body of the rule
				this.createRule(this.label, this.atomSet,
						this.atomSet2, objectType);
			}
			break;
			
		case PREFERENCE_RULE:
			if(this.atomSet2 == null) {
				// We are parsing the preference head of the rule
				this.atomSet2 = new LinkedListAtomSet();
				this.atomSet2.add(this.pref);
			} else {
				// We are parsing the preference of the body of the rule
				this.createRule(this.label, this.atomSet, this.atomSet2, objectType);
			}
			break;
		
		case PREFERENCE:
			this.set.write(this.pref);
			break;
			
		case FACT:
			this.createAtomSet(this.atomSet);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * Creates a set of facts by grounding any existential variable that might exists.
	 * @param atomset a at of atoms representing a conjunction of facts
	 */
	protected void createAtomSet(InMemoryAtomSet atomset) {
		FreshVarSubstitution s = new FreshVarSubstitution(DlgpDefeasibleParser.freeVarGen);
		CloseableIteratorWithoutException<Atom> it = atomset.iterator();
		while (it.hasNext()) {
			Atom a = it.next();
			this.set.write(new FlexibleAtom(s.createImageOf(a)));
		}
	}
	
	
	/**
	 * Adds a query to the buffer
	 * @param query
	 */
	protected void createQuery(ConjunctiveQuery query) {
		this.set.write(query);
	}


	/**
	 * Adds a negative constraint to the buffer
	 * @param negativeConstraint
	 */
	protected void createNegConstraint(DefaultNegativeConstraint negativeConstraint) {
		this.set.write(negativeConstraint);
	}
	
	
	/**
	 * Creates a rule after a conjunction ends depending on the objectType
	 * @param label the label of the rule
	 * @param body a list of atoms for the body of the rule
	 * @param head a list of atoms for the head of the rule
	 * @param objectType the type of the rule
	 */
	protected void createRule(String label, LinkedListAtomSet body, LinkedListAtomSet head, OBJECT_TYPE objectType) {
		Object rule;
		
		switch(objectType) {
			case DEFEASIBLE_RULE:
				rule = this.factory.createDefeasibleRule(label, body, head); break;
			case DEFEATER_RULE:
				rule = this.factory.createDefeaterRule(label, body, head); break;
			case PREFERENCE_RULE:
				rule = this.factory.createPreferenceRule(label, body, head); break;
			default:
				rule = this.factory.createStrictRule(label, body, head);
		}
		this.set.write(rule);
	}

	
	
	
	
	
	/**
	 * Creates a predicate from a name (uri) and arity
	 * @param uri
	 * @param arity
	 * @return a predicate with name uri and arity arity
	 */
	private static Predicate createPredicate(Object uri, int arity) {
		return new Predicate(uri, arity);
	}

	/**
	 * Creates a constant from a name (uri)
	 * @param uri name of the constant
	 * @return a constant.
	 */
	private static Constant createConstant(Object uri) {
		return DefaultTermFactory.instance().createConstant(uri);
	}

	/**
	 * Creates a term or a constant depending on the type
	 * @param t an object representing a term or a constant
	 * @return a term
	 */
	private static Term createTerm(Object t) {
		if (t instanceof Term) {
			return (Term) t;
		} else {
			return createConstant(t);
		}
	}
}