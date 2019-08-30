package elearning.sql;

import elearning.entity.Question;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExamSQL extends BaseSQL {
    public static ArrayList<Question> GetQuestions(){
        //准备一个空的集合
        ArrayList<Question> questions = new ArrayList<>();
        //填入作品名
        try{
            init();
            rs = stmt.executeQuery("select *from question ");
            while(rs.next())
            {
                questions.add(new Question(rs.getString("id"),rs.getInt("order"),rs.getString("type"),rs.getString("question"),rs.getString("answer")));
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return questions;
    }


    public static String AddCollection(String userID, String questionID) {
        try{
            init();
            String sql = "insert into collection(user_id,question_id)VALUES('"+userID+"','"+questionID+"');";
            stmt.executeUpdate(sql);
            closeUpdate();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
            return "-1";
        }
        return "0";
    }


    public static ArrayList<String> GetCollection(String userID) {
        ArrayList<String> questionsID = new ArrayList<>();
        try{
            init();
            rs = stmt.executeQuery("select *from collection where user_id = "+userID);
            while(rs.next())
            {
                questionsID.add(rs.getString("question_id"));
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return questionsID;
    }

    public static String AddScore(String userID, String score, String time, String rate, String errorNum, String day) {
        try{
            init();
            String sql = "insert into score(user_id,score,time,rate,errorNum,day)VALUES('"+userID+"','"+score+"','"+time+"','"+rate+"','"+errorNum+"','"+day+"');";
            stmt.executeUpdate(sql);
            closeUpdate();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
            return "-1";
        }
        return "0";
    }

    public static String GetMyScore(String userID) {
        System.out.println(userID);
        String score = "";
        try {
            init();
            rs = stmt.executeQuery("select *from score where user_id = "+userID);
            while (rs.next()){
                score = rs.getString("score")+"@"+
                        rs.getString("time")+"@"+
                        rs.getString("rate")+"@"+
                        rs.getString("errorNum")+"@"+
                        rs.getString("day");
            }
            System.out.println(score);
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return score;
    }
}
