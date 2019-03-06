package fr.lirmm.graphik.graal.defeasible.core;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import fr.lirmm.graphik.graal.api.core.AtomSetException;
import fr.lirmm.graphik.graal.api.core.Rule;
import fr.lirmm.graphik.graal.defeasible.core.atoms.FlexibleAtom;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeasibleRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.DefeaterRule;
import fr.lirmm.graphik.graal.defeasible.core.rules.StrictRule;
import fr.lirmm.graphik.util.stream.IteratorException;

public class AuthorshipTest {
	@Test
	public void shouldGiveAllFactsAndRulesTheAuthorshipOfTheKnowledgeBase() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase("Test");
		kb.add("p(a). q(X) <= p(X). q(X) <- p(X). q(X) <~ p(X).");
		HashSet<String> authors = ((FlexibleAtom) kb.getFacts().iterator().next()).getAuthors();
		if(!authors.contains("Test")) Assert.fail("Authorship problem with facts");
		
		for(Rule r: kb.getStrictRules()) {
			authors = ((StrictRule) r).getAuthors();
			if(!authors.contains("Test")) Assert.fail("Authorship problem with Strict Rules");
		}
		for(Rule r: kb.getDefeasibleRules()) {
			authors = ((DefeasibleRule) r).getAuthors();
			if(!authors.contains("Test")) Assert.fail("Authorship problem with Defeasible Rules");
		}
		for(Rule r: kb.getDefeaterRules()) {
			authors = ((DefeaterRule) r).getAuthors();
			if(!authors.contains("Test")) Assert.fail("Authorship problem with Defeater Rules");
		}
		
	}
}
