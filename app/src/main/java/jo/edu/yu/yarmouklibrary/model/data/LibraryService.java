package jo.edu.yu.yarmouklibrary.model.data;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeoutException;

class LibraryService {
    public static void getData(final String urlString, final OnResultListener onResultListener) {
        new Thread(){
            public void run(){
                try{
                    String result=getContent(urlString);
                    onResultListener.onResult(result);
                }
                catch (IOException io) {
                    Log.d("TEST_LIBRARY",io.getMessage());
                    onResultListener.onFailed(OnResultListener.GENERAL_ERROR);
                }
                catch (TimeoutException to){
                    onResultListener.onFailed(OnResultListener.TIME_OUT);
                }
            }
        }.start();
    }

    public interface OnResultListener {
        int TIME_OUT=-1;
        int GENERAL_ERROR=-2;
        void onResult(String result);
        void onFailed(int errorCode);
    }
    private static String getContent(String urlString) throws IOException,TimeoutException{
        URL url =new URL(urlString);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(3000);
        try{
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();
            int i=connection.getResponseCode();
            if(connection.getResponseCode()==HttpURLConnection.HTTP_CLIENT_TIMEOUT)
                throw new TimeoutException();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+": with "+urlString);
            }
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while ((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return new String(out.toByteArray());
        }
        finally {
            connection.disconnect();
        }
    }
}
