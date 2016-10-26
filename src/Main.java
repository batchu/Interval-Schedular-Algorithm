import org.batchu.conferencetaskmanagement.IntervalSchedulerUtility;
import org.batchu.conferencetaskmanagement.ConferenceSchedularException;

public class Main {

    public static void main(String[] args) {
        //Location of the input data file - Update as necessary
        String fileName = "C:\\Users\\me\\Documents\\Interval-Schedular-Algorithm\\input.txt";
        IntervalSchedulerUtility intervalSchedulerUtility = new IntervalSchedulerUtility();
        try{
            intervalSchedulerUtility.scheduleConference(fileName);
        }catch(ConferenceSchedularException ite) {
            ite.printStackTrace();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
