package jo.edu.yu.yarmouklibrary.model;

import java.util.Date;

public class Transaction extends Item
{
    private Book mBook;
    private String mBarCode;
    private String mTitle;
    private String mAuthor;
    private String mType;
    private Date mTransactionDate;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public Date getTransactionDate() {
        return mTransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        mTransactionDate = transactionDate;
    }
}
