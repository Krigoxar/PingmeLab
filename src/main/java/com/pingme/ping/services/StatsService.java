package com.pingme.ping.services;

import com.pingme.ping.components.HourlyCheckTask;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.stereotype.Service;

/**
 * The `StatsService` class in Java periodically checks the reachability of observed URLs and saves
 * the observation results.
 */
@Service
public class StatsService {

  private Timer timer;
  private TimerTask hourlyTask;

  /**
   * This code snippet is from a Java class `StatsService` that is responsible for periodically
   * checking the reachability of observed URLs and saving the observation results. Let's break down
   * the important parts of the code:
   */
  public StatsService(HourlyCheckTask hourlyTask) {
    this.hourlyTask = hourlyTask;
    this.timer = new Timer();
    startCorutine();
  }

  /**
   * The `startCorutine` function uses a Timer to periodically check the reachability of observed
   * URLs and save the observations in a repository.
   */
  public void startCorutine() {
    timer.schedule(hourlyTask, 0L, 1000 * 60 * 5L);
  }
}
