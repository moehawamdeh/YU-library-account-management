package jo.edu.yu.yarmouklibrary.viewmodel;

import android.app.Application;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.*;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.model.data.LibraryRepository;
import jo.edu.yu.yarmouklibrary.model.data.UserCache;
import jo.edu.yu.yarmouklibrary.model.Alert;
import jo.edu.yu.yarmouklibrary.model.Book;
import jo.edu.yu.yarmouklibrary.model.Fine;
import jo.edu.yu.yarmouklibrary.model.Transaction;
import jo.edu.yu.yarmouklibrary.model.User;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<User>mUser;
    private MutableLiveData<Integer>mLoader;
    private MutableLiveData<List<Book>> mBooks;
    private MutableLiveData<List<Transaction>> mHistory;
    private MutableLiveData<List<Fine>> mFines;
    private MutableLiveData<Float>mTotalFines;
    private MutableLiveData<List<Alert>> mAlerts;
    private MutableLiveData<String>mErrorMessage;
    public UserViewModel(@NonNull Application application) {
        super(application);
        mUser=new MutableLiveData<>();
        mBooks = new MutableLiveData<>();
        mHistory = new MutableLiveData<>();
        mFines = new MutableLiveData<>();
        mTotalFines=new MutableLiveData<>();
        mAlerts = new MutableLiveData<>();
        mLoader=new MutableLiveData<>();
        mErrorMessage=new MutableLiveData<>();

    }
    public LiveData<String>getErrorMessage(){
        return mErrorMessage;
    }
    public LiveData<User>getUser(final String id){
        LibraryRepository<User> repository=new LibraryRepository<>();
        mLoader.postValue(View.VISIBLE);
        repository.getUserSummary(getApplication(),id,new LibraryRepository.RequestListener<User>() {
            @Override
            public void onCachedResult(User result) {
                mUser.setValue(result);
            }

            @Override
            public void onSuccess(User result) {
                mUser.postValue(result);
                mLoader.postValue(View.INVISIBLE);
            }

            @Override
            public void onFailed(int error_code) {
                handleError(error_code);
                mBooks.postValue(null);
            }
        });
        return mUser;
    }
    public LiveData<List<Book>> getBooks(){
        LibraryRepository<Book> repository=new LibraryRepository<>();
        mLoader.setValue(View.VISIBLE);
        repository.getBooks(getApplication(), new LibraryRepository.ListRequestListener<Book>() {
            @Override
            public void onCachedResult(List<Book> result) {
                mBooks.setValue(result);
            }

            @Override
            public void onSuccess(List<Book> result) {
                mBooks.postValue(result);
                mLoader.postValue(View.INVISIBLE);
            }
            @Override
            public void onFailed(int error_code) {
                handleError(error_code);
            }
        });
        return mBooks;
    }
    public LiveData<List<Transaction>> getTransactionHistory(String from, String to){
        LibraryRepository<Transaction> repository=new LibraryRepository<>();
        mLoader.setValue(View.VISIBLE);
        repository.getTransactionHistory(getApplication(),from,to,true, new LibraryRepository.ListRequestListener<Transaction>() {
            @Override
            public void onCachedResult(List<Transaction> result) {

            }

            @Override
            public void onSuccess(List<Transaction> result) {
                mHistory.postValue(result);
                mLoader.postValue(View.INVISIBLE);

            }
            @Override
            public void onFailed(int error_code) {
                handleError(error_code);
                mBooks.postValue(null);
            }
        });
        return mHistory;
    }
    public LiveData<List<Fine>>getFines(){
        LibraryRepository<Fine> repository=new LibraryRepository<>();
        mLoader.setValue(View.VISIBLE);
        repository.getFines(getApplication(), new LibraryRepository.ListRequestListener<Fine>() {
            @Override
            public void onCachedResult(List<Fine> result) {
                mFines.setValue(result);
                if(result!=null)
                {
                    float total=0;
                    for(Fine fine:result) {
                        total+=fine.getValue();
                    }
                    mTotalFines.setValue(total);
                }
            }

            @Override
            public void onSuccess(List<Fine> result) {
                mFines.postValue(result);
                if(result!=null)
                {
                    float total=0;
                    for(Fine fine:result) {
                        total+=fine.getValue();
                    }
                    mTotalFines.postValue(total);
                }
                mLoader.postValue(View.INVISIBLE);
            }
            @Override
            public void onFailed(int error_code) {
                handleError(error_code);
                mBooks.postValue(null);

            }
        });
        return mFines;
    }
    public LiveData<Float>getTotalFines(){
        return mTotalFines;
    }
    public LiveData<Integer>getLoader(){return mLoader;}
    public LiveData getAlerts(){
        return mAlerts;
    }
    public void logout() {
        UserCache.eraseAll(getApplication());
    }
    public String getID() {
        return UserCache.getID(getApplication());
    }
    private void handleError(int error_code){
        mLoader.postValue(View.INVISIBLE);
        if(error_code==LibraryRepository.ERROR_NETWORK_GENERAL)
            mErrorMessage.postValue(getApplication().getString(R.string.error_network));
        else if(error_code==LibraryRepository.ERROR_NETWORK_TIMEOUT)
            mErrorMessage.postValue(getApplication().getString(R.string.error_network_timeout));
        else if(error_code==LibraryRepository.ERROR_NO_SUCH_RECORD)
        mErrorMessage.postValue(getApplication().getString(R.string.error_no_such_record));
    }
}
