import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class Utility {


    /**
     * Public static method used to convert a String into a String array by looking for a provided delimeter
     *
     * @param string
     * @param delimeter
     * @return
     */
    public static String[] explode(String string, String delimeter)
    {
        // Before exploding, replace problematic characters
        String cleanRecord1 = string.replace("\"" + delimeter, "|");
        String cleanRecord2 = cleanRecord1.replace("\"", "");

        // Explode the Record into individual elements
        String[] recordArray = cleanRecord2.split("\\|");

        return recordArray;

    }

    /**
     * Public static method used to convert a String array into a delimited string by a provided delimeter
     *
     * @param stringArray
     * @param delimeter
     * @return
     */
    public static String implode(String[] stringArray, String delimeter)
    {

        String record = "";

        for(String i : stringArray) {

            record += "\"" + i + "\",";

        }

        return record.substring(0, record.length() - 1); // You would actually want to do some type of rtrim method, but removing the last character, in this case a "," works too

    }

    /**
     * Public static method used to convert a human readable datestamp to epoch timestamp
     *
     * @param datetime
     * @return
     * @throws Exception
     */
    public static Long convertDatestampToEpoch(String datetime) throws Exception
    {

        // Per https://stackoverflow.com/questions/6687433/convert-a-date-format-in-epoch

        // Note: Dattime is expected in the following format
        // String datetime = "12/15/2017 23:11:52";

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = df.parse(datetime);
        return  date.getTime();

    }

    /**
     * Public static method used to convert a epoch timestamp to a human readable datestamp
     *
     * @param epoch
     * @return
     */
    public static String convertEpochToDatestamp(Long epoch)
    {
        // Per https://stackoverflow.com/questions/7740972/convert-epoch-time-to-date

        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return format.format(date);

    }
}
