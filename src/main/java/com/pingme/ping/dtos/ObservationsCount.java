package com.pingme.ping.dtos;

/**
 * This code snippet is defining a Java record class named `ObservationsCount`. Records were
 * introduced in Java 14 as a more concise way to declare classes that are meant to be simple data
 * carriers. In this case, the record has two components: a `Long` field named `count` and a
 * `String` field named `url`. The record class automatically generates the constructor, accessor
 * methods, `equals()`, `hashCode()`, and `toString()` methods based on the components provided in
 * the constructor. This makes it easier to create simple data-holding classes without writing
 * boilerplate code.
 */
public record ObservationsCount(Long count, String url) {}
