package com.example.xlibrary.ui.books.addbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.google.android.material.textfield.TextInputEditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class CreateBookActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_create_book);
        ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        if (result.getData() == null)
                            return;
                        ImageView issuePictureImageView = findViewById(R.id.bookImageView);
                        issuePictureImageView.setImageURI(result.getData().getData());
                    }
                });
        findViewById(R.id.bookImageView).setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            galleryActivityResultLauncher.launch(intent);
        });
        findViewById(R.id.createBookButton).setOnClickListener(v -> {
            TextInputEditText title = findViewById(R.id.book_title);
            TextInputEditText description = findViewById(R.id.bookDescriptionTextInputEditText);
            TextInputEditText author = findViewById(R.id.bookAuthorTextInputEditText);
            TextInputEditText pages = findViewById(R.id.pagesTextInputEditText);
            TextInputEditText language = findViewById(R.id.languageTextInputEditText);
            TextInputEditText release_year = findViewById(R.id.releaseTextInputEditText);
            TextInputEditText category = findViewById(R.id.categoryInputEditText);
            ImageView bookImg = findViewById(R.id.bookImageView);
            Bitmap bookImgBitmap = bookImg.getDrawable() instanceof BitmapDrawable ? ((BitmapDrawable)bookImg.getDrawable()).getBitmap() : null;
            // insert to database
            databaseHelper.addBooks(title.getText().toString(),description.getText().toString(), author.getText().toString(), pages.getText().toString(), language.getText().toString(), release_year.getText().toString(), category.getText().toString(), bookImgBitmap);
            finish();
        });
    }
}