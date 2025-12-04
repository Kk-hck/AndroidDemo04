package com.example.android.notepad;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CategoryManager extends Activity {
    private Spinner mCategorySpinner;
    private EditText mNewCategoryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_manager);

        mCategorySpinner = findViewById(R.id.category_spinner);
        mNewCategoryEditText = findViewById(R.id.new_category_edit);

        setupCategorySpinner();

        findViewById(R.id.add_category_button).setOnClickListener(v -> addNewCategory());
    }

    private void setupCategorySpinner() {
        // 获取所有分类
        String[] categories = {
                NotePad.Notes.CATEGORY_DEFAULT,
                NotePad.Notes.CATEGORY_WORK,
                NotePad.Notes.CATEGORY_PERSONAL,
                NotePad.Notes.CATEGORY_TODO
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(adapter);
    }

    private void addNewCategory() {
        String newCategory = mNewCategoryEditText.getText().toString().trim();
        if (!newCategory.isEmpty()) {
            // 这里可以将新分类保存到 SharedPreferences 或数据库中
            Toast.makeText(this, "分类添加成功: " + newCategory, Toast.LENGTH_SHORT).show();
            mNewCategoryEditText.setText("");
        }
    }
}

