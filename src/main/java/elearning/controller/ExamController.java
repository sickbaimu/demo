package elearning.controller;


import elearning.entity.Question;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Collections;


/**
 * @author lele
 * @version 1.00
 * @description 处理考试请求
 * @Date 2019/7/11 13:27
 */
@Controller
public class ExamController {

    @ResponseBody
    @RequestMapping("/GetExam")
    public ArrayList<Question> getExam(String Type){
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("1",1,"Radio","1+1=?@1@2@3@4","B"));
        questions.add(new Question("2",2,"Radio","1+2=?@1@2@3@4","C"));
        questions.add(new Question("3",3,"Check","偶数?@1@2@3@4","BD"));
        questions.add(new Question("4",4,"Fill","1+_=2?","1"));
        if(Type.equals("Random"))
            Collections.shuffle(questions);
        return questions;
    }
}
