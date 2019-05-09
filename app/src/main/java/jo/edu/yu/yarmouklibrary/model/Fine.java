package jo.edu.yu.yarmouklibrary.model;
import java.util.Date;

public class Fine {
    public Fine() {
    }

    public void setBookTitle(String bookTitle) {
        mBookTitle = bookTitle;
    }
    private Date mReturnDate;
    private String mBookTitle;
    private String Barcode;
    private String mType;
    private Date mDue;
    private float mValue;

    public Date getDue() {
        return mDue;
    }
    public void setDue(Date due) {
        mDue=due;
    }
    public Date getReturnDate() {
        return mReturnDate;
    }
    public void setReturnDate(Date returnDate) {
        mReturnDate = returnDate;
    }
    public String getBookTitle() {
        return mBookTitle;
    }
    public String getBarcode() {
        return Barcode;
    }
    public void setBarcode(String barcode) {
        Barcode = barcode;
    }
    public float getValue(){
//        long diff = mReturnDate.getTime()-mDue.getTime();
//        long seconds = diff / 1000;
//        long minutes = seconds / 60;
//        long hours = minutes / 60;
//        long days = hours / 24;
//        return Math.abs((float)(days*0.100));
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
