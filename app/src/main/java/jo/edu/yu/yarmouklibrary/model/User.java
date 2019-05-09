package jo.edu.yu.yarmouklibrary.model;

import java.io.Serializable;

public class User implements Serializable {
    private String mName;
    private String mID;
    private PatronType mPatronType;
    private int mBooksCount;
    private int mLateBooksCount;
    private float mPendingFines;
    //constructor
    public User(String id){
        mID=id;
    }
    //getter / setter
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getID() {
        return mID;
    }
    public void setID(String ID) {
        mID = ID;
    }
    public PatronType getPatronType() {
        return mPatronType;
    }
    public void setPatronType(PatronType patronType) {
        mPatronType = patronType;
    }
    public float getPendingFines() {
        return mPendingFines;
    }
    public void setPendingFines(float pendingFines) {
        mPendingFines = pendingFines;
    }
    public int getBooksCount() {
        return mBooksCount;
    }
    public void setBooksCount(int booksCount) {
        mBooksCount = booksCount;
    }

    public int getLateBooksCount() {
        return mLateBooksCount;
    }

    public void setLateBooksCount(int lateBooksCount) {
        mLateBooksCount = lateBooksCount;
    }
}
