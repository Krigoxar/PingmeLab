package com.pingme.ping.dtos;

import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import java.util.List;

public record UrlStats(ObservedUrl url, List<Observation> observations) {}
