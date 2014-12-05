package org.icedog.common.web.controller;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

/**
 * Created by ice on 14-9-24.
 */
public class DemoJob implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    Map data = jobExecutionContext.getJobDetail().getJobDataMap();
    System.out.println("hi," + data.get("name"));
  }
}
