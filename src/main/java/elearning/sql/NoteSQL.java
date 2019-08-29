package elearning.sql;


import java.sql.SQLException;

public class NoteSQL extends BaseSQL {

    public static String AddNote(String userID, String type, String title, String tag, String content) {
        try{
            Class.forName(jdbc);
            init();
            String sql = "insert into note(user_id,shape,title,tag,content)VALUES('"+userID+"','"+type+"','"+title+"','"+tag+"','"+content+"');";
            stmt.executeUpdate(sql);
            closeUpdate();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
            return "-1";
        }
        return "0";
    }
}
