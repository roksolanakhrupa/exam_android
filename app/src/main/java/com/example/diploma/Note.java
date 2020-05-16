//package com.example.diploma;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//public class Note implements Serializable {
//    public String title;
//    public String content;
//    public ArrayList<String> photos;
//
//    public boolean isChecked;//?
//
//    public String changeDate;
//    public String createDate;
//
//    public Note(String title, String content, ArrayList<String> photos, boolean isChecked, String changeDate, String createDate) {
//        this.title = title;
//        this.content = content;
//        this.photos = photos;
//        this.isChecked = isChecked;
//        this.changeDate = changeDate;
//        this.createDate = createDate;
//    }
//
//    public Note() {
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public ArrayList<String> getPhotos() {
//        return photos;
//    }
//
//    public void setPhotos(ArrayList<String> photos) {
//        this.photos = photos;
//    }
//
//    public boolean isChecked() {
//        return isChecked;
//    }
//
//    public void setChecked(boolean checked) {
//        isChecked = checked;
//    }
//
//    public String getChangeDate() {
//        return changeDate;
//    }
//
//    public void setChangeDate(String changeDate) {
//        this.changeDate = changeDate;
//    }
//
//    public String getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(String createDate) {
//        this.createDate = createDate;
//    }
//}


package com.example.diploma;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String path;
    public String type; //text, photo, list, sound

    public String changeDate;
    public String password;

    public Note(long id, String title, String path, String type, String changeDate, String password) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.type = type;
        this.changeDate = changeDate;
        this.password = password;
    }

    @Ignore
    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
