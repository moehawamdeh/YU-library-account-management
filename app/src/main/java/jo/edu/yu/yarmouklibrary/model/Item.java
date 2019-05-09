package jo.edu.yu.yarmouklibrary.model;

public class Item {
    private String mBarCode;
    private String mTitle;
    private String mAuthor;
    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBarCode() {
        return mBarCode;
    }

    public void setBarCode(String barCode) {
        mBarCode = barCode;
    }

}
