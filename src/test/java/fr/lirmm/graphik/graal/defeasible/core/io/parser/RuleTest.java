package fr.lirmm.graphik.graal.defeasible.core.io.parser;

import org.junit.Assert;
import org.junit.Test;

import fr.lirmm.graphik.graal.api.io.ParseException;
import fr.lirmm.graphik.graal.defeasible.core.io.DlgpDefeasibleParser;
import fr.lirmm.graphik.graal.defeasible.core.rules.StrictRule;

public class RuleTest {
	@Test
	public void shouldConsiderTwoRulesEqualIfSameBodySameHead1() throws ParseException {
		StrictRule r1 = (StrictRule) DlgpDefeasibleParser.parseRule("q(Z), p(X) <- u(Y), t(X).");
		StrictRule r2 = (StrictRule) DlgpDefeasibleParser.parseRule("q(Z), p(X) <- u(Y), t(X).");
		Assert.assertEquals(r1,r2);
	}
	
	@Test
	public void shouldConsiderTwoRulesEqualIfSameBodySameHead2() throws ParseException {
		StrictRule r1 = (StrictRule) DlgpDefeasibleParser.parseRule("[r1] q(Z), p(X) <- u(Y), t(X).");
		StrictRule r2 = (StrictRule) DlgpDefeasibleParser.parseRule("[r2] q(Z), p(X) <- u(Y), t(X).");
		Assert.assertEquals(r1,r2);
	}
	
	@Test
	public void shouldConsiderTwoRulesEqualIfSameBodySameHead3() throws ParseException {
		StrictRule r1 = (StrictRule) DlgpDefeasibleParser.parseRule("[r1] q(Z), p(X) <- u(Y), t(X).");
		StrictRule r2 = (StrictRule) DlgpDefeasibleParser.parseRule("[r2] p(X), q(Z) <- u(Y), t(X).");
		Assert.assertEquals(r1,r2);
	}
	
	@Test
	public void shouldConsiderTwoRulesEqualIfSameBodySameHead4() throws ParseException {
		StrictRule r1 = (StrictRule) DlgpDefeasibleParser.parseRule("[r1] q(Z), p(X) <- u(Y), t(X).");
		StrictRule r2 = (StrictRule) DlgpDefeasibleParser.parseRule("[r2] q(Z), p(X) <- t(X),u(Y).");
		Assert.assertEquals(r1,r2);
	}
	
	/*
	@Test
	public void shouldConsiderTwoRulesEqualIfSameBodySameHead() throws ParseException {
		StrictRule r1 = (StrictRule) DlgpDefeasibleParser.parseRule("[r1] p(A), q(I) <- t(A), u(R).");
		StrictRule r2 = (StrictRule) DlgpDefeasibleParser.parseRule("[r2] q(Z), p(X) <- u(Y), t(X).");
		Assert.assertEquals(r1,r2);
	}
	*/
}
