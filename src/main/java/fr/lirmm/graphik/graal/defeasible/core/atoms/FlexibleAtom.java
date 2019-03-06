package fr.lirmm.graphik.graal.defeasible.core.atoms;

import java.util.HashSet;
import java.util.List;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.Constant;
import fr.lirmm.graphik.graal.api.core.Predicate;
import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.core.DefaultAtom;
import fr.lirmm.graphik.graal.defeasible.core.Authorable;

@SuppressWarnings("serial") // We will not need to serialize atoms
public class FlexibleAtom extends DefaultAtom implements Authorable {
	private HashSet<String> authors;
	
	/**
	 * Flexible Atom constructor from a predicate and an array of terms
	 * @param predicate a predicate
	 * @param terms an array of terms
	 */
	public FlexibleAtom(Predicate predicate, Term[] terms) {
		super(predicate, terms);
	}

	/**
	 * Flexible Atom constructor from a predicate and a list of terms
	 * @param predicate a predicate
	 * @param terms a list of terms
	 */
	public FlexibleAtom(Predicate predicate, List<Term> terms) {
		super(predicate, terms);
	}
	
	/**
	 * Flexible Atom constructor from a predicate and two terms
	 * @param predicate a predicate
	 * @param term1 first term
	 * @param term2 second term
	 */
	public FlexibleAtom(Predicate predicate, Term term1, Term term2) {
		super(predicate, term1, term2);
	}
	
	/**
	 * Creates a Flexible atom with no terms
	 * @param predicate a perdicate
	 */
	public FlexibleAtom(Predicate predicate) {
		super(predicate);
	}

	/**
	 * Creates a Flexible atom from another atom
	 * @param atom an atom
	 */
	public FlexibleAtom(Atom atom) {
		super(atom);
	}
	

	/**
	 * Creates a 1 ary predicate with a constant
	 * @param predicate a predicate
	 * @param constant a constant
	 */
	public FlexibleAtom(Predicate predicate, Constant constant) {
		super(predicate, constant);
	}

	
	public HashSet<String> getAuthors() {
		return authors;
	}

	public void setAuthors(HashSet<String> authors) {
		this.authors = authors;
	}

	/**
	 * Displays the atom without the predicate arity
	 * @param sb a string builder
	 */
	@Override
	public void appendTo(StringBuilder sb) {
		sb.append(this.getPredicate().getIdentifier().toString());
		sb.append('(');
		boolean bool = false;
		for (Term term : this) {
			if (bool)
				sb.append(',');
			sb.append(term.toString());
			bool = true;
		}
		sb.append(')');
	}
}
