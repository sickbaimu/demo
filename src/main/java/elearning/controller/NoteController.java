package elearning.controller;

import elearning.sql.NoteSQL;
import elearning.sql.UserSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NoteController {
    @ResponseBody
    @RequestMapping("/AddNote")
    public String AddNote(String userID,String type,String title,String TAG,String content){
       return NoteSQL.AddNote(userID,type,title,TAG,content);
    }
}
