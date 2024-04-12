package com.pingme.ping.dtos;

/**
 * This code snippet is defining a Java record class named `CategoryName` with a single component
 * `name` of type `String`. Records were introduced in Java 14 as a preview feature and provide a
 * concise way to declare classes that are transparent holders for immutable data. In this case, the
 * `CategoryName` record class is essentially a data transfer object (DTO) representing a category
 * name. The `record` keyword automatically generates the necessary methods such as constructor,
 * accessor methods, `equals()`, `hashCode()`, and `toString()` based on the components defined in
 * the record.
 */
public record CategoryName(String name) {}
