package org.batchu.conferencetaskmanagement;

/**
 * Created by pbatchu on 10/24/2016.
 */
/**
 * class Talk, to store and retrive information about talk.
 * implements comparabe to sort talk on the basis of time duration.
 */
public class Talk implements Comparable {
    String title;
    String name;
    int timeDuration;
    boolean scheduled = false;
    String scheduledTime;

    /**
     * Constructor for Talk.
     *
     * @param title
     * @param name
     * @param time
     */
    public Talk(String title, String name, int time) {
        this.title = title;
        this.name = name;
        this.timeDuration = time;
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
    public int getTimeDuration() {
        return timeDuration;
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
        if(this.timeDuration > talk.timeDuration)
            return -1;
        else if(this.timeDuration < talk.timeDuration)
            return 1;
        else
            return 0;
    }
}
