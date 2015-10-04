package com.tvoyagryvnia.service;

import com.tvoyagryvnia.bean.NoteBean;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface INoteService {

    void create(String text, int usercategory, int owner);
    void update(NoteBean noteBean);
    void delete(int note);
    NoteBean getById(int id);
    List<NoteBean> getAllOfUser(int userId);
    List<NoteBean> getAllOfUserByCategory(int user, int category);
    public void updateSingleField(int noteId, String fieldName, String fielValue)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException;

}
