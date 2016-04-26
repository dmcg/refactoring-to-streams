# refactoring-to-streams

Learn how to express your algorithms in Java 8 Streams.

Exercises and solutions by [Duncan McGregor](https://twitter.com/duncanmcg)
and [Nat Pryce](https://twitter.com/natpryce) used in their 
[SPA2015 workshop on programming with streams](http://www.spaconference.org/spa2015/sessions/session647.html).

Presented again at [ACCU 2016](http://accu.org/index.php/conferences/accu_conference_2016/accu2016_sessions#Refactoring_to_Streams)

## Description 

[Streams](https://docs.oracle.com/javase/tutorial/collections/streams/) and 
[lambda expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
introduced in Java 8 give programmers access to some advanced functional abstractions. 
In this exercises we look at how to refactor imperative code to take advantage of 
this style. We'll start by removing for loops and work our way through mapping and 
reducing to advanced parallelism.

### Refactoring Workflow

The general workflow while refactoring towards Streams and lambda expressions is like that:
Start by replacing the `Iterable` in the extended for loop with a `Stream`. Then use `forEach` to
execute the loop body. Use other functions like `map` or `filter` to remove chunks of code from 
the loop body in small steps. Finally use a `Collector` to collect the result of the stream.
See [Refactoring with Loops and Collection Pipelines](http://martinfowler.com/articles/refactoring-pipelines.html)
by Martin Fowler for several detailed examples of these refactoring steps.

## Things to cover

* [x] Converting mutation-and-for-loops
* [x] Performance
* [x] Parallelising
* [x] Filtering
* [x] Mapping
* [x] Reducing
* [x] Collecting
* [x] Partitioning and grouping
* [x] Generators (infinite streams, streams evaluate lazily)
* [x] Take (re. streams evaluate lazily)
* [x] Flatmap
* [x] Null vs Optional<T>
* [x] ForEach
* [x] Error handling and exceptions 

## Notes on Running the Workshop
This is a 150 minute workshop, 10 minutes intro, 2 hours of exercises and a conclusion.
Developers new to Java 8, streams and lambda expressions might need considerable more time, 
depending on how much support the facilitator gives. [Peter Kofler](https://twitter.com/codecopkofler) 
used the exercise in a Coding Dojo styled training and the group needed around six hours 
to complete all exercises. Participants were new to Java 8 and I let them explore the 
solutions on their own.

### Notes on the Exercises

Work through the exercises in `exercises` (`src/test/.../rts/exercises/Ex*.java`). 
The `solutions` source tree (`src/test/.../rts/solutions/Ex*.java`) gives our suggestions.

There is one failing test where new code has to be written. All other exercises contain
working code, which is written without making use of streams and lambda expressions.
Often there is more than one way to rewrite the code using the new language features. 
In this case just add more methods containing the alternative ways and mark them with
`@Way`. (That is the rationale behind naming the marker annotation `@Way` because it
shows different ways to do things.)

The `ExampleRunner`'s (`src/test/.../rts/runner/ExampleRunner.java`) job is to collect the 
methods marked `@Way` and create a test for each by feeding the returned function to all the 
JUnit test method(s) marked `@Test` as usual. A nasty side effect is that you can't rerun 
an individual test in IntelliJ, but that seems to be true for `@Theory`'s as well.

The final exercise is to refactor the `ExampleRunner` itself - our solution of that is also given.

### Feedback from Participants

* This was a great exercise.
* It was good to see the variations of the usage of lambda expressions.
* It helped to get an overview of what is available in Java 8 regarding streams and lambda expressions.
* We got a first understanding of streams, but we need more practice.
* Streams and lambda expressions are complicated (when you are new to it).

### License

(Cc Attribution Sharealike](http://creativecommons.org/licenses/by-sa/4.0/)

Please do run use this material to run the session yourself - let us know how it goes. If you want Duncan and Nat 
to come in and run it for you, we should talk!
