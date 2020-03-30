//package com.example.diploma;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.baoyz.swipemenulistview.SwipeMenu;
//import com.baoyz.swipemenulistview.SwipeMenuCreator;
//import com.baoyz.swipemenulistview.SwipeMenuItem;
//import com.baoyz.swipemenulistview.SwipeMenuListView;
//
//import java.util.ArrayList;
//
//public class HomeActivity extends AppCompatActivity {
//
//    public ArrayList<Note> notes = new ArrayList();
//    public ArrayList<Note> pinNotes = new ArrayList();
//    public NoteAdapter noteAdapter;
//    public NoteAdapter pinNoteAdapter;
//    public SwipeMenuListView notesList;
//    public SwipeMenuListView pinNotesList;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//
//        setInitialData();
//        notesList = findViewById(R.id.notesList);
//        noteAdapter = new NoteAdapter(this, R.layout.list_item, notes);
//        notesList.setAdapter(noteAdapter);
//
//        pinNotesList = findViewById(R.id.pinNotesList);
//        pinNoteAdapter = new NoteAdapter(this, R.layout.list_item, pinNotes);
//        pinNotesList.setAdapter(pinNoteAdapter);
//
//        UpdateCountNotes();
//
//        ImageButton btnAddNote = findViewById(R.id.btnAddNote);
//        btnAddNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), NoteActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });
//
//
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
//                deleteItem.setWidth(150);
//                deleteItem.setIcon(R.mipmap.ic_delete);
//                menu.addMenuItem(deleteItem);
//
//                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
//                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
//                pinItem.setWidth(150);
//                pinItem.setIcon(R.mipmap.ic_pin);
//                menu.addMenuItem(pinItem);
//            }
//        };
//
//        final SwipeMenuCreator pinCreator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
//                deleteItem.setWidth(150);
//                deleteItem.setIcon(R.mipmap.ic_delete);
//                menu.addMenuItem(deleteItem);
//
//                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
//                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
//                pinItem.setWidth(150);
//                pinItem.setIcon(R.mipmap.ic_unpin);
//                menu.addMenuItem(pinItem);
//            }
//        };
//
//
//
//        notesList.setMenuCreator(creator);
//        pinNotesList.setMenuCreator(pinCreator);
//
//        pinNotesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        pinNoteAdapter.remove(pinNotes.get(position));
//                        pinNoteAdapter.notifyDataSetChanged();
//                        pinNotesList.setAdapter(pinNoteAdapter);
//                        UpdateCountNotes();
//
//                        if (pinNotes.size() == 0) {
//                            View line = findViewById(R.id.line);
//                            line.setBackgroundResource(R.color.colorWhite);
//
//                            TextView pinnedText=findViewById(R.id.tv_pinned);
//                            pinnedText.setVisibility(View.GONE);
//                        }
//                        break;
//                    case 1:
//                        noteAdapter.add(pinNotes.get(position));
//                        noteAdapter.notifyDataSetChanged();
//                        notesList.setAdapter(noteAdapter);
//
//                        pinNoteAdapter.remove(pinNotes.get(position));
//                        pinNoteAdapter.notifyDataSetChanged();
//                        pinNotesList.setAdapter(pinNoteAdapter);
//
//                        if (pinNotes.size() == 0) {
//                            View line = findViewById(R.id.line);
//                            line.setBackgroundResource(R.color.colorWhite);
//
//                            TextView pinnedText=findViewById(R.id.tv_pinned);
//                            pinnedText.setVisibility(View.GONE);
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
//        notesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        noteAdapter.remove(notes.get(position));
//                        noteAdapter.notifyDataSetChanged();
//                        notesList.setAdapter(noteAdapter);
//                        UpdateCountNotes();
//                        break;
//                    case 1:
//                        if (pinNotes.size() == 0) {
//                            View line = findViewById(R.id.line);
//                            line.setBackgroundResource(R.color.colorGrey);
//
//                            TextView pinnedText=findViewById(R.id.tv_pinned);
//                           pinnedText.setVisibility(View.VISIBLE);
//                        }
//                        if (pinNotes.size() == 3)
//                            Toast.makeText(getApplicationContext(), "You can not pin no more", Toast.LENGTH_LONG).show();
//                        else {
//                            pinNotes.add(notes.get(position));
//                            pinNoteAdapter.notifyDataSetChanged();
//                            pinNotesList.setAdapter(pinNoteAdapter);
//
//                            noteAdapter.remove(notes.get(position));
//                            noteAdapter.notifyDataSetChanged();
//                            notesList.setAdapter(noteAdapter);
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
//
//
//        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Note noteToEdit = notes.get(position);
//
//                Intent intent = new Intent(view.getContext(), NoteActivity.class);
//                intent.putExtra("id", position);
//                intent.putExtra("title", noteToEdit.title);
//                intent.putExtra("content", noteToEdit.content);
//                intent.putExtra("createDate", noteToEdit.changeDate);
//                intent.putStringArrayListExtra("photos", noteToEdit.photos);
//                startActivityForResult(intent, 1);
//
//
//            }
//        });
//
//
//    }
//
//
//    private void setInitialData() {
//        notes.add(new Note("test1", "test test", new ArrayList<String>(), false, "01.01.2020", "01.01.2020"));
//        notes.add(new Note("test2", "test test", new ArrayList<String>(), false, "01.01.2020", "01.01.2020"));
//        notes.add(new Note("test3", "test test", new ArrayList<String>(), false, "01.01.2020", "01.01.2020"));
//    }
//
//    private void UpdateCountNotes() {
//        TextView countNotes = findViewById(R.id.countNotes);
//        countNotes.setText("Count: " + String.valueOf(notes.size()+pinNotes.size()));
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null) {
//            return;
//        }
//        switch (requestCode) {
//            case 1: {
//                String title = data.getStringExtra("title");
//                String content = data.getStringExtra("content");
//                ArrayList<String> photos=data.getStringArrayListExtra("photos");
//                ArrayList<String> photosTmp=data.getStringArrayListExtra("photosTmp");
//                if (!title.isEmpty() || !content.isEmpty()) {
//                    String createDate = data.getStringExtra("createDate");
//                    Boolean isEditable = data.getBooleanExtra("isEditable", false);
//                    if (isEditable) {
//                        int id = data.getIntExtra("id", 0);
//                        Note newNote = new Note(title, content, photosTmp, false, createDate, createDate);
//                        noteAdapter.remove(notes.get(id));
//                        noteAdapter.insert(newNote, id);
//                    } else {
//                        Note newNote = new Note(title, content, photosTmp, false, createDate, createDate);
//                        noteAdapter.add(newNote);
//                        UpdateCountNotes();
//                    }
//                    noteAdapter.notifyDataSetChanged();
//                    notesList.setAdapter(noteAdapter);
//                }
//            }
//        }
//    }
//
//
//}


package com.example.diploma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public ArrayList<Note> notes = new ArrayList();
    public SwipeMenuListView notesList;
    NoteAdapter noteAdapter;
    private AppDatabase database;
    private NoteDAO noteDAO;


    public ArrayList<Note> pinNotes = new ArrayList();
    public NoteAdapter pinNoteAdapter;
    public SwipeMenuListView pinNotesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        noteDAO = database.noteDao();
        notes = (ArrayList<Note>) noteDAO.getAll();
        notesList = findViewById(R.id.notesList);
        noteAdapter = new NoteAdapter(this, R.layout.list_item, notes);
        notesList.setAdapter(noteAdapter);


        pinNotesList = findViewById(R.id.pinNotesList);
        pinNoteAdapter = new NoteAdapter(this, R.layout.list_item, pinNotes);
        pinNotesList.setAdapter(pinNoteAdapter);

        Toast.makeText(getApplicationContext(), String.valueOf(noteDAO.getCount()), Toast.LENGTH_LONG).show();

        UpdateCountNotes();

        ImageButton btnAddNote = findViewById(R.id.btnAddNote);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);

            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
                deleteItem.setWidth(100);
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
                pinItem.setWidth(100);
                pinItem.setIcon(R.mipmap.ic_pin);
                menu.addMenuItem(pinItem);

                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                editItem.setBackground(R.color.colorPurple);
                editItem.setWidth(100);
                editItem.setIcon(R.mipmap.ic_edit);
                menu.addMenuItem(editItem);
            }
        };

        SwipeMenuCreator pinCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
                deleteItem.setWidth(100);
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
                pinItem.setWidth(100);
                pinItem.setIcon(R.mipmap.ic_unpin);
                menu.addMenuItem(pinItem);

                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                editItem.setBackground(R.color.colorPurple);
                editItem.setWidth(100);
                editItem.setIcon(R.mipmap.ic_edit);
                menu.addMenuItem(editItem);
            }
        };

        notesList.setMenuCreator(creator);
        pinNotesList.setMenuCreator(pinCreator);

        pinNotesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //delete manually file
                        noteDAO.delete(pinNotes.get(position));
                        pinNoteAdapter.remove(pinNotes.get(position));
                        pinNoteAdapter.notifyDataSetChanged();
                        UpdateCountNotes();

                        if (pinNotes.size() == 0) {
                            View line = findViewById(R.id.line);
                            line.setBackgroundResource(R.color.colorWhite);

                            TextView pinnedText = findViewById(R.id.tv_pinned);
                            pinnedText.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        noteAdapter.add(pinNotes.get(position));
                        noteAdapter.notifyDataSetChanged();
                        notesList.setAdapter(noteAdapter);

                        pinNoteAdapter.remove(pinNotes.get(position));
                        pinNoteAdapter.notifyDataSetChanged();
                        pinNotesList.setAdapter(pinNoteAdapter);

                        if (pinNotes.size() == 0) {
                            View line = findViewById(R.id.line);
                            line.setBackgroundResource(R.color.colorWhite);

                            TextView pinnedText = findViewById(R.id.tv_pinned);
                            pinnedText.setVisibility(View.GONE);
                        }
                        break;
                    case 2:
                    {
                        Note noteToEdit = pinNotes.get(position);
                        Intent intent = null;
                        if (noteToEdit.type.equals("text"))
                            intent = new Intent(getApplicationContext(), TextNoteActivity.class);
                        if (noteToEdit.type.equals("photo"))
                            intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
                        intent.putExtra("id", noteToEdit.id);
                        intent.putExtra("title", noteToEdit.title);
                        intent.putExtra("path", noteToEdit.path);
                        intent.putExtra("createDate", noteToEdit.changeDate);
                        intent.putExtra("position", position);
                        if (noteToEdit.type.equals("text"))
                            startActivityForResult(intent, 1);
                        else
                            startActivityForResult(intent, 2);


                    }
                    break;
                }
                return false;
            }
        });
        notesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //delete manually file
                        noteDAO.delete(notes.get(position));
                        noteAdapter.remove(notes.get(position));
                        noteAdapter.notifyDataSetChanged();
                        notesList.setAdapter(noteAdapter);
                        UpdateCountNotes();
                        break;
                    case 1:
                        if (pinNotes.size() == 0) {
                            View line = findViewById(R.id.line);
                            line.setBackgroundResource(R.color.colorGrey);

                            TextView pinnedText = findViewById(R.id.tv_pinned);
                            pinnedText.setVisibility(View.VISIBLE);
                        }
                        if (pinNotes.size() == 3)
                            Toast.makeText(getApplicationContext(), "You can not pin no more", Toast.LENGTH_LONG).show();
                        else {
                            pinNotes.add(notes.get(position));
                            pinNoteAdapter.notifyDataSetChanged();
                            pinNotesList.setAdapter(pinNoteAdapter);

                            noteAdapter.remove(notes.get(position));
                            noteAdapter.notifyDataSetChanged();
                            notesList.setAdapter(noteAdapter);
                        }
                        break;
                    case 2:
                    {
                        Note noteToEdit = notes.get(position);
                        Intent intent = null;
                        if (noteToEdit.type.equals("text"))
                            intent = new Intent(getApplicationContext(), TextNoteActivity.class);
                        if (noteToEdit.type.equals("photo"))
                            intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
                        intent.putExtra("id", noteToEdit.id);
                        intent.putExtra("title", noteToEdit.title);
                        intent.putExtra("path", noteToEdit.path);
                        intent.putExtra("createDate", noteToEdit.changeDate);
                        intent.putExtra("position", position);
                        if (noteToEdit.type.equals("text"))
                            startActivityForResult(intent, 1);
                        else
                            startActivityForResult(intent, 2);


                    }
                    break;
                }
                return false;
            }
        });


        pinNotesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note noteToEdit = pinNotes.get(position);
                Intent intent = null;
                switch(noteToEdit.type)
                {
                    case "text":
                        intent = new Intent(view.getContext(), ViewTextNoteActivity.class);
                        break;
                    case "photo":
                        intent = new Intent(view.getContext(), ViewPhotoNoteActivity.class);
                        break;
                    case "list":
                        intent = new Intent(view.getContext(), ViewListNoteActivity.class);
                        break;
                    case "sound":
                        intent = new Intent(view.getContext(), ViewSoundNoteActivity.class);
                        break;
                }


                intent.putExtra("id", noteToEdit.id);
                intent.putExtra("title", noteToEdit.title);
                intent.putExtra("path", noteToEdit.path);
                intent.putExtra("createDate", noteToEdit.changeDate);
                intent.putExtra("password", noteToEdit.password);
                intent.putExtra("position", position);
                switch(noteToEdit.type)
                {
                    case "text":
                        startActivityForResult(intent, 1);
                        break;
                    case "photo":
                        startActivityForResult(intent, 2);
                        break;
                    case "list":
                        startActivityForResult(intent, 3);
                        break;
                    case "sound":
                        startActivityForResult(intent, 4);
                        break;
                }

            }
        });

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note noteToEdit = notes.get(position);
                Intent intent = null;
                switch(noteToEdit.type)
                {
                    case "text":
                        intent = new Intent(view.getContext(), ViewTextNoteActivity.class);
                        break;
                    case "photo":
                        intent = new Intent(view.getContext(), ViewPhotoNoteActivity.class);
                        break;
                    case "list":
                        intent = new Intent(view.getContext(), ViewListNoteActivity.class);
                        break;
                    case "sound":
                        intent = new Intent(view.getContext(), ViewSoundNoteActivity.class);
                        break;
                }
                intent.putExtra("id", noteToEdit.id);
                intent.putExtra("title", noteToEdit.title);
                intent.putExtra("path", noteToEdit.path);
                intent.putExtra("createDate", noteToEdit.changeDate);
                intent.putExtra("password", noteToEdit.password);
                intent.putExtra("position", position);
                switch(noteToEdit.type)
                {
                    case "text":
                        startActivityForResult(intent, 1);
                        break;
                    case "photo":
                        startActivityForResult(intent, 2);
                        break;
                    case "list":
                        startActivityForResult(intent, 3);
                        break;
                    case "sound":
                        startActivityForResult(intent, 4);
                        break;
                }


            }
        });

    }


    private void UpdateCountNotes() {
        TextView countNotes = findViewById(R.id.countNotes);
        int allCount = notes.size() + pinNotes.size();
        countNotes.setText("Count: " + allCount);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1: {
                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");
                String password = data.getStringExtra("password");


                Note note = new Note();
                note.title = title;
                note.path = path;
                note.changeDate = changeDate;
                note.type = "text";
                note.password=password;
                Boolean isEditable = data.getBooleanExtra("isEditable", false);
                if (isEditable) {
                    note.id = data.getIntExtra("id", 1);
                    noteDAO.update(note);
                    int position = data.getIntExtra("position", 0);
                    noteAdapter.remove(noteAdapter.getItem(position));
                    noteAdapter.insert(note, position);
                } else {
                    noteDAO.insert(note);
                    noteAdapter.add(note);

                }


            }
            break;
            case 2: {

                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");
                String password = data.getStringExtra("password");


                Note note = new Note();
                note.title = title;
                note.path = path;
                note.changeDate = changeDate;
                note.password=password;
                note.type = "photo";
                Boolean isEditable = data.getBooleanExtra("isEditable", false);
                if (isEditable) {
                    note.id = data.getIntExtra("id", 1);
                    noteDAO.update(note);
                    int position = data.getIntExtra("position", 0);
                    noteAdapter.remove(noteAdapter.getItem(position));
                    noteAdapter.insert(note, position);
                } else {
                    noteDAO.insert(note);
                    noteAdapter.add(note);

                }
            }
            break;
            case 3:
            {
                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");
                String password = data.getStringExtra("password");


                Note note = new Note();
                note.title = title;
                note.path = path;
                note.changeDate = changeDate;
                note.password=password;
                note.type = "list";
                Boolean isEditable = data.getBooleanExtra("isEditable", false);
                if (isEditable) {
                    note.id = data.getIntExtra("id", 1);
                    noteDAO.update(note);
                    int position = data.getIntExtra("position", 0);
                    noteAdapter.remove(noteAdapter.getItem(position));
                    noteAdapter.insert(note, position);
                } else {
                    noteDAO.insert(note);
                    noteAdapter.add(note);

                }
            }
            break;
            case 4:
            {
                String title = data.getStringExtra("title");
                String path = data.getStringExtra("path");
                String changeDate = data.getStringExtra("changeDate");
                String password = data.getStringExtra("password");


                Note note = new Note();
                note.title = title;
                note.path = path;
                note.changeDate = changeDate;
                note.password=password;
                note.type = "sound";
                Boolean isEditable = data.getBooleanExtra("isEditable", false);
                if (isEditable) {
                    note.id = data.getIntExtra("id", 1);
                    noteDAO.update(note);
                    int position = data.getIntExtra("position", 0);
                    noteAdapter.remove(noteAdapter.getItem(position));
                    noteAdapter.insert(note, position);
                } else {
                    noteDAO.insert(note);
                    noteAdapter.add(note);

                }
            }
            break;
        }

        noteAdapter.notifyDataSetChanged();
        notesList.setAdapter(noteAdapter);

        UpdateCountNotes();

    }


    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.addnote_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_textnote: {
                        Intent intent = new Intent(getApplicationContext(), TextNoteActivity.class);
                        startActivityForResult(intent, 1);

                        return true;
                    }
                    case R.id.action_photonote: {
                        Intent intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
                        startActivityForResult(intent, 2);
                        return true;
                    }
                    case R.id.action_listnote: {
                        Intent intent = new Intent(getApplicationContext(), ListNoteActivity.class);
                        startActivityForResult(intent, 3);
                        return true;
                    }
                    case R.id.action_soundnote: {
                        Intent intent = new Intent(getApplicationContext(), SoundNoteActivity.class);
                        startActivityForResult(intent, 4);
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

}
