package jo.edu.yu.yarmouklibrary.model.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jo.edu.yu.yarmouklibrary.model.Book;
import jo.edu.yu.yarmouklibrary.model.Fine;
import jo.edu.yu.yarmouklibrary.model.User;

public class UserCache {
    private static final String PREF_USER= "UserData";
    private static final String KEY_USER= "user";
    private static final String KEY_FINES= "fines";
    private static final String KEY_ID ="main_id" ;
    private static final String KEY_BOOKS ="books" ;
    private static final String KEY_BOOKS_HISTORY ="books_history" ;

    public static User getUser(Context context){
       SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
       return new Gson().fromJson(sharedPreferences.getString(KEY_USER,null),User.class);
    }
    public static void persistUser(User user,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(KEY_USER,new Gson().toJson(user))
                .apply();
    }
    public static List<Fine> getFines(Context context){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        Type listType = new TypeToken<ArrayList<Fine>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(KEY_FINES,null),listType);
    }
    public static void persistFines(List<Fine> fines,Context context){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(KEY_FINES,gson.toJson(fines))
                .apply();
    }

    public static void eraseAll(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public static String getID(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,null);
    }

    public static void persistID(Context context,String id) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_ID,id).apply();
    }

    public static void persistBooks(List<Book> books, Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(KEY_BOOKS,gson.toJson(books))
                .apply();
    }

    public static List<Book> getBooks(Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(KEY_BOOKS,null),listType);
    }

    public static List<Book>  persistBooksHistory(List<Book> books, Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(KEY_BOOKS_HISTORY,null),listType);
    }

    public static List<Book> getBooksHistory(Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_USER,Context.MODE_PRIVATE);
        Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(KEY_BOOKS_HISTORY,null),listType);

    }
}
