package com.pingme.ping.dtos;

import java.util.*;
import com.pingme.ping.daos.model.*;

public record URLStats(ObservedURL url, List<Observation> observations) { }
