package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.NoteBean;
import com.tvoyagryvnia.dao.INoteDao;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.dao.IUserCurrencyDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.CurrencyEntity;
import com.tvoyagryvnia.model.NoteEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteService implements INoteService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserCategoryDao userCategoryDao;
    @Autowired
    private INoteDao noteDao;
    @Autowired private IUserCurrencyDao userCurrencyDao;

    @Override
    public void create(String text, int usercategory, int owner, float sum, int currency) {
        UserCategoryEntity category = userCategoryDao.getById(usercategory);
        UserEntity user = userDao.getUserById(owner);
        if (Objects.nonNull(user)) {
            NoteEntity note = new NoteEntity();
            note.setActive(true);
            note.setOwner(user);
            note.setCategory(category);
            note.setDate(new Date());
            note.setText(text);
            if (sum > 0){
                note.setSum(sum);
                note.setCurrency(userCurrencyDao.getById(currency));
            }
            noteDao.save(note);
        }
    }

    @Override
    public void update(NoteBean noteBean) {
        UserCategoryEntity category = userCategoryDao.getById(noteBean.getCategoryId());
        NoteEntity entity = noteDao.getById(noteBean.getId());
        if (null != category && category.getId() != entity.getCategory().getId()) {
            entity.setCategory(category);
        }
        entity.setText(noteBean.getText());
        noteDao.update(entity);
    }

    @Override
    public void delete(int note) {
        noteDao.delete(noteDao.getById(note));
    }

    @Override
    public NoteBean getById(int id) {
        return new NoteBean(noteDao.getById(id));
    }

    @Override
    public List<NoteBean> getAllOfUser(int userId) {
        List<NoteBean> notes = noteDao.getAllOfUser(userId)
                .stream().map(NoteBean::new)
                .collect(Collectors.toList());
        return notes;
    }

    @Override
    public List<NoteBean> getAllOfUserByCategory(int user, int category) {
        return noteDao.getAllOfUserByCategory(user, category)
                .stream().map(NoteBean::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateSingleField(int noteId, String fieldName, String fielValue) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        NoteEntity note = noteDao.getById(noteId);
        if (fieldName.equals("category")) {
            int val = Integer.valueOf(fielValue);
            UserCategoryEntity categoryEntity = userCategoryDao.getById(val);
            note.setCategory(categoryEntity);
        } else {
            Method setter = new PropertyDescriptor(fieldName, note.getClass()).getWriteMethod();
            setter.invoke(note, fielValue);
        }
        noteDao.update(note);
    }

    @Override
    public List<NoteBean> getLastFiveOfUser(int user) {
        return getAllOfUser(user).stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .limit(5).collect(Collectors.toList());
    }
}
