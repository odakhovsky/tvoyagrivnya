package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.ChangeNoteSelectBean;
import com.tvoyagryvnia.bean.NoteBean;
import com.tvoyagryvnia.bean.currency.CurrencyBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.bean.user.UserFieldBean;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.service.INoteService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cabinet/organizer")
@SessionAttributes("userBean")
public class CabinetOrganizerController {

    @Autowired
    private INoteService noteService;
    @Autowired
    private IUserCategoryDao userCategoryDao;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index(ModelMap map, @ModelAttribute("userBean") UserBean user,
                        @RequestParam(value = "category", required = false, defaultValue = "0") Integer category) {
        List<NoteBean> notes;
        switch (category) {
            case 0:
                notes = noteService.getAllOfUser(user.getId());
                break;
            default:
                notes = noteService.getAllOfUserByCategory(user.getId(), category);
        }
        List<UserCategoryEntity> cats = userCategoryDao.getAll(user.getId(), true);
        map.addAttribute("notes", notes);
        map.addAttribute("cats", cats);
        return "cabinet/organizer";
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String create(@RequestParam("text") String text,
                        @RequestParam("category") Integer category,
                        @ModelAttribute("userBean") UserBean user) {
        noteService.create(text, category, user.getId());
        return "redirect:/cabinet/organizer/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(@RequestParam("noteId")Integer noteId,
                        @RequestParam("text") String text,
                        @RequestParam("category") Integer category,
                        @ModelAttribute("userBean") UserBean user) {
        NoteBean noteBean = noteService.getById(noteId);
        noteBean.setText(text);
        noteBean.setCategoryId(category);
        noteService.update(noteBean);
        return "redirect:/cabinet/organizer/note/" + noteId + "/info/";
    }

    @RequestMapping(value = "/notes/list", method = RequestMethod.GET)
    @ResponseBody
    public List<NoteBean> getUserNotes(@ModelAttribute("userBean") UserBean user) {
        return noteService.getAllOfUser(user.getId());
    }

    @RequestMapping(value = "/categories/list", method = RequestMethod.GET)
    @ResponseBody
    public List<ChangeNoteSelectBean> getUserCats(@ModelAttribute("userBean") UserBean user) {
        return userCategoryDao.getAll(user.getId(), true)
                .stream().map(ChangeNoteSelectBean::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/editField", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> editField(UserFieldBean field) {
        try {
            if (field.getName().equals("text")) {
                noteService.updateSingleField(field.getPk(), field.getName(), field.getValue());
            } else {
                if (StringUtils.isEmpty(field.getValue())) {
                    field.setValue("0");
                }

                noteService.updateSingleField(field.getPk(), field.getName(), field.getValue());
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @RequestMapping(value = "/note/{noteId}/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> removeNote(@PathVariable("noteId")Integer id) {
        noteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/note/{noteId}/info/", method = RequestMethod.GET)
    public String showpage(@PathVariable("noteId")Integer noteId,ModelMap map) {
        NoteBean note = noteService.getById(noteId);
        if (null != note) {
            map.addAttribute("note", note);
            List<UserCategoryEntity> cats = userCategoryDao.getAll(note.getOwner(), true);
            map.addAttribute("cats", cats);
            return "cabinet/organizer/note/info";
        }else {
            return "redirect:/cabinet/organizer/";
        }
    }

    @RequestMapping(value = "/note/view/", method = RequestMethod.GET)
    public String noteView(@ModelAttribute("userBean")UserBean user, ModelMap map) {
        List<NoteBean> notes = noteService.getLastFiveOfUser(user.getId());
        map.addAttribute("notes", notes);
        return "cabinet/organizer/note/view";
    }

}
