package com.example.room_viewmodel_livedata_recyclerviewmvvm.repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.room_viewmodel_livedata_recyclerviewmvvm.data.Note;
import com.example.room_viewmodel_livedata_recyclerviewmvvm.data.NoteDao;
import com.example.room_viewmodel_livedata_recyclerviewmvvm.data.NotedataBase;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NotedataBase notedataBase=NotedataBase.getNotedatabase(application);
        noteDao=notedataBase.noteDao();
        allNotes=noteDao.getAllNotes();
    }

    public void insertNote(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void updateNote(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteNote(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
            return allNotes;
    }


    public static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    public static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    public static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;
        public DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }




}
