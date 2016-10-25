Problem Statement - Conference Track Management

You are planning a big programming conference and have received many proposals which have passed the initial screen process but you're having trouble fitting them into the time constraints of the day -- there are so many possibilities! So you write a program to do it for you.

· The conference has multiple tracks each of which has a morning and afternoon session.
· Each session contains multiple talks.
· Morning sessions begin at 9am and must finish by 12 noon, for lunch.
· Afternoon sessions begin at 1pm and must finish in time for the networking event.
· The networking event can start no earlier than 4:00 and no later than 5:00.
· No talk title has numbers in it.
· All talk lengths are either in minutes (not hours) or lightning (5 minutes).
· Presenters will be very punctual; there needs to be no gap between sessions.

Note that depending on how you choose to complete this problem, your solution may give a different ordering or combination of talks into tracks. This is acceptable; you don’t need to exactly duplicate the sample output given here.

Test input :-
------------

Writing Fast Tests Against Enterprise Rails 60min
Overdoing it in Python 45min
Lua for the Masses 30min
Ruby Errors from Mismatched Gem Versions 45min
Common Ruby Errors 45min
Rails for Python Developers lightning
Communicating Over Distance 60min
Accounting-Driven Development 45min
Woah 30min
Sit Down and Write 30min
Pair Programming vs Noise 45min
Rails Magic 60min
Ruby on Rails: Why We Should Move On 60min
Clojure Ate Scala (on my project) 45min
Programming in the Boondocks of Seattle 30min
Ruby vs. Clojure for Back-End Development 30min
Ruby on Rails Legacy App Maintenance 60min
A World Without HackerNews 30min
User Interface CSS in Rails Apps 30min

Test output :-
-------------

Track 1:
09:00AM Writing Fast Tests Against Enterprise Rails 60min
10:00AM Communicating Over Distance 60min
11:00AM Rails Magic 60min
12:00PM Lunch
01:00PM Ruby on Rails: Why We Should Move On 60min
02:00PM Common Ruby Errors 45min
02:45PM Accounting-Driven Development 45min
03:30PM Pair Programming vs Noise 45min
04:15PM User Interface CSS in Rails Apps 30min
04:45PM Rails for Python Developers lightning
04:50PM Networking Event

Track 2:
09:00AM Ruby on Rails Legacy App Maintenance 60min
10:00AM Overdoing it in Python 45min
10:45AM Ruby Errors from Mismatched Gem Versions 45min
11:30AM Lua for the Masses 30min
12:00PM Lunch
01:00PM Clojure Ate Scala (on my project) 45min
01:45PM Woah 30min
02:15PM Sit Down and Write 30min
02:45PM Programming in the Boondocks of Seattle 30min
03:15PM Ruby vs. Clojure for Back-End Development 30min
03:45PM A World Without HackerNews 30min
04:15PM Networking Event


=======================================================================

Solution :


Here is the solution of this problem, Let me explain the algorithm first before proceeding for programming solution.


Algorithm :  Below are the step which used to schedule the talks to satisfy above condition.

/ **
  * 1. Read data from file and create a list of String.
  * 2. validate each string talk, check the time.
  * 3. sort the list of talks.
  * 4. find the possible days to schedule conference.
  * 5. find out the combination which can fill the first half (morning session total time 180 mins).
  * 6. find out the combination that can fill the evening sessions (180 >= totalSessionTime <= 240).
  * 7. check if any task remaining in the list if yes then try to fill all the eve session.
  *
  * ASSUMPTION :-
  * 1. This algorithm made to be consider we will not have any task which have time more than 240 mins(4 hrs maximum time for session).
  * 2. To initialize the object of Talk class its assumed that the time for Networking Event is 1 hr, however its not used any where else to schedule talks.
  */

Java Code :

/**
 * ConferenceManager : This class can be used to schedule talks from the given set of talks, either using file or as a list.
 * @author Rahul Chauhan
 */

package com.mywork.conference.model;

public class ConferenceManager {

    /**
     * Constructor for ConferenceManager.
     */
    public ConferenceManager() {
    }

    /**
     * public method to create and schedule conference.
     * @param fileName
     * @throws InvalidTalkException
     */
    public List<List<Talk>> scheduleConference(String fileName) throws Exception
    {
        List<String> talkList = getTalkListFromFile(fileName);
        return scheduleConference(talkList);
    }

    /**
     * public method to create and schedule conference.
     * @param talkList
     * @throws InvalidTalkException
     */
    public List<List<Talk>> scheduleConference(List<String> talkList) throws Exception
    {
        List<Talk> talksList = validateAndCreateTalks(talkList);
        return getScheduleConferenceTrack(talksList);
    }

    /**
     * Load talk list from input file.
     * @param fileName
     * @return
     * @throws InvalidTalkException
     */
    public List<String> getTalkListFromFile(String fileName) throws InvalidTalkException
    {
        List<String> talkList = new ArrayList<String>();
        try{
          // Open the file.
          FileInputStream fstream = new FileInputStream(fileName);
          // Get the object of DataInputStream
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine = br.readLine();
          //Read File Line By Line
          while (strLine != null)   {
            talkList.add(strLine);
            strLine = br.readLine();
          }
          //Close the input stream
          in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }

        return talkList;
    }

    /**
     * Validate talk list, check the time for talk and initilize Talk Object accordingly.
     * @param talkList
     * @throws Exception
     */
    private List<Talk> validateAndCreateTalks(List<String> talkList) throws Exception
    {
        // If talksList is null throw exception invaid list to schedule.
        if(talkList == null)
            throw new InvalidTalkException("Empty Talk List");

        List<Talk> validTalksList = new ArrayList<Talk>();
        int talkCount = -1;
        String minSuffix = "min";
        String lightningSuffix = "lightning";

        // Iterate list and validate time.
        for(String talk : talkList)
        {
            int lastSpaceIndex = talk.lastIndexOf(" ");
            // if talk does not have any space, means either title or time is missing.
            if(lastSpaceIndex == -1)
                throw new InvalidTalkException("Invalid talk, " + talk + ". Talk time must be specify.");

            String name = talk.substring(0, lastSpaceIndex);
            String timeStr = talk.substring(lastSpaceIndex + 1);
            // If title is missing or blank.
            if(name == null || "".equals(name.trim()))
                throw new InvalidTalkException("Invalid talk name, " + talk);
            // If time is not ended with min or lightning.
            else if(!timeStr.endsWith(minSuffix) && !timeStr.endsWith(lightningSuffix))
                throw new InvalidTalkException("Invalid talk time, " + talk + ". Time must be in min or in lightning");

            talkCount++;
            int time = 0;
            // Parse time from the time string .
            try{
                if(timeStr.endsWith(minSuffix)) {
                    time = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(minSuffix)));
                }
                else if(timeStr.endsWith(lightningSuffix)) {
                    String lightningTime = timeStr.substring(0, timeStr.indexOf(lightningSuffix));
                    if("".equals(lightningTime))
                        time = 5;
                    else
                        time = Integer.parseInt(lightningTime) * 5;
                }
            }catch(NumberFormatException nfe) {
                throw new InvalidTalkException("Unbale to parse time " + timeStr + " for talk " + talk);
            }

            // Add talk to the valid talk List.
            validTalksList.add(new Talk(talk, name, time));
        }

        return validTalksList;
    }

    /**
     * Schedule Conference tracks for morning and evening session.
     * @param talksList
     * @throws Exception
     */
    private List<List<Talk>> getScheduleConferenceTrack(List<Talk> talksList) throws Exception
    {
        // Find the total possible days.
        int perDayMinTime = 6 * 60;
        int totalTalksTime = getTotalTalksTime(talksList);
        int totalPossibleDays = totalTalksTime/perDayMinTime;

        // Sort the talkList.
        List<Talk> talksListForOperation = new ArrayList<Talk>();
        talksListForOperation.addAll(talksList);
        Collections.sort(talksListForOperation);

        // Find possible combinations for the morning session.
        List<List<Talk>> combForMornSessions = findPossibleCombSession(talksListForOperation, totalPossibleDays, true);

        // Remove all the scheduled talks for morning session, from the operationList.
        for(List<Talk> talkList : combForMornSessions) {
            talksListForOperation.removeAll(talkList);
        }

        // Find possible combinations for the evening session.
        List<List<Talk>> combForEveSessions = findPossibleCombSession(talksListForOperation, totalPossibleDays, false);

        // Remove all the scheduled talks for evening session, from the operationList.
        for(List<Talk> talkList : combForEveSessions) {
            talksListForOperation.removeAll(talkList);
        }

        // check if the operation list is not empty, then try to fill all the remaining talks in evening session.
        int maxSessionTimeLimit = 240;
        if(!talksListForOperation.isEmpty()) {
            List<Talk> scheduledTalkList = new ArrayList<Talk>();
            for(List<Talk> talkList : combForEveSessions) {
                int totalTime = getTotalTalksTime(talkList);

                for(Talk talk : talksListForOperation) {
                    int talkTime = talk.getTimeDuration();

                    if(talkTime + totalTime <= maxSessionTimeLimit) {
                        talkList.add(talk);
                        talk.setScheduled(true);
                        scheduledTalkList.add(talk);
                    }
                }

                talksListForOperation.removeAll(scheduledTalkList);
                if(talksListForOperation.isEmpty())
                    break;
            }
        }

        // If operation list is still not empty, its mean the conference can not be scheduled with the provided data.
        if(!talksListForOperation.isEmpty())
        {
            throw new Exception("Unable to schedule all task for conferencing.");
        }

        // Schedule the day event from morning session and evening session.
        return getScheduledTalksList(combForMornSessions, combForEveSessions);
    }

    /**
     * Find possible combination for the session.
     * If morning session then each session must have total time 3 hr.
     * if evening session then each session must have total time greater then 3 hr.
     * @param talksListForOperation
     * @param totalPossibleDays
     * @param morningSession
     * @return
     */
    private List<List<Talk>> findPossibleCombSession(List<Talk> talksListForOperation, int totalPossibleDays, boolean morningSession)
    {
        int minSessionTimeLimit = 180;
        int maxSessionTimeLimit = 240;

        if(morningSession)
            maxSessionTimeLimit = minSessionTimeLimit;

        int talkListSize = talksListForOperation.size();
        List<List<Talk>> possibleCombinationsOfTalks = new ArrayList<List<Talk>>();
        int possibleCombinationCount = 0;

        // Loop to get combination for total possible days.
        // Check one by one from each talk to get possible combination.
        for(int count = 0; count < talkListSize; count++) {
            int startPoint = count;
            int totalTime = 0;
            List<Talk> possibleCombinationList = new ArrayList<Talk>();

            // Loop to get possible combination.
            while(startPoint != talkListSize) {
                int currentCount = startPoint;
                startPoint++;
                Talk currentTalk = talksListForOperation.get(currentCount);
                if(currentTalk.isScheduled())
                    continue;
                int talkTime = currentTalk.getTimeDuration();
                // If the current talk time is greater than maxSessionTimeLimit or
                // sum of the current time and total of talk time added in list  is greater than maxSessionTimeLimit.
                // then continue.
                if(talkTime > maxSessionTimeLimit || talkTime + totalTime > maxSessionTimeLimit) {
                    continue;
                }

                possibleCombinationList.add(currentTalk);
                totalTime += talkTime;

                // If total time is completed for this session than break this loop.
                if(morningSession) {
                    if(totalTime == maxSessionTimeLimit)
                        break;
                }else if(totalTime >= minSessionTimeLimit)
                    break;
            }

            // Valid session time for morning session is equal to maxSessionTimeLimit.
            // Valid session time for evening session is less than or eqaul to maxSessionTimeLimit and greater than or equal to minSessionTimeLimit.
            boolean validSession = false;
            if(morningSession)
                validSession = (totalTime == maxSessionTimeLimit);
            else
                validSession = (totalTime >= minSessionTimeLimit && totalTime <= maxSessionTimeLimit);

            // If session is valid than add this session in the possible combination list and set all added talk as scheduled.
            if(validSession) {
                possibleCombinationsOfTalks.add(possibleCombinationList);
                for(Talk talk : possibleCombinationList){
                    talk.setScheduled(true);
                }
                possibleCombinationCount++;
                if(possibleCombinationCount == totalPossibleDays)
                    break;
            }
        }

        return possibleCombinationsOfTalks;
    }

    /**
     * Print the scheduled talks with the expected text msg.
     * @param combForMornSessions
     * @param combForEveSessions
     */
    private List<List<Talk>> getScheduledTalksList(List<List<Talk>> combForMornSessions, List<List<Talk>> combForEveSessions)
    {
        List<List<Talk>> scheduledTalksList = new ArrayList<List<Talk>>();
        int totalPossibleDays = combForMornSessions.size();

        // for loop to schedule event for all days.
        for(int dayCount = 0; dayCount < totalPossibleDays; dayCount++) {
            List<Talk> talkList = new ArrayList<Talk>();

            // Create a date and initialize start time 09:00 AM.
            Date date = new Date( );
            SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mma ");
            date.setHours(9);
            date.setMinutes(0);
            date.setSeconds(0);

            int trackCount = dayCount + 1;
            String scheduledTime = dateFormat.format(date);

            System.out.println("Track " + trackCount + ":");

            // Morning Session - set the scheduled time in the talk and get the next time using time duration of current talk.
            List<Talk> mornSessionTalkList = combForMornSessions.get(dayCount);
            for(Talk talk : mornSessionTalkList) {
                talk.setScheduledTime(scheduledTime);
                System.out.println(scheduledTime + talk.getTitle());
                scheduledTime = getNextScheduledTime(date, talk.getTimeDuration());
                talkList.add(talk);
            }

            // Scheduled Lunch Time for 60 mins.
            int lunchTimeDuration = 60;
            Talk lunchTalk = new Talk("Lunch", "Lunch", 60);
            lunchTalk.setScheduledTime(scheduledTime);
            talkList.add(lunchTalk);
            System.out.println(scheduledTime + "Lunch");

            // Evening Session - set the scheduled time in the talk and get the next time using time duration of current talk.
            scheduledTime = getNextScheduledTime(date, lunchTimeDuration);
            List<Talk> eveSessionTalkList = combForEveSessions.get(dayCount);
            for(Talk talk : eveSessionTalkList) {
                talk.setScheduledTime(scheduledTime);
                talkList.add(talk);
                System.out.println(scheduledTime + talk.getTitle());
                scheduledTime = getNextScheduledTime(date, talk.getTimeDuration());
            }

            // Scheduled Networking Event at the end of session, Time duration is just to initialize the Talk object.
            Talk networkingTalk = new Talk("Networking Event", "Networking Event", 60);
            networkingTalk.setScheduledTime(scheduledTime);
            talkList.add(networkingTalk);
            System.out.println(scheduledTime + "Networking Event\n");
            scheduledTalksList.add(talkList);
        }

        return scheduledTalksList;
    }

    /**
     * To get total time of talks of the given list.
     * @param talksList
     * @return
     */
    public static int getTotalTalksTime(List<Talk> talksList)
    {
        if(talksList == null || talksList.isEmpty())
            return 0;

        int totalTime = 0;
        for(Talk talk : talksList) {
            totalTime += talk.timeDuration;
        }
        return totalTime;
    }

    /**
     * To get next scheduled time in form of String.
     * @param date
     * @param timeDuration
     * @return
     */
    private String getNextScheduledTime(Date date, int timeDuration)
    {
        long timeInLong  = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mma ");

        long timeDurationInLong = timeDuration * 60 * 1000;
        long newTimeInLong = timeInLong + timeDurationInLong;

        date.setTime(newTimeInLong);
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * Main method to execute program.
     * @param args
     */
    public static void main(String[] args) {
        String fileName = "D:\\Jdeveloper\\mywork\\ConferenceTrackManagement\\src\\com\\mywork\\conference\\data\\ConferenceData.txt";
        ConferenceManager conferenceManager = new ConferenceManager();
        try{
            conferenceManager.scheduleConference(fileName);
        }catch(InvalidTalkException ite) {
            ite.printStackTrace();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * class Talk, to store and retrive information about talk.
     * implements comparabe to sort talk on the basis of time duration.
     */
    public static class Talk implements Comparable{
        String title;
        String name;
        int timeDuration;
        boolean scheduled = false;
        String scheduledTime;

        /**
         * Constructor for Talk.
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
         * @param scheduled
         */
        public void setScheduled(boolean scheduled) {
            this.scheduled = scheduled;
        }

        /**
         * To get flag scheduled.
         * If talk scheduled then returns true, else false.
         * @return
         */
        public boolean isScheduled() {
            return scheduled;
        }

        /**
         * Set scheduled time for the talk. in format - hr:min AM/PM.
         * @param scheduledTime
         */
        public void setScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        /**
         * To get scheduled time.
         * @return
         */
        public String getScheduledTime() {
            return scheduledTime;
        }

        /**
         * To get time duration  for the talk.
         * @return
         */
        public int getTimeDuration() {
            return timeDuration;
        }

        /**
         * To get the title of the talk.
         * @return
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sort data in decending order.
         * @param obj
         * @return
         */
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

}


/*
 * Exception class for invalid Talk.
 *  @author Rahul Chauhan
 */
package com.mywork.conference.exception;

public class InvalidTalkException extends Exception{
    @SuppressWarnings("compatibility:-140331834793898838")
    private static final long serialVersionUID = 1L;

    public InvalidTalkException(String msg) {
        super(msg);
    }

}