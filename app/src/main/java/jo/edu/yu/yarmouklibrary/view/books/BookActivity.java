package jo.edu.yu.yarmouklibrary.view.books;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.model.Book;
import jo.edu.yu.yarmouklibrary.databinding.ActivityBookBinding;

import android.view.View;

public class BookActivity extends AppCompatActivity {
    public final static String EXTRA_BOOK="book_extra";
    private Book mBook;
    ActivityBookBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        if(intent!=null || mBook!=null){
            Book book=(Book)intent.getSerializableExtra(EXTRA_BOOK);
            mBook=book;
            mBinding.textTitle.setText(book.getTitle());
            mBinding.textAuthor.setText(book.getAuthor());
            mBinding.textBarcode.setText(book.getBarCode());
            mBinding.textCheckoutDate.setText(book.getCheckOutDate()==null?"":book.getCheckOutDate().toString());
            mBinding.textDueDate.setText(book.getDueDate()==null?"":book.getDueDate().toString());
            if(book.getReturnDate()!=null){
                mBinding.textReturnDate.setText(book.getReturnDate().toString());
                mBinding.layoutReturnDate.setVisibility(View.VISIBLE);
            }


        }
        else finish();



    }

}
