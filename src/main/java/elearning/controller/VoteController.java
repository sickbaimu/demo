package elearning.controller;

import elearning.entity.RankRecord;
import elearning.sql.UserSQL;
import elearning.sql.VoteSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static elearning.sql.BaseSQL.ListToString;
import static elearning.sql.LearnSQL.getNameList;

@Controller
public class VoteController {
    @ResponseBody
    @RequestMapping("/GetWorkNames")
    public String GetWorkNames(){
        return ListToString(getNameList("work","name",null));
    }

    @ResponseBody
    @RequestMapping("/GetWorkIDs")
    public String GetWorkIDs(){
        return ListToString(getNameList("work","id",null));
    }

    @ResponseBody
    @RequestMapping("/GetRank")
    public ArrayList<RankRecord> GetRank(){
        //获得作品的集合
        ArrayList<RankRecord> rankRecords = VoteSQL.GetRank();
        //填入作者名
        for(RankRecord rankRecord:rankRecords){
            rankRecord.setUserName(UserSQL.getUserNameByID(rankRecord.getUserName()));
        }
        //填入票数
        for(RankRecord rankRecord:rankRecords){
            rankRecord.setPoint(VoteSQL.getVote(rankRecord.getId()));
        }
        Collections.sort(rankRecords, new SortByVote());
        return rankRecords;
    }

    class SortByVote implements Comparator {
        public int compare(Object o1, Object o2) {
            RankRecord s1 = (RankRecord) o1;
            RankRecord s2 = (RankRecord) o2;
            return s2.getPoint().compareTo(s1.getPoint());
        }
    }

    @ResponseBody
    @RequestMapping("/Vote")
    public String Vote(String from,String to){
        if(VoteSQL.VoteCheck(from,to).equals("-1"))
            return "-1";
        String result = VoteSQL.Vote(from,to);
        if(!result.equals("-1"))
            return "0";
        else
            return "-2";
    }
}
