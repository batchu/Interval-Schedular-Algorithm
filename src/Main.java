import org.batchu.conferencetaskmanagement.ConferenceManager;
import org.batchu.conferencetaskmanagement.InvalidTalkException;

public class Main {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\me\\Documents\\Interval-Schedular-Algorithm\\input.txt";
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
}
