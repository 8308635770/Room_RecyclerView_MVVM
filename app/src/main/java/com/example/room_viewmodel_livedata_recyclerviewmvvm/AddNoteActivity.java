package com.example.room_viewmodel_livedata_recyclerviewmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private NumberPicker numberPicker;
    private EditText titleEt,descEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        numberPicker=findViewById(R.id.number_picker_priority);
        titleEt=findViewById(R.id.edit_text_title);
        descEt=findViewById(R.id.edit_text_description);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);
        setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    public void savenote(){
        
        String title,desc;
        int priority;
        
        title=titleEt.getText().toString();
        desc=descEt.getText().toString();
        priority=numberPicker.getValue();
        
        if(title.trim().isEmpty() || desc.trim().isEmpty()){
            Toast.makeText(this, "Please Fill Proper Details", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent=new Intent();
            intent.putExtra("TITLE",title);
            intent.putExtra("DESC",desc);
            intent.putExtra("PRIORITY",priority);

            setResult(RESULT_OK,intent);
            finish();
        }
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_note:
                savenote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}