package jo.edu.yu.yarmouklibrary.model.data;

import java.util.Date;

import jo.edu.yu.yarmouklibrary.model.OverdueStatus;

public class LibraryPolicy {
    private final static int THREE_DAYS=3*24*60*60*1000;
    private final static long YEAR=365*24*60*60*1000L;
    public static long findDays(Date dueDate, Date returnDate){
        return (dueDate.getTime()-returnDate.getTime())/(24*60*60*1000);
    }
    public static OverdueStatus getOverdueStatus(Date dueDate, Date returnDate){
        long diff=dueDate.getTime()-returnDate.getTime();
        if(diff <=THREE_DAYS)
            return OverdueStatus.SAFE_LATE;
        else if(diff>YEAR)
            return OverdueStatus.LOST;
        else return OverdueStatus.LATE;
    }
    public static OverdueStatus getOverdueStatus(long days){
        if(days <=3)
            return OverdueStatus.SAFE_LATE;
        else if(days>365)
            return OverdueStatus.LOST;
        else return OverdueStatus.LATE;
    }
//    public static String getOverdueString(Context context, OverdueStatus status){
//        if(status==OverdueStatus.LATE)
//            return context.getResources().getQuantityString(R.string.)
//    }
}
