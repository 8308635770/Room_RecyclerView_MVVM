package com.example.room_viewmodel_livedata_recyclerviewmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.room_viewmodel_livedata_recyclerviewmvvm.data.Note;
import com.example.room_viewmodel_livedata_recyclerviewmvvm.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public void insert(Note note) {
        noteRepository.insertNote(note);
    }
    public void update(Note note) {
        noteRepository.updateNote(note);
    }
    public void delete(Note note) {
        noteRepository.deleteNote(note);
    }
    public void deleteAllNotes() {
        noteRepository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

}
