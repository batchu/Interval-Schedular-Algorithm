import org.batchu.conferencetaskmanagement.InvalidTalkException;
import org.batchu.conferencetaskmanagement.Talk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public List<String> getTalkListFromFile(String fileName) throws InvalidTalkException
    {
        List<String> talkList = new ArrayList<String>();
        FileInputStream fstream=null;
        try{
            // Open the file.
            fstream = new FileInputStream(fileName);
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
        }finally {
            try {
                fstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return talkList;
    }

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

}
