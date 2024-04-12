package com.pingme.ping.dtos;

import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import java.util.List;

/**
 * This code snippet is defining a Java record class named `UrlStats`. A record class is a special
 * type of class introduced in Java 14 that is used to model immutable data. In this case, the
 * `UrlStats` record class has two components: an `ObservedUrl` object named `url` and a `List` of
 * `Observation` objects named `observations`.
 */
public record UrlStats(ObservedUrl url, List<Observation> observations) {}
