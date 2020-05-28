////package com.example.diploma;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import android.content.Intent;
////import android.graphics.Color;
////import android.graphics.drawable.ColorDrawable;
////import android.os.Bundle;
////import android.view.View;
////import android.widget.AdapterView;
////import android.widget.LinearLayout;
////import android.widget.TextView;
////import android.widget.ImageButton;
////import android.widget.Toast;
////
////import com.baoyz.swipemenulistview.SwipeMenu;
////import com.baoyz.swipemenulistview.SwipeMenuCreator;
////import com.baoyz.swipemenulistview.SwipeMenuItem;
////import com.baoyz.swipemenulistview.SwipeMenuListView;
////
////import java.util.ArrayList;
////
////public class HomeActivity extends AppCompatActivity {
////
////    public ArrayList<Note> notes = new ArrayList();
////    public ArrayList<Note> pinNotes = new ArrayList();
////    public NoteAdapter noteAdapter;
////    public NoteAdapter pinNoteAdapter;
////    public SwipeMenuListView notesList;
////    public SwipeMenuListView pinNotesList;
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_home);
////
////
////        setInitialData();
////        notesList = findViewById(R.id.notesList);
////        noteAdapter = new NoteAdapter(this, R.layout.list_item, notes);
////        notesList.setAdapter(noteAdapter);
////
////        pinNotesList = findViewById(R.id.pinNotesList);
////        pinNoteAdapter = new NoteAdapter(this, R.layout.list_item, pinNotes);
////        pinNotesList.setAdapter(pinNoteAdapter);
////
////        UpdateCountNotes();
////
////        ImageButton btnAddNote = findViewById(R.id.btnAddNote);
////        btnAddNote.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(v.getContext(), NoteActivity.class);
////                startActivityForResult(intent, 1);
////            }
////        });
////
////
////        SwipeMenuCreator creator = new SwipeMenuCreator() {
////            @Override
////            public void create(SwipeMenu menu) {
////                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
////                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
////                deleteItem.setWidth(150);
////                deleteItem.setIcon(R.mipmap.ic_delete);
////                menu.addMenuItem(deleteItem);
////
////                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
////                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
////                pinItem.setWidth(150);
////                pinItem.setIcon(R.mipmap.ic_pin);
////                menu.addMenuItem(pinItem);
////            }
////        };
////
////        final SwipeMenuCreator pinCreator = new SwipeMenuCreator() {
////            @Override
////            public void create(SwipeMenu menu) {
////                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
////                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
////                deleteItem.setWidth(150);
////                deleteItem.setIcon(R.mipmap.ic_delete);
////                menu.addMenuItem(deleteItem);
////
////                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
////                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
////                pinItem.setWidth(150);
////                pinItem.setIcon(R.mipmap.ic_unpin);
////                menu.addMenuItem(pinItem);
////            }
////        };
////
////
////
////        notesList.setMenuCreator(creator);
////        pinNotesList.setMenuCreator(pinCreator);
////
////        pinNotesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
////                switch (index) {
////                    case 0:
////                        pinNoteAdapter.remove(pinNotes.get(position));
////                        pinNoteAdapter.notifyDataSetChanged();
////                        pinNotesList.setAdapter(pinNoteAdapter);
////                        UpdateCountNotes();
////
////                        if (pinNotes.size() == 0) {
////                            View line = findViewById(R.id.line);
////                            line.setBackgroundResource(R.color.colorWhite);
////
////                            TextView pinnedText=findViewById(R.id.tv_pinned);
////                            pinnedText.setVisibility(View.GONE);
////                        }
////                        break;
////                    case 1:
////                        noteAdapter.add(pinNotes.get(position));
////                        noteAdapter.notifyDataSetChanged();
////                        notesList.setAdapter(noteAdapter);
////
////                        pinNoteAdapter.remove(pinNotes.get(position));
////                        pinNoteAdapter.notifyDataSetChanged();
////                        pinNotesList.setAdapter(pinNoteAdapter);
////
////                        if (pinNotes.size() == 0) {
////                            View line = findViewById(R.id.line);
////                            line.setBackgroundResource(R.color.colorWhite);
////
////                            TextView pinnedText=findViewById(R.id.tv_pinned);
////                            pinnedText.setVisibility(View.GONE);
////                        }
////                        break;
////                }
////                return false;
////            }
////        });
////        notesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
////                switch (index) {
////                    case 0:
////                        noteAdapter.remove(notes.get(position));
////                        noteAdapter.notifyDataSetChanged();
////                        notesList.setAdapter(noteAdapter);
////                        UpdateCountNotes();
////                        break;
////                    case 1:
////                        if (pinNotes.size() == 0) {
////                            View line = findViewById(R.id.line);
////                            line.setBackgroundResource(R.color.colorGrey);
////
////                            TextView pinnedText=findViewById(R.id.tv_pinned);
////                           pinnedText.setVisibility(View.VISIBLE);
////                        }
////                        if (pinNotes.size() == 3)
////                            Toast.makeText(getApplicationContext(), "You can not pin no more", Toast.LENGTH_LONG).show();
////                        else {
////                            pinNotes.add(notes.get(position));
////                            pinNoteAdapter.notifyDataSetChanged();
////                            pinNotesList.setAdapter(pinNoteAdapter);
////
////                            noteAdapter.remove(notes.get(position));
////                            noteAdapter.notifyDataSetChanged();
////                            notesList.setAdapter(noteAdapter);
////                        }
////                        break;
////                }
////                return false;
////            }
////        });
////
////
////        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Note noteToEdit = notes.get(position);
////
////                Intent intent = new Intent(view.getContext(), NoteActivity.class);
////                intent.putExtra("id", position);
////                intent.putExtra("title", noteToEdit.title);
////                intent.putExtra("content", noteToEdit.content);
////                intent.putExtra("createDate", noteToEdit.changeDate);
////                intent.putStringArrayListExtra("photos", noteToEdit.photos);
////                startActivityForResult(intent, 1);
////
////
////            }
////        });
////
////
////    }
////
////
////    private void setInitialData() {
////        notes.add(new Note("test1", "test test", new ArrayList<String>(), false, "01.01.2020", "01.01.2020"));
////        notes.add(new Note("test2", "test test", new ArrayList<String>(), false, "01.01.2020", "01.01.2020"));
////        notes.add(new Note("test3", "test test", new ArrayList<String>(), false, "01.01.2020", "01.01.2020"));
////    }
////
////    private void UpdateCountNotes() {
////        TextView countNotes = findViewById(R.id.countNotes);
////        countNotes.setText("Count: " + String.valueOf(notes.size()+pinNotes.size()));
////    }
////
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        if (data == null) {
////            return;
////        }
////        switch (requestCode) {
////            case 1: {
////                String title = data.getStringExtra("title");
////                String content = data.getStringExtra("content");
////                ArrayList<String> photos=data.getStringArrayListExtra("photos");
////                ArrayList<String> photosTmp=data.getStringArrayListExtra("photosTmp");
////                if (!title.isEmpty() || !content.isEmpty()) {
////                    String createDate = data.getStringExtra("createDate");
////                    Boolean isEditable = data.getBooleanExtra("isEditable", false);
////                    if (isEditable) {
////                        int id = data.getIntExtra("id", 0);
////                        Note newNote = new Note(title, content, photosTmp, false, createDate, createDate);
////                        noteAdapter.remove(notes.get(id));
////                        noteAdapter.insert(newNote, id);
////                    } else {
////                        Note newNote = new Note(title, content, photosTmp, false, createDate, createDate);
////                        noteAdapter.add(newNote);
////                        UpdateCountNotes();
////                    }
////                    noteAdapter.notifyDataSetChanged();
////                    notesList.setAdapter(noteAdapter);
////                }
////            }
////        }
////    }
////
////
////}
//
//
//package com.example.diploma;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Room;
//
//import android.app.Dialog;
//import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//
//import com.baoyz.swipemenulistview.SwipeMenu;
//import com.baoyz.swipemenulistview.SwipeMenuCreator;
//import com.baoyz.swipemenulistview.SwipeMenuItem;
//import com.baoyz.swipemenulistview.SwipeMenuListView;
//
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomeActivity extends AppCompatActivity {
//
//    public ArrayList<Note> notes = new ArrayList();
//  //  public SwipeMenuListView notesList;
////  public ListView notesList;
//    NoteAdapter noteAdapter;
//    private AppDatabase database;
//    private NoteDAO noteDAO;
//
//
//    public ArrayList<Note> pinNotes = new ArrayList();
//    public NoteAdapter pinNoteAdapter;
//    public SwipeMenuListView pinNotesList;
//
//
//
//    private NoteDataAdapter mAdapter;
//    SwipeController swipeController = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//
//
//        database = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database")
//                .allowMainThreadQueries()
//                .build();
//
//        noteDAO = database.noteDao();
//        notes = (ArrayList<Note>) noteDAO.getAll();
//     //   notesList = findViewById(R.id.notesList);
//        noteAdapter = new NoteAdapter(this, R.layout.list_item, notes);
//       // notesList.setAdapter(noteAdapter);
//
//
//        pinNotesList = findViewById(R.id.pinNotesList);
//        pinNoteAdapter = new NoteAdapter(this, R.layout.list_item, pinNotes);
//        pinNotesList.setAdapter(pinNoteAdapter);
//
//        Toast.makeText(getApplicationContext(), String.valueOf(noteDAO.getCount()), Toast.LENGTH_LONG).show();
//
//        UpdateCountNotes();
//
//        mAdapter = new NoteDataAdapter(notes);
//        setupRecyclerView();
//
//
//
////        swipeController = new SwipeController(new SwipeControllerActions() {
////            @Override
////            public void onRightClicked(int position) {
////                mAdapter.notes.remove(position);
////                mAdapter.notifyItemRemoved(position);
////                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
////            }
////        });
////
////        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
////        itemTouchhelper.attachToRecyclerView(recyclerView);
//
//
//
//
//
//        ImageButton btnAddNote = findViewById(R.id.btnAddNote);
//        btnAddNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                  showPopupMenu(v);
////                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
////                final View mView = getLayoutInflater().inflate(R.layout.activity_test, null);
////                mBuilder.setView(mView);
////                final AlertDialog dialog = mBuilder.create();
////
////                Window window = dialog.getWindow();
////                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////                window.setGravity(Gravity.CENTER);
////                WindowManager.LayoutParams wlp = window.getAttributes();
////
////                wlp.gravity = Gravity.BOTTOM;
////                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
////                window.setAttributes(wlp);
////
////                dialog.show();
//
//
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
//                final View mView = getLayoutInflater().inflate(R.layout.activity_add_note, null);
//                mBuilder.setView(mView);
//                final AlertDialog dialog = mBuilder.create();
//
////               mydialog.setContentView(R.layout.activity_test);
//
////                Window window = mydialog.getWindow();
////                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////                window.setGravity(Gravity.CENTER);
////                WindowManager.LayoutParams wlp = window.getAttributes();
////
////                wlp.gravity = Gravity.BOTTOM;
////                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
////                window.setAttributes(wlp);
//
//
//                Window window = dialog.getWindow();
//                WindowManager.LayoutParams wlp = window.getAttributes();
//
//                wlp.gravity = Gravity.CENTER;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                window.setAttributes(wlp);
//                window.setBackgroundDrawableResource(android.R.color.transparent);
//                ImageButton btnText=mView.findViewById(R.id.imgbtn_text);
//                btnText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(), TextNoteActivity.class);
//                        startActivityForResult(intent, 1);
//                        dialog.cancel();
//                    }
//                });
//
//                ImageButton btnPhoto=mView.findViewById(R.id.imgbtn_photo);
//                btnPhoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
//                        startActivityForResult(intent, 2);
//                        dialog.cancel();
//                    }
//                });
//
//                ImageButton btnList=mView.findViewById(R.id.imgbtn_list);
//                btnList.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(), ListNoteActivity.class);
//                        startActivityForResult(intent, 3);
//                        dialog.cancel();
//                    }
//                });
//
//                ImageButton btnSound=mView.findViewById(R.id.imgbtn_sound);
//                btnSound.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(), SoundNoteActivity.class);
//                        startActivityForResult(intent, 4);
//                        dialog.cancel();
//                    }
//                });
//
//                dialog.show();
////
//
//
//            }
//        });
//
//
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
//                deleteItem.setWidth(100);
//                deleteItem.setIcon(R.mipmap.ic_delete);
//                menu.addMenuItem(deleteItem);
//
//                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
//                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
//                pinItem.setWidth(100);
//                pinItem.setIcon(R.mipmap.ic_pin);
//                menu.addMenuItem(pinItem);
//
//                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
//                editItem.setBackground(R.color.colorPurple);
//                editItem.setWidth(100);
//                editItem.setIcon(R.mipmap.ic_edit);
//                menu.addMenuItem(editItem);
//            }
//        };
//
//        SwipeMenuCreator pinCreator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
//                deleteItem.setWidth(100);
//                deleteItem.setIcon(R.mipmap.ic_delete);
//                menu.addMenuItem(deleteItem);
//
//                SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
//                pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
//                pinItem.setWidth(100);
//                pinItem.setIcon(R.mipmap.ic_unpin);
//                menu.addMenuItem(pinItem);
//
//                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
//                editItem.setBackground(R.color.colorPurple);
//                editItem.setWidth(100);
//                editItem.setIcon(R.mipmap.ic_edit);
//                menu.addMenuItem(editItem);
//            }
//        };
//
//     //   notesList.setMenuCreator(creator);
//       // pinNotesList.setMenuCreator(pinCreator);
//
////        pinNotesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
////                switch (index) {
////                    case 0:
////                        //delete manually file
////                        noteDAO.delete(pinNotes.get(position));
////                        pinNoteAdapter.remove(pinNotes.get(position));
////                        pinNoteAdapter.notifyDataSetChanged();
////                        UpdateCountNotes();
////
////                        if (pinNotes.size() == 0) {
////                            View line = findViewById(R.id.line);
////                            line.setBackgroundResource(R.color.colorWhite);
////
////                            TextView pinnedText = findViewById(R.id.tv_pinned);
////                            pinnedText.setVisibility(View.GONE);
////                        }
////                        break;
////                    case 1:
////                        noteAdapter.add(pinNotes.get(position));
////                        noteAdapter.notifyDataSetChanged();
////                        notesList.setAdapter(noteAdapter);
////
////                        pinNoteAdapter.remove(pinNotes.get(position));
////                        pinNoteAdapter.notifyDataSetChanged();
////                        pinNotesList.setAdapter(pinNoteAdapter);
////
////                        if (pinNotes.size() == 0) {
////                            View line = findViewById(R.id.line);
////                            line.setBackgroundResource(R.color.colorWhite);
////
////                            TextView pinnedText = findViewById(R.id.tv_pinned);
////                            pinnedText.setVisibility(View.GONE);
////                        }
////                        break;
////                    case 2: {
////                        Note noteToEdit = pinNotes.get(position);
////                        Intent intent = null;
////                        int requestCode = 0;
////                        switch (noteToEdit.type) {
////                            case "text":
////                                intent = new Intent(getApplicationContext(), TextNoteActivity.class);
////                                requestCode = 1;
////                                break;
////                            case "photo":
////                                intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
////                                requestCode = 2;
////                                break;
////                            case "list":
////                                intent = new Intent(getApplicationContext(), ListNoteActivity.class);
////                                requestCode = 3;
////                                break;
////                            case "sound":
////                                intent = new Intent(getApplicationContext(), SoundNoteActivity.class);
////                                requestCode = 4;
////                                break;
////                        }
////                        intent.putExtra("id", noteToEdit.id);
////                        intent.putExtra("title", noteToEdit.title);
////                        intent.putExtra("path", noteToEdit.path);
////                        intent.putExtra("createDate", noteToEdit.changeDate);
////                        intent.putExtra("position", position);
////                        startActivityForResult(intent, requestCode);
////
////
////                    }
////                    break;
////                }
////                return false;
////            }
////        });
////        notesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
////                switch (index) {
////                    case 0:
////                        //delete manually file
////                        noteDAO.delete(notes.get(position));
////                        noteAdapter.remove(notes.get(position));
////                        noteAdapter.notifyDataSetChanged();
////                        notesList.setAdapter(noteAdapter);
////                        UpdateCountNotes();
////                        break;
////                    case 1:
////                        if (pinNotes.size() == 0) {
////                            View line = findViewById(R.id.line);
////                            line.setBackgroundResource(R.color.colorGrey);
////
////                            TextView pinnedText = findViewById(R.id.tv_pinned);
////                            pinnedText.setVisibility(View.VISIBLE);
////                        }
////                        if (pinNotes.size() == 3)
////                            Toast.makeText(getApplicationContext(), "You can not pin no more", Toast.LENGTH_LONG).show();
////                        else {
////                            pinNotes.add(notes.get(position));
////                            pinNoteAdapter.notifyDataSetChanged();
////                            pinNotesList.setAdapter(pinNoteAdapter);
////
////                            noteAdapter.remove(notes.get(position));
////                            noteAdapter.notifyDataSetChanged();
////                            notesList.setAdapter(noteAdapter);
////                        }
////                        break;
////                    case 2: {
////                        Note noteToEdit = notes.get(position);
////                        Intent intent = null;
////                        int requestCode = 0;
////                        switch (noteToEdit.type) {
////                            case "text":
////                                intent = new Intent(getApplicationContext(), TextNoteActivity.class);
////                                requestCode = 1;
////                                break;
////                            case "photo":
////                                intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
////                                requestCode = 2;
////                                break;
////                            case "list":
////                                intent = new Intent(getApplicationContext(), ListNoteActivity.class);
////                                requestCode = 3;
////                                break;
////                            case "sound":
////                                intent = new Intent(getApplicationContext(), SoundNoteActivity.class);
////                                requestCode = 4;
////                                break;
////                        }
////
////                        intent.putExtra("id", noteToEdit.id);
////                        intent.putExtra("title", noteToEdit.title);
////                        intent.putExtra("path", noteToEdit.path);
////                        intent.putExtra("createDate", noteToEdit.changeDate);
////                        intent.putExtra("position", position);
////                        startActivityForResult(intent, requestCode);
////
////                    }
////                    break;
////                }
////                return false;
////            }
////        });
//
//
//        pinNotesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Note noteToEdit = pinNotes.get(position);
//                Intent intent = null;
//                int requestCode = 0;
//                switch (noteToEdit.type) {
//                    case "text":
//                        intent = new Intent(view.getContext(), ViewTextNoteActivity.class);
//                        requestCode = 1;
//                        break;
//                    case "photo":
//                        intent = new Intent(view.getContext(), ViewPhotoNoteActivity.class);
//                        requestCode = 2;
//                        break;
//                    case "list":
//                        intent = new Intent(view.getContext(), ViewListNoteActivity.class);
//                        requestCode = 3;
//                        break;
//                    case "sound":
//                        intent = new Intent(view.getContext(), ViewSoundNoteActivity.class);
//                        requestCode = 4;
//                        break;
//                }
//
//
//                intent.putExtra("id", noteToEdit.id);
//                intent.putExtra("title", noteToEdit.title);
//                intent.putExtra("path", noteToEdit.path);
//                intent.putExtra("createDate", noteToEdit.changeDate);
//                intent.putExtra("password", noteToEdit.password);
//                intent.putExtra("position", position);
//                startActivityForResult(intent, requestCode);
//
//            }
//        });
//
////        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Note noteToEdit = notes.get(position);
////                Intent intent = null;
////                int requestCode = 0;
////                switch (noteToEdit.type) {
////                    case "text":
////                        intent = new Intent(view.getContext(), ViewTextNoteActivity.class);
////                        requestCode = 1;
////                        break;
////                    case "photo":
////                        intent = new Intent(view.getContext(), ViewPhotoNoteActivity.class);
////                        requestCode = 2;
////                        break;
////                    case "list":
////                        intent = new Intent(view.getContext(), ViewListNoteActivity.class);
////                        requestCode = 3;
////                        break;
////                    case "sound":
////                        intent = new Intent(view.getContext(), ViewSoundNoteActivity.class);
////                        requestCode = 4;
////                        break;
////                }
////                intent.putExtra("id", noteToEdit.id);
////                intent.putExtra("title", noteToEdit.title);
////                intent.putExtra("path", noteToEdit.path);
////                intent.putExtra("createDate", noteToEdit.changeDate);
////                intent.putExtra("password", noteToEdit.password);
////                intent.putExtra("position", position);
////                startActivityForResult(intent, requestCode);
////
////
////            }
////        });
//
//    }
//
//
//    private void UpdateCountNotes() {
//        TextView countNotes = findViewById(R.id.countNotes);
//        int allCount = notes.size() + pinNotes.size();
//        countNotes.setText("Count: " + allCount);
//    }
//
//
////    private void setPlayersDataAdapter() {
////        List<Note> players = new ArrayList<>();
////        try {
////            InputStreamReader is = new InputStreamReader(getAssets().open("players.csv"));
////
////            BufferedReader reader = new BufferedReader(is);
////            reader.readLine();
////            String line;
////            String[] st;
////            while ((line = reader.readLine()) != null) {
////                st = line.split(",");
////                Player player = new Player();
////                player.setName(st[0]);
////                player.setNationality(st[1]);
////                player.setClub(st[4]);
////                player.setRating(Integer.parseInt(st[9]));
////                player.setAge(Integer.parseInt(st[14]));
////                players.add(player);
////            }
////        } catch (IOException e) {
////
////        }
////
////
////    }
//
////    private void setupRecyclerView() {
////        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.notesList);
////
////        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
////        recyclerView.setAdapter(mAdapter);
////    }
//
//
//    private void setupRecyclerView() {
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.notesList);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(mAdapter);
//
//        swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
//                mAdapter.notes.remove(position);
//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
//            }
//        });
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(recyclerView);
//
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null) {
//            return;
//        }
//        String title = data.getStringExtra("title");
//        String path = data.getStringExtra("path");
//        String changeDate = data.getStringExtra("changeDate");
//        String password = data.getStringExtra("password");
//
//
//        Note note = new Note();
//        note.title = title;
//        note.path = path;
//        note.changeDate = changeDate;
//        note.password = password;
//
//        switch (requestCode) {
//            case 1:
//                note.type = "text";
//                break;
//            case 2:
//                note.type = "photo";
//                break;
//            case 3:
//                note.type = "list";
//                break;
//            case 4:
//                note.type = "sound";
//                break;
//        }
//        Boolean isEditable = data.getBooleanExtra("isEditable", false);
//        if (isEditable) {
//            note.id = data.getIntExtra("id", 1);
//            noteDAO.update(note);
//            int position = data.getIntExtra("position", 0);
//
//            noteAdapter.remove(noteAdapter.getItem(position));
//            noteAdapter.insert(note, position);
//
//        } else {
//            noteDAO.insertByFields(title, path, note.type, changeDate, password);
////                    noteDAO.insert(note);
////                    ((List<Note>)noteDAO.getAll()).indexOf(note);
////                    noteDAO.getById()
//            Note n = noteDAO.getAll().get(noteDAO.getCount() - 1);
//            Toast.makeText(getApplicationContext(), String.valueOf(n.id), Toast.LENGTH_SHORT).show();
//            noteAdapter.add(n);
//
//        }
//       // notesList.
//        noteAdapter.notifyDataSetChanged();
////        notesList.setAdapter(noteAdapter);
//
//        UpdateCountNotes();
//
//    }
//
//
////    private void showPopupMenu(View v) {
////        PopupMenu popupMenu = new PopupMenu(this, v);
////        popupMenu.inflate(R.menu.addnote_menu);
////
////        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(MenuItem item) {
////                switch (item.getItemId()) {
////                    case R.id.action_textnote: {
////                        Intent intent = new Intent(getApplicationContext(), TextNoteActivity.class);
////                        startActivityForResult(intent, 1);
////
////                        return true;
////                    }
////                    case R.id.action_photonote: {
////                        Intent intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
////                        startActivityForResult(intent, 2);
////                        return true;
////                    }
////                    case R.id.action_listnote: {
////                        Intent intent = new Intent(getApplicationContext(), ListNoteActivity.class);
////                        startActivityForResult(intent, 3);
////                        return true;
////                    }
////                    case R.id.action_soundnote: {
////                        Intent intent = new Intent(getApplicationContext(), SoundNoteActivity.class);
////                        startActivityForResult(intent, 4);
////                        return true;
////                    }
////                    default:
////                        return false;
////                }
////            }
////        });
////        popupMenu.show();
////    }
//
//}


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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.diploma.database.DatabaseHandler;
import com.example.diploma.database.NoteCursorAdapter;
import com.example.diploma.edit.ListNoteActivity;
import com.example.diploma.edit.PhotoNoteActivity;
import com.example.diploma.edit.SoundNoteActivity;
import com.example.diploma.edit.TextNoteActivity;
import com.example.diploma.view.ViewListNoteActivity;
import com.example.diploma.view.ViewPhotoNoteActivity;
import com.example.diploma.view.ViewSoundNoteActivity;
import com.example.diploma.view.ViewTextNoteActivity;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {


//    private AppDatabase database;
//    private NoteDAO noteDAO;


    private DatabaseHandler db;
    private NoteCursorAdapter noteCursorAdapter;
    private NoteCursorAdapter pinNoteCursorAdapter;


    //    private RecyclerView recyclerView;
//    private AlertDialog.Builder alertDialog;
//    private Paint p = new Paint();


    public ArrayList<Note> notes = new ArrayList();
    public ArrayList<Note> pinNotes = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        db = DatabaseHandler.getmInstance(getApplicationContext());

        SwipeMenuListView notesList = findViewById(R.id.notesList);
        final SwipeMenuListView pinNotesList = findViewById(R.id.pinNotesList);

        Cursor cursorNotes = db.getReverseNoteList(0);
        Cursor cursorPinNotes = db.getReverseNoteList(1);
        if (cursorNotes != null || cursorPinNotes != null) {
            noteCursorAdapter = new NoteCursorAdapter(getApplicationContext(), cursorNotes, 0);
            pinNoteCursorAdapter = new NoteCursorAdapter(getApplicationContext(), cursorPinNotes, 0);
            notesList.setAdapter(noteCursorAdapter);
            pinNotesList.setAdapter(pinNoteCursorAdapter);
        }

        //   Collections.reverse(notes);
        notes = db.getReverseNoteArrayList(0);
        pinNotes = db.getReverseNoteArrayList(1);

        //  Collections.reverse(notes);
        // Collections.reverse(pinNotes);

//        notesList.get


        if (pinNotes.size() > 0) {
            // View line = findViewById(R.id.line);
            //  line.setBackgroundResource(R.color.colorGrey);
            setMargins(pinNotesList, 0, 0, 0, 30);

            TextView pinnedText = findViewById(R.id.tv_pinned);
            pinnedText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            pinnedText.setVisibility(View.VISIBLE);
        }
//        database = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database")
//                .allowMainThreadQueries()
//                .build();
//
//        noteDAO = database.noteDao();
//        notes = (ArrayList<Note>) noteDAO.getAll();
//
//        noteAdapter = new NoteDataAdapter(notes);
//
//        setupRecyclerView();
        //   notesList = findViewById(R.id.notesList);
//        noteAdapter = new NoteAdapter(this, R.layout.list_item, notes);
//        // notesList.setAdapter(noteAdapter);
//
//
//        pinNotesList = findViewById(R.id.pinNotesList);
//        pinNoteAdapter = new NoteAdapter(this, R.layout.list_item, pinNotes);
//        pinNotesList.setAdapter(pinNoteAdapter);


        UpdateCountNotes();


        Button btnAdd = (Button) findViewById(R.id.btnAddNote);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.activity_add_note, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();


                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                window.setBackgroundDrawableResource(android.R.color.transparent);
                ImageButton btnText = mView.findViewById(R.id.imgbtn_text);
                btnText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), TextNoteActivity.class);
                        startActivityForResult(intent, 1);
                        dialog.cancel();
                    }
                });

                ImageButton btnPhoto = mView.findViewById(R.id.imgbtn_photo);
                btnPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), PhotoNoteActivity.class);
                        startActivityForResult(intent, 2);
                        dialog.cancel();
                    }
                });

                ImageButton btnList = mView.findViewById(R.id.imgbtn_list);
                btnList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ListNoteActivity.class);
                        startActivityForResult(intent, 3);
                        dialog.cancel();
                    }
                });

                ImageButton btnSound = mView.findViewById(R.id.imgbtn_sound);
                btnSound.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SoundNoteActivity.class);
                        startActivityForResult(intent, 4);
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });



    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
            deleteItem.setWidth(150);
            deleteItem.setIcon(R.mipmap.ic_delete);
            menu.addMenuItem(deleteItem);

            SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
            pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
            pinItem.setWidth(150);
            pinItem.setIcon(R.mipmap.ic_pin);
            menu.addMenuItem(pinItem);
        }
    };

    SwipeMenuCreator pinCreator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00, 0x00)));
            deleteItem.setWidth(150);
            deleteItem.setIcon(R.mipmap.ic_delete);
            menu.addMenuItem(deleteItem);

            SwipeMenuItem pinItem = new SwipeMenuItem(getApplicationContext());
            pinItem.setBackground(new ColorDrawable(Color.rgb(0x80, 0x80, 0x80)));
            pinItem.setWidth(150);
            pinItem.setIcon(R.mipmap.ic_unpin);
            menu.addMenuItem(pinItem);
        }
    };


        notesList.setMenuCreator(creator);
        pinNotesList.setMenuCreator(pinCreator);

        pinNotesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()

    {
        @Override
        public boolean onMenuItemClick ( int position, SwipeMenu menu,int index){
        switch (index) {
            case 0: {
//                        pinNoteAdapter.remove(pinNotes.get(position));
//                        pinNoteAdapter.notifyDataSetChanged();
//                        pinNotesList.setAdapter(pinNoteAdapter);
//                        UpdateCountNotes();
                Note n = pinNotes.get(position);

                db.deleteById(n.id);
                pinNotes.remove(position);

                pinNoteCursorAdapter.swapCursor(db.getReverseNoteList(1));
                UpdateCountNotes();
                if (pinNotes.size() == 0) {
                    //View line = findViewById(R.id.line);
                    // line.setBackgroundResource(R.color.colorWhite);
                    setMargins(pinNotesList, 0, 0, 0, 30);

                    TextView pinnedText = findViewById(R.id.tv_pinned);
                    pinnedText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    pinnedText.setVisibility(View.GONE);
                }

                if (pinNotes.size() == 0 && notes.size() == 0) {
                    setMargins(pinNotesList, 0, 0, 0, 30);
                }

            }
            break;


            case 1:
//                        noteAdapter.add(pinNotes.get(position));
//                        noteAdapter.notifyDataSetChanged();
//                        notesList.setAdapter(noteAdapter);
//
//                        pinNoteAdapter.remove(pinNotes.get(position));
//                        pinNoteAdapter.notifyDataSetChanged();
//                        pinNotesList.setAdapter(pinNoteAdapter);

                Note n = pinNotes.get(position);
//                        long id = pinNotes.get(position).id;
                db.updatePinStatus(n.id, 0);
                //pinNotes.remove();

//                        notes.add(n);
//                        pinNotes.remove(position);

                notes = db.getReverseNoteArrayList(0);
                pinNotes = db.getReverseNoteArrayList(1);


//                        Collections.reverse(notes);
//                        Collections.reverse(pinNotes);

                if (pinNotes.size() == 0) {
                    //   View line = findViewById(R.id.line);
                    //  line.setBackgroundResource(R.color.colorWhite);
                    setMargins(pinNotesList, 0, 0, 0, 0);

                    TextView pinnedText = findViewById(R.id.tv_pinned);

                    pinnedText.setVisibility(View.GONE);
                }
                noteCursorAdapter.swapCursor(db.getReverseNoteList(0));
                pinNoteCursorAdapter.swapCursor(db.getReverseNoteList(1));
                break;
        }
        return false;
    }
    });
        notesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()

    {
        @Override
        public boolean onMenuItemClick ( int position, SwipeMenu menu,int index){
        switch (index) {
            case 0: {
                Note n = notes.get(position);

                db.deleteById(n.id);
                notes.remove(position);

                noteCursorAdapter.swapCursor(db.getReverseNoteList(0));
                UpdateCountNotes();
            }
            break;
            case 1:
//                        if (pinNotes.size() == 0) {
//                            View line = findViewById(R.id.line);
//                            line.setBackgroundResource(R.color.colorGrey);
//
//                            TextView pinnedText=findViewById(R.id.tv_pinned);
//                            pinnedText.setVisibility(View.VISIBLE);
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
//
                if (pinNotes.size() == 0) {
                    //   View line = findViewById(R.id.line);
                    //    line.setBackgroundResource(R.color.colorGrey);
                    setMargins(pinNotesList, 0, 0, 0, 30);

                    TextView pinnedText = findViewById(R.id.tv_pinned);
                    pinnedText.setVisibility(View.VISIBLE);
                }
                if (pinNotes.size() == 3)
                    Toast.makeText(getApplicationContext(), "You can not pin no more", Toast.LENGTH_LONG).show();
                else {
                    Note n = notes.get(position);
                    db.updatePinStatus(n.id, 1);

//                            pinNotes.add(n);
//                            notes.remove(position);

                    notes = db.getReverseNoteArrayList(0);
                    pinNotes = db.getReverseNoteArrayList(1);

                    // Collections.reverse(notes);
                    // Collections.reverse(pinNotes);

                    noteCursorAdapter.swapCursor(db.getReverseNoteList(0));
                    pinNoteCursorAdapter.swapCursor(db.getReverseNoteList(1));

                    //    pinNotes.add(notes.get(position));
                    //pinNoteAdapter.notifyDataSetChanged();
                    //pinNotesList.setAdapter(pinNoteAdapter);
                    //  notes.remove(notes.get(position));

//                            noteAdapter.remove(notes.get(position));
//                            noteAdapter.notifyDataSetChanged();
//                            notesList.setAdapter(noteAdapter);
                }
                break;
        }
        return false;
    }
    });


        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
        Note noteToEdit = notes.get(position);
        OpenNewNote(position, view, noteToEdit);
    }
    });

        pinNotesList.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
        Note noteToEdit = pinNotes.get(position);
        OpenNewNote(position, view, noteToEdit);
    }
    });

}


    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    private void OpenNewNote(int position, View view, Note noteToEdit) {
        Intent intent = null;
        int requestCode = 0;
        switch (noteToEdit.type) {
            case "text":
                intent = new Intent(view.getContext(), ViewTextNoteActivity.class);
                requestCode = 1;
                break;
            case "photo":
                intent = new Intent(view.getContext(), ViewPhotoNoteActivity.class);
                requestCode = 2;
                break;
            case "list":
                intent = new Intent(view.getContext(), ViewListNoteActivity.class);
                requestCode = 3;
                break;
            case "sound":
                intent = new Intent(view.getContext(), ViewSoundNoteActivity.class);
                requestCode = 4;
                break;
        }
        intent.putExtra("id", noteToEdit.id);
        intent.putExtra("title", noteToEdit.title);
        intent.putExtra("path", noteToEdit.path);
        intent.putExtra("createDate", noteToEdit.changeDate);
        intent.putExtra("password", noteToEdit.password);
        intent.putExtra("position", position);
        startActivityForResult(intent, requestCode);

    }


    private void UpdateCountNotes() {
        TextView countNotes = findViewById(R.id.countNotes);
        int allCount = db.getNotelistCount();// + pinNotes.size();

        switch (allCount) {
            case 0:
                countNotes.setText("no notes");
                break;
            case 1:
                countNotes.setText("1 note");
                break;
            default:
                countNotes.setText(" " + allCount + " notes");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String title = data.getStringExtra("title");
        String path = data.getStringExtra("path");
        String changeDate = data.getStringExtra("changeDate");
        String password = data.getStringExtra("password");
        //   Integer isPin = data.getIntExtra("isPin", 0);


        Note note = new Note();
        note.title = title;
        note.path = path;
        note.changeDate = changeDate;
        note.password = password;

        switch (requestCode) {
            case 1:
                note.type = "text";
                break;
            case 2:
                note.type = "photo";
                break;
            case 3:
                note.type = "list";
                break;
            case 4:
                note.type = "sound";
                break;
        }
        Boolean isEditable = data.getBooleanExtra("isEditable", false);
        if (isEditable) {
            note.id = data.getLongExtra("id", 1);

            db.updateNoteById(note.id, title, path, note.type, changeDate, password);
            int position = data.getIntExtra("position", 0);
            notes.remove(notes.get(position));
            notes.add(0, note);
//            noteDAO.update(note);
//            int position = data.getIntExtra("position", 0);
//
//            noteAdapter.notes.remove(noteAdapter.notes.get(position));
//            noteAdapter.notes.add(position, note);

        } else {

            long id = db.insertIntoNote(title, path, note.type, changeDate, password);
            note.id = id;
            notes.add(0, note);
//            noteDAO.insertByFields(title, path, note.type, changeDate, password);
////                    noteDAO.insert(note);
////                    ((List<Note>)noteDAO.getAll()).indexOf(note);
////                    noteDAO.getById()
//            Note n = noteDAO.getAll().get(noteDAO.getCount() - 1);
//            noteAdapter.notes.add(n);

        }
        // notesList.
        //   noteAdapter.notifyDataSetChanged();
//        notesList.setAdapter(noteAdapter);
        noteCursorAdapter.swapCursor(db.getReverseNoteList(0));
        pinNoteCursorAdapter.swapCursor(db.getReverseNoteList(1));
        UpdateCountNotes();

    }


}
