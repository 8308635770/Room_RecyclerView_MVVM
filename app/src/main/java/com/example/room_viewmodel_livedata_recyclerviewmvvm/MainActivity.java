package com.example.room_viewmodel_livedata_recyclerviewmvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.room_viewmodel_livedata_recyclerviewmvvm.adapter.NoteAdapter;
import com.example.room_viewmodel_livedata_recyclerviewmvvm.data.DataBase;
import com.example.room_viewmodel_livedata_recyclerviewmvvm.data.Note;
import com.example.room_viewmodel_livedata_recyclerviewmvvm.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;

    private FloatingActionButton btn_insert_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        recyclerView = findViewById(R.id.recycler_view_note_item);
        btn_insert_note = findViewById(R.id.btn_insert_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra("ID", note.getId());
                intent.putExtra("TITLE", note.getTitle());
                intent.putExtra("DESC", note.getDescription());
                intent.putExtra("PRIORITY", note.getPriority());
                startActivityForResult(intent, 2);
            }
        });


        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
                Toast.makeText(MainActivity.this, Integer.toString(notes.size()), Toast.LENGTH_SHORT).show();
            }
        });

        btn_insert_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//                noteViewModel.insert();

                String title = data.getStringExtra("TITLE");
                String dec = data.getStringExtra("DESC");
                int priority = data.getIntExtra("PRIORITY", 0);

                noteViewModel.insert(new Note(title, dec, priority));
            }
            else if (requestCode == 2) {
                Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
                int id = data.getIntExtra("ID", -1);
                if (id == -1) {
                    Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    String title = data.getStringExtra("TITLE");
                    String description = data.getStringExtra("DESC");
                    int priority = data.getIntExtra("PRIORITY", 1);
                    Note note = new Note(title, description, priority);
                    note.setId(id);
                    noteViewModel.update(note);
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}