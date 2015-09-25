Where can I find a method to...

Create a stream:

 * a static factory method defined on the stream's interface (a new feature of Java 8)
 * a "plural" class of related functions. E.g. functions related to Banana instances will be defined in a class named Bananas.
 * The stream() method of a collection
 * StreamSupport.stream(..) for an iterator

Convert back to an iterable:

 * Iterable<T> t = streamOfT::iterator

Create a collector:

 * static factory method defined in Collectors. 
 
