package org.batchu.conferencetaskmanagement;

/**
 * Created by pbatchu on 10/24/2016.
 */
/*
Model for each lecture/talk
 */
public class Talk implements Comparable {
    String title;
    String name;
    int duration;
    boolean scheduled = false;
    String scheduledTime;


    public Talk(String title, String name, int time) {
        this.title = title;
        this.name = name;
        this.duration = time;
    }

    /**
     * To set the flag scheduled.
     *
     * @param scheduled
     */
    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * To get flag scheduled.
     * If talk scheduled then returns true, else false.
     *
     * @return
     */
    public boolean isScheduled() {
        return scheduled;
    }

    /**
     * Set scheduled time for the talk. in format - hr:min AM/PM.
     *
     * @param scheduledTime
     */
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * To get scheduled time.
     *
     * @return
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     * To get time duration  for the talk.
     *
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * To get the title of the talk.
     *
     * @return
     */
    public String getTitle() {
        return title;
    }
    @Override
    public int compareTo(Object obj)
    {
        Talk talk = (Talk)obj;
        if(this.duration > talk.duration)
            return -1;
        else if(this.duration < talk.duration)
            return 1;
        else
            return 0;
    }
}
