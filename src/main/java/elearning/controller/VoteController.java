package elearning.controller;

import elearning.entity.RankRecord;
import elearning.sql.UserSQL;
import elearning.sql.VoteSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

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
    @RequestMapping("/GetRank")
    public ArrayList<RankRecord> GetRank(){
        //获得作品的集合
        ArrayList<RankRecord> rankRecords = VoteSQL.GetRank();
        //填入作者名
        for(RankRecord rankRecord:rankRecords){
            rankRecord.setUserName(UserSQL.getUserNameByID(rankRecord.getUserName()));
        }
        //填入票数

        return rankRecords;
    }
}
