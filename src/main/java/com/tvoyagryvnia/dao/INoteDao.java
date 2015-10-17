package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.NoteEntity;

import java.util.List;

public interface INoteDao {
    NoteEntity getById(int id);
    void delete(NoteEntity note);
    void save(NoteEntity note);
    void update(NoteEntity note);
    List<NoteEntity> getAllOfUser(int user);
    List<NoteEntity> getAllOfUserByCategory(int user, int usercategory);
}
