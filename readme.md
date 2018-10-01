[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.lirmm.graphik/graal-defeasible-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.lirmm.graphik/graal-defeasible-core)

# Graal Defeasible Core
This is the parser module for Graal, it can parse strict, defeasible, and defeater rules along with preferences on rules and on alternatives using DLGP fromat. 

## Getting Started
You can either import this module using Maven Central (recommended). Add the following to your pom.xml:
```
<dependency>
      <groupId>fr.lirmm.graphik</groupId>
      <artifactId>graal-defeasible-core</artifactId>
      <version>0.0.2</version>
</dependency>
```

Or using Github then adding it to your project:
```
> git clone https://github.com/hamhec/graal-defeasible-core.git
```
### Example

```
penguin(kowalski). # strict fact.
bird(tweety) <= . # defeasible fact.
brokenWing(tweety) <= . # defeasible fact.

# penguins definetly are birds who do not fly
[r1] notFly(X), bird(X) <- penguin(X). # strict rule, the label [r1] is optional

# birds generally fly
[r2] fly(X) <= bird(X). # defeasible rule 

# birds with broken wings are not able to fly
[r3] notFly(X) <~ bird(X), brokenWings(X). # defeater rule

# r3 is preferred to r2
r3 >> r2. # preference on rules.

# one cannot fly and notFly at the same time (it leads to bottom '!' )
! :- fly(X), notFly(X). # Negative Constraint to express negation.
```

## Authors

* **Abdelraouf Hecham** - *Developement and Maintenance* - [Hamhec](https://github.com/hamhec)
* **Clement Sipieter** - *Initial work* - [6pi](https://github.com/sipi)

## License

This project is licensed under CeCILL 2.1 - see the [LICENSE](LICENSE) file for details
