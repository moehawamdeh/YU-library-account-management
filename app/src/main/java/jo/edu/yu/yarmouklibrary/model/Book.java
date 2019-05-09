package jo.edu.yu.yarmouklibrary.model;

import java.io.Serializable;
import java.util.Date;

public class Book extends Item implements Serializable {

    private Date mCheckOutDate;
    private Date mDueDate;
    private Date mReturnDate;
    private int mDaysLeft;

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public Long getDaysLeft() {
        long diff=  mDueDate.getTime()-new Date().getTime() ;
        return (diff / (1000 * 60 * 60 * 24)) +1;
    }

    public void setDaysLeft(int daysLeft) {
        mDaysLeft = daysLeft;
    }

    public Date getReturnDate() {
        return mReturnDate;
    }

    public void setReturnDate(Date returnDate) {
        mReturnDate = returnDate;
    }

    public Date getCheckOutDate() {
        return mCheckOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        mCheckOutDate = checkOutDate;
    }
}