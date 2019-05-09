package jo.edu.yu.yarmouklibrary.model.data;

import android.content.Context;

import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.model.QueryType;

public class libraryUrlBuilder {
    private String mBase;
    public libraryUrlBuilder(Context context){

        //base link is used to send get/post request
        mBase=context.getString(R.string.lib_url);
    }
    public String buildFor(QueryType type, String id, String... typeParams){
        UrlBuild urlBuild=new UrlBuild(mBase);
        switch (type){
            case SUMMARY:{
                urlBuild.appendQueryParameter("type", "summary");
                break;
            }
            case Fines:{
                urlBuild.appendQueryParameter("type","fines");
                break;
            }
            case BOOKS:{
                urlBuild.appendQueryParameter("type","books");
                break;
            }
            case HISTORY:{
                urlBuild.appendQueryParameter("type","history");
                urlBuild.appendQueryParameter("from",typeParams[0]);
                urlBuild.appendQueryParameter("to",typeParams[1]);
                break;
            }
        }
        urlBuild.appendQueryParameter("id","yu"+id);
        return urlBuild.getURL();
    }

    private class UrlBuild{
        private String mURL;
        private boolean first=true;
        UrlBuild(String base){
          mURL=base+'?';
        }
        void appendQueryParameter(String key,String value){
            if(first){
                first=false;
                mURL = mURL+key+"="+value;
                return;
            }
            mURL = mURL+"&"+key+"="+value;

        }
        String getURL(){
            return mURL;
        }
    }
}
