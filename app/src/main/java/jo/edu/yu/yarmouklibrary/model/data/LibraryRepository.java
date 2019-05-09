package jo.edu.yu.yarmouklibrary.model.data;

import android.content.Context;

import java.util.List;

import jo.edu.yu.yarmouklibrary.model.Book;
import jo.edu.yu.yarmouklibrary.model.Fine;
import jo.edu.yu.yarmouklibrary.model.QueryType;
import jo.edu.yu.yarmouklibrary.model.Transaction;
import jo.edu.yu.yarmouklibrary.model.User;

public class LibraryRepository<T> {
    public static final int ERROR_NETWORK_GENERAL =1;
    public static final int ERROR_NETWORK_TIMEOUT=2;
    public static final int ERROR_NO_SUCH_RECORD=5;
    private LibraryParser mService; //retrofit web service

    public void getFines(final Context context, final ListRequestListener<Fine> listener){
        if(listener==null)
            return;
        libraryUrlBuilder urlBuilder= new libraryUrlBuilder(context);
        final String url = urlBuilder.buildFor(QueryType.Fines,UserCache.getID(context));
        LibraryService.getData(url,new LibraryService.OnResultListener(){
            public void onResult(String result){
                List<Fine> fines =LibraryParser.parseFines(result);
                if(fines==null)
                {
                    listener.onFailed(ERROR_NO_SUCH_RECORD);
                    return;
                }
                UserCache.persistFines(fines,context);
                listener.onSuccess(fines);
            }

            @Override
            public void onFailed(int errorCode) {
                listener.onFailed(errorCode);
            }
        });
        List<Fine> fines= UserCache.getFines(context);
        listener.onCachedResult(fines);
    }

    public void getUserSummary(final Context context,final String id, final RequestListener<User> requestListener) {
        if(requestListener==null)
            return;
        libraryUrlBuilder urlBuilder= new libraryUrlBuilder(context);
        final String url = urlBuilder.buildFor(QueryType.SUMMARY,id);
        LibraryService.getData(url,new LibraryService.OnResultListener(){
            public void onResult(String result){
                User user =LibraryParser.parseUser(result);
                if(user==null)
                {
                    requestListener.onFailed(ERROR_NO_SUCH_RECORD);
                    return;
                }
                user.setID(id);
                UserCache.persistUser(user,context);
                requestListener.onSuccess(user);
            }

            @Override
            public void onFailed(int errorCode) {
                requestListener.onFailed(0);
            }
        });
        User user= UserCache.getUser(context);
        requestListener.onCachedResult(user);
    }

    public void getBooks(final Context context, final ListRequestListener<Book> listener) {
        if(listener==null)
            return;
        libraryUrlBuilder urlBuilder= new libraryUrlBuilder(context);
        final String url = urlBuilder.buildFor(QueryType.BOOKS,UserCache.getID(context));
        LibraryService.getData(url,new LibraryService.OnResultListener(){
            public void onResult(String result){
                List<Book> books =LibraryParser.parseBooks(result);
                if(books==null)
                {
                    listener.onFailed(ERROR_NO_SUCH_RECORD);
                    return;
                }
                UserCache.persistBooks(books,context);
                listener.onSuccess(books);
            }

            @Override
            public void onFailed(int errorCode) {
                listener.onFailed(0);
            }
        });
        List<Book> books= UserCache.getBooks(context);
        listener.onCachedResult(books);
    }

    public void getTransactionHistory(final Context context, String from, String to,final boolean inArabic, final ListRequestListener<Transaction> listener) {
        //TODO no need to cache this!!
        if(listener==null)
            return;
        libraryUrlBuilder urlBuilder= new libraryUrlBuilder(context);
        final String url = urlBuilder.buildFor(QueryType.HISTORY,UserCache.getID(context),from,to);
        LibraryService.getData(url,new LibraryService.OnResultListener(){
            public void onResult(String result){
                List<Transaction> transactions =LibraryParser.parseTransactionHistory(result, inArabic);
                if(transactions==null)//empty result
                {
                    listener.onFailed(ERROR_NO_SUCH_RECORD);
                    return;
                }
                listener.onSuccess(transactions);
            }

            @Override
            public void onFailed(int errorCode) {
                listener.onFailed(0);
            }
        });
    }

    public void syncData(final Context context,String id) {

    }

    public interface ListRequestListener<T>{
        void onCachedResult(List<T> result);
        void onSuccess(List<T> result);
        void onFailed(int error_code);
    }
    public interface RequestListener<T>{
        void onCachedResult(T result);
        void onSuccess(T result);
        void onFailed(int error_code);
    }
}
