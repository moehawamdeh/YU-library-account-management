package jo.edu.yu.yarmouklibrary.model.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jo.edu.yu.yarmouklibrary.model.Book;
import jo.edu.yu.yarmouklibrary.model.Fine;
import jo.edu.yu.yarmouklibrary.model.Transaction;
import jo.edu.yu.yarmouklibrary.model.User;

public class LibraryParser {
    //TODO don't use order, config file
    public static User parseUser(String string){
        if(string.isEmpty())
            return null;
        String[] result=string.split("\u001E");
        User user=new User("");
        user.setName(result[0]);
        user.setBooksCount(Integer.parseInt(result[1]));
        user.setLateBooksCount(Integer.parseInt(result[2]));
        return user;
    }
    public static List<Fine> parseFines(String string){
        if(string.isEmpty())
            return null;
        String[]records=string.split("\u001F");
        List<Fine>finesList=new ArrayList<>();
        for (String record:records) {
            String[] result=record.split("\u001E");
            Fine fine=new Fine();
            fine.setType(result[0]);
            fine.setBarcode(result[1]);
            fine.setBookTitle(result[2]);
            if(result[0]!="LOST"){
                fine.setDue(Date.valueOf(result[3]));
                String date=result[3];
                if(date.equals("--"))
                    fine.setReturnDate(Date.valueOf(result[4]));
                fine.setValue(Float.parseFloat(result[5])*1.0f);
            }else{
                fine.setValue(Float.parseFloat(result[3])*1.0f);
            }

            finesList.add(fine);
        }
        return finesList;
    }
    public static List<Book> parseBooks(String string) {
        if(string.isEmpty())
            return null;
        String[]records=string.split("\u001F");
        List<Book>booksList=new ArrayList<>();
        for (String record:records) {
            if(record.isEmpty())
                break;

            String[] result=record.split("\u001E");
            Book book=new Book();
            book.setBarCode(result[0]);
            book.setTitle(result[1]);
            book.setAuthor(result[2]);
            book.setCheckOutDate(Date.valueOf(result[3]));
            book.setDueDate(Date.valueOf(result[4]));
            booksList.add(book);
        }
        return booksList;
    }
    public static List<Transaction> parseTransactionHistory(String string,boolean inArabic) {
        if(string.isEmpty())
            return null;
        String[]records=string.split("\u001F");
        List<Transaction>transactionList=new ArrayList<>();
        for (String record:records) {
            if(record.isEmpty())
                break;

            String[] result=record.split("\u001E");
            Transaction transaction=new Transaction();
            transaction.setBarCode(result[0]);
            transaction.setTitle(result[1]);
            transaction.setAuthor(result[2]);
            transaction.setType(result[3]);
//            if(!inArabic)
//            transaction.setType(result[3]);
//            else{
//                switch (result[3])
//                {
//                    case "UNDEFINED": transaction.setType("غير معرّف");{
//                        break;
//                    }
//                }
//            }
            transaction.setTransactionDate(Date.valueOf(result[4]));
            transactionList.add(transaction);
        }
        return transactionList;
    }
}
