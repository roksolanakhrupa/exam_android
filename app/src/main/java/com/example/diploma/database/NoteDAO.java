//package com.example.diploma;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//
//@Dao
//public interface NoteDAO {
//
//    @Query("SELECT count(*) from Note")
//    int getCount();
//
//    @Query("SELECT * FROM note")
//    List<Note> getAll();
//
//    @Query("SELECT * FROM note WHERE id = :id")
//    Note getById(long id);
//
//    @Insert
//    void insert(Note employee);
//
//    @Update
//    void update(Note employee);
//
//    @Delete
//    void delete(Note employee);
//
//    @Query("INSERT into Note(title, path, type, changeDate, password) values (:title, :path, :type, :changeDate, :password)")
//    void insertByFields(String title, String path, String type, String changeDate, String password);
//
//}