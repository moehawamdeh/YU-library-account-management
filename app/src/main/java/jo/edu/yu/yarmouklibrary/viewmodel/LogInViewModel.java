package jo.edu.yu.yarmouklibrary.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;


import com.daandtu.webscraper.Element;
import com.daandtu.webscraper.WebScraper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.*;
public class LogInViewModel extends AndroidViewModel {
    private MutableLiveData<AuthState> mAuthState;
    public LogInViewModel(@NonNull Application application) {
        super(application);
        mAuthState=new MutableLiveData<>();
        mAuthState.setValue(AuthState.DECLINED);
    }
    private String autoLogin(final String loginUrl, String id, String password) {


            return "";
//        final WebScraper webScraper = new WebScraper(activity.getContext());
//        webScraper.setUserAgentToDesktop(true); //default: false
//        webScraper.setLoadImages(true); //default: false
//        webScraper.setOnPageLoadedListener(new WebScraper.onPageLoadedListener() {
//            @Override
//            public void loaded(String URL) {
//                String a=URL;
//                String v=a;
//            }
//        });
//        activity.post(new Runnable() {
//            @Override
//            public void run() {
//                webScraper.loadURL(loginUrl);
//            }
//        });
//        webScraper.loadURL(loginUrl);
//        Element inputID = webScraper.findElementByXpath("//input[@id='P101_USERNAME']");
//        Element inputPass = webScraper.findElementByXpath("//input[@id='P101_PASSWORD']");
//        inputID.setText(id);
//        inputPass.setText(password);
//        Element buttonLogin = webScraper.findElementByXpath("//a[contains(@href,\"javascript:apex.submit('LOGIN');\") ]");
//        buttonLogin.click();
//        WebDriver driver = new AndroidWebDriver(activity);
//        driver.get(loginUrl);
//        WebElement inputID=driver.findElement(By.xpath("//input[@id='P101_USERNAME']"));
//        WebElement inputPass=driver.findElement(By.xpath("//input[@id='P101_USERNAME']"));
//        WebElement button=driver.findElement(By.xpath("//a[contains(@href,\"javascript:apex.submit('LOGIN');\") ]"));
//        button.click();
//        return driver.getCurrentUrl();


    }
    public LiveData<AuthState> getAuthenticationState( String id) {
        /* authenticate */

        try {
            final WebScraper webScraper = new WebScraper(getApplication());
            webScraper.setUserAgentToDesktop(true); //default: false
            webScraper.setLoadImages(true); //default: false

            webScraper.setOnPageLoadedListener(new WebScraper.onPageLoadedListener() {
                @Override
                public void loaded(String URL) {
                    Element inputID = webScraper.findElementById("username");
                    Element inputPass = webScraper.findElementById("password");
                    inputID.setText("2016980044");
                    inputPass.setText("123mmm123");
                    //Element buttonLogin = webScraper.findElementByXpath("//a[contains(@href,\"javascript:apex.submit('LOGIN');\") ]");
                    Element buttonLogin = webScraper.findElementById("loginbtn");
                    buttonLogin.click();
                        while (webScraper.getURL().equals("https://elearning.yu.edu.jo/login/index.php"));
                    Log.i("LOGIN_DEBUG","@@@@@@@@@@");


                    mAuthState.postValue(AuthState.DECLINED);

                }
            });
            webScraper.loadURL("https://elearning.yu.edu.jo/login/index.php");

        }catch (Exception e){
            Log.i("LOGIN_DEBUG",e.getMessage());
            e.printStackTrace();
        }


        return mAuthState;
    }
    public enum AuthState{
        GRANTED,DECLINED
    }
}
