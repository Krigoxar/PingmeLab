package com.pingme.ping.dtos;

/**
 * This code snippet is defining a Java record class named `NewURL` with a single component `url` of
 * type `String`. Records were introduced in Java 14 as a more concise way to declare classes that
 * are meant to be used as data carriers. In this case, the `NewURL` record class is essentially a
 * data transfer object (DTO) that represents a new URL. The `url` component is initialized through
 * the constructor, and records automatically generate useful methods like `equals()`, `hashCode()`,
 * and `toString()` based on the components defined in the record.
 */
public record NewUrl(String url) {}
