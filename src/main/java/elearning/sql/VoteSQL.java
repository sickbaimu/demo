package elearning.sql;

import elearning.entity.RankRecord;

import java.sql.SQLException;
import java.util.ArrayList;

public class VoteSQL extends BaseSQL {
    public static ArrayList<RankRecord> GetRank(){
        //准备一个空的集合
        ArrayList<RankRecord> rankRecords = new ArrayList<>();
        //填入作品名
        try{
            init();
            rs = stmt.executeQuery("select *from work ");
            while(rs.next())
            {
                rankRecords.add(new RankRecord(rs.getString("name"),rs.getString("user_id"),""));
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }

        return rankRecords;
    }
}
