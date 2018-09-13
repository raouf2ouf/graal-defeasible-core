package fr.lirmm.graphik.graal.defeasible.core.io.parser;

import org.junit.Assert;
import org.junit.Test;

import fr.lirmm.graphik.graal.api.io.ParseException;
import fr.lirmm.graphik.graal.defeasible.core.LogicalObjectsFactory;
import fr.lirmm.graphik.graal.defeasible.core.io.DlgpDefeasibleParser;
import fr.lirmm.graphik.graal.defeasible.core.preferences.AlternativePreference;
import fr.lirmm.graphik.graal.defeasible.core.preferences.RulePreference;

public class DlgpDefeasibleParserTest {
	
//	/* Atom */
//	@Test
//	public void ShouldReturnAtom() throws ParseException {
//		Atom a = DlgpDefeasibleParser.parseAtom("p(a).");
//		if(a == null) Assert.fail("No atom parsed");
//		Assert.assertEquals("p(a)", a.toString());
//	}
//	@Test
//	public void ShouldReturnAtomSet() throws IteratorException {
//		CloseableIterator<Atom> it = DlgpDefeasibleParser.parseAtomSet("p(a), q(b).");
//		if(!it.hasNext()) {
//			Assert.fail("Did not parse any atom");
//			return;
//		}
//		Atom atom1 = it.next();
//		if(!it.hasNext()) {
//			Assert.fail("Parsed only one atom");
//			return;
//		}
//		Atom atom2 = it.next();
//		Assert.assertTrue(atom1.toString().equals("p(a)") && atom2.toString().equals("q(b)"));
//	}
//	@Test
//	public void ShouldThrowParseExceptionAtom1() {
//		try {
//			DlgpDefeasibleParser.parseAtom("p(a)");
//			fail("Did not throw exception when Atom does not end with .");
//		} catch (ParseException e) {
//		}
//	}
//	@Test
//	public void ShouldThrowParseExceptionAtom2() {
//		try {
//			DlgpDefeasibleParser.parseAtom("p(a.");
//			fail("Did not throw exception when Atom was malformed");
//		} catch (ParseException e) {
//		}
//	}
	
	
	/* Preference */
	@Test
	public void ShouldParseAlternativePreferenceBetweenConstants() throws ParseException {
		AlternativePreference pref = DlgpDefeasibleParser.parseAlternativePreference("entrecote > indien.");
		Assert.assertEquals(LogicalObjectsFactory.instance().getAlternativePreferencePredicate().getIdentifier().toString() + "(entrecote,indien)", pref.toString());
	}
	
	@Test
	public void ShouldParseRulePreferenceBetweenConstants() throws ParseException {
		RulePreference pref = DlgpDefeasibleParser.parseRulePreference("r1 >> r2.");
		Assert.assertEquals(LogicalObjectsFactory.instance().getRulePreferencePredicate().getIdentifier().toString() + "(r1,r2)", pref.toString());
	}
	
//	/* Strict Rules */
//	@Test
//	public void ShouldReturnStrictRule() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(X) :- q(X).");
//		Assert.assertTrue(rule != null);
//	}
//	@Test
//	public void ShouldReturnStrictRule2() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(X) <- q(X).");
//		Assert.assertTrue(rule != null);
//	}
//	
//	/* Defeasible Rules */
//	@Test
//	public void ShouldReturnDefeasibleRule() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(X) := q(X).");
//		Assert.assertTrue((rule != null) && rule instanceof DefeasibleRule);
//	}
//	@Test
//	public void ShouldReturnDefeasibleRule2() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(X) <= q(X).");
//		Assert.assertTrue((rule != null) && rule instanceof DefeasibleRule);
//	}
//	@Test
//	public void ShouldReturnDefeasibleRuleWithEmptyBody() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(a) := .");
//		Assert.assertTrue((rule != null) && (rule instanceof DefeasibleRule) && (rule.getBody().isEmpty()));
//	}
//	@Test
//	public void ShouldReturnDefeasibleRuleWithEmptyBody2() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(a) <= .");
//		Assert.assertTrue((rule != null) && (rule instanceof DefeasibleRule) && (rule.getBody().isEmpty()));
//	}
//	
//	/* DefeaterRules */
//	@Test
//	public void ShouldReturnDefeaterRule() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(X) :~ q(X).");
//		Assert.assertTrue((rule != null) && rule instanceof DefeaterRule);
//	}
//	@Test
//	public void ShouldReturnDefeaterRule2() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(X) <~ q(X).");
//		Assert.assertTrue((rule != null) && rule instanceof DefeaterRule);
//	}
//	@Test
//	public void ShouldReturnDefeaterRuleWithEmptyBody() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(a) :~ .");
//		Assert.assertTrue((rule != null) && (rule instanceof DefeaterRule) && (rule.getBody().isEmpty()));
//	}
//	@Test
//	public void ShouldReturnDefeaterRuleWithEmptyBody2() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("p(a) <~ .");
//		Assert.assertTrue((rule != null) && (rule instanceof DefeaterRule) && (rule.getBody().isEmpty()));
//	}
//	
//	
//	@Test
//	public void ShouldParseRuleLabel() throws ParseException {
//		Rule rule = DlgpDefeasibleParser.parseRule("[r1] p(X) :- q(X).");
//		Assert.assertTrue(rule.getLabel().equals("r1"));
//	}
//	
//	/* Preference Rules */
//	@Test
//	public void ShouldReturnPreference() throws ParseException {
//		Preference pref = DlgpDefeasibleParser.parsePreference("[r1] > [r2].");
//		Assert.assertTrue((pref != null) && pref.getSuperior().equals("r1") && pref.getInferior().equals("r2"));
//	}
//	
//	@Test
//	public void ShouldThrowExceptionIfPreferenceWithNullLabel() {
//		try {
//			DlgpDefeasibleParser.parsePreference(" > [r2].");
//			fail("Did not throw exception when Preference is with Null labels");
//		} catch (ParseException e) {
//		}
//	}
}
