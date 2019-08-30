package elearning.controller;


import elearning.entity.Question;
import elearning.sql.ExamSQL;
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
        ArrayList<Question> questions = ExamSQL.GetQuestions();
        if(Type.equals("Random"))
            Collections.shuffle(questions);
        return questions;
    }

    @ResponseBody
    @RequestMapping("/CollectQuestion")
    public String CollectQuestion(String userID,String questionID){
        return ExamSQL.AddCollection(userID,questionID);
    }

    @ResponseBody
    @RequestMapping("/GetQuestionCollection")
    public ArrayList<Question> GetQuestionCollection(String userID){
        ArrayList<String> questionID = ExamSQL.GetCollection(userID);
        ArrayList<Question> allQuestions = ExamSQL.GetQuestions();
        ArrayList<Question> collectionQuestions = new ArrayList<>();
        for(Question question:allQuestions){
            if(questionID.contains(question.getId()))
                collectionQuestions.add(question);
        }
        return collectionQuestions;
    }

    @ResponseBody
    @RequestMapping("/AddScore")
    public String AddScore(String userID,String score,String time,String rate,String errorNum,String day){
        return ExamSQL.AddScore(userID,score,time,rate,errorNum,day);
    }

    @ResponseBody
    @RequestMapping("GetMyScore")
    public String GetMyScore(String userID){
        return ExamSQL.GetMyScore(userID);
    }
}
