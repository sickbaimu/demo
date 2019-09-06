package elearning.controller;


import elearning.entity.MyMedia;
import elearning.entity.RankRecord;
import elearning.entity.TextChapter;
import elearning.entity.TextSection;
import elearning.patch.TextContent;
import elearning.sql.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static elearning.FileHandler.ReadFile;
import static elearning.sql.LearnSQL.*;

@Controller
public class LearnController {
    /**
     * 获取文本目录
     * @return 文本目录
     */
    @ResponseBody
    @RequestMapping("/GetTextList")
    public ArrayList<TextChapter> getTextList(){
        ArrayList<TextChapter> textChapters = GetTextChapters();
        ArrayList<TextSection> textSections = GetTextSections();
        for(TextChapter textChapter:textChapters){
            ArrayList<TextSection> temp_section = new ArrayList<>();
            for(TextSection textSection:textSections){
                if(textSection.getChapter_id().equals(textChapter.getId()))
                    temp_section.add(textSection);
            }
            textChapter.setTextSections(temp_section);
        }
        return textChapters;
    }


    /**
     * 获取某一章的节数
     * @param chapter_id 章的id
     * @return 节数
     */
    @ResponseBody
    @RequestMapping("/GetChapterSize")
    public String GetChapterSize(String chapter_id){
        return String.valueOf(LearnSQL.GetChapterSize(chapter_id));
    }

    /**
     * 获得文本内容
     * @param chapter_id 章id
     * @param section_order 节id
     * @return 文本内容
     */
    @ResponseBody
    @RequestMapping("/GetTextContent")
    public TextContent GetTextContent(String chapter_id, String section_order,String userID){
        //获得章名
        TextChapter textChapter = GetTextChapter(chapter_id);
        //获得节信息
        TextSection textSection = GetTextSection(chapter_id,section_order);
        TextContent textContent = new TextContent(null,textChapter.getName(),textChapter.getOrder(),textSection.getName(),textSection.getOrder(),null);
        textContent.setFlag(textSection.getId());
        if(Integer.parseInt(textContent.getFlag())>=0)
            textContent.setContent(ReadFile("data/text/"+textChapter.getName()+"/"+textSection.getName()+".txt"));
        if(!CheckRecord(userID,"text",chapter_id+"_"+section_order))
            AddRecord(userID,"text",chapter_id+"_"+section_order);
        return textContent;
    }

    /**
     * 获得图片的文字描述
     * @param photoName 图片名
     * @return 文字描述
     */
    @ResponseBody
    @RequestMapping("/GetPhotoDescription")
    public String GetPhotoDescription(String photoName,String userID){
        PointController.AddPoint(userID,1,"TP");
        if(!CheckRecord(userID,"photo",photoName))
            AddRecord(userID,"photo",photoName);
        return ReadFile("src/main/resources/static/photo/"+photoName+".txt");
    }

    @ResponseBody
    @RequestMapping("/GetMediaPath")
    public String GetMediaPath(String name,String userID){
        if(!CheckRecord(userID,"media",name))
            AddRecord(userID,"media",name);
        return LearnSQL.GetMediaPath(name);
    }

    @ResponseBody
    @RequestMapping("/GetMedia")
    public MyMedia GetMedia(String name){
        return LearnSQL.GetMediaByName(name);
    }
    /**
     * 测试网络连接
     * @return 回复ping
     */
    @ResponseBody
    @RequestMapping("/Ping")
    public String DefaultRequest(){
        return "rePing";
    }

    @ResponseBody
    @RequestMapping("/GetRate")
    public static String GetRate(String userID){
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        int numText = LearnSQL.getRate(userID,"text");
        int numPhoto = LearnSQL.getRate(userID,"photo");
        int numMedia = LearnSQL.getRate(userID,"media");
        int totalText = LearnSQL.getTotal("section");
        int totalPhoto = LearnSQL.getTotal("photo");
        int totalMedia= LearnSQL.getTotal("media");
        float rateText =  (float)numText*100/totalText;
        float ratePhoto =  (float)numPhoto*100/totalPhoto;
        float rateMedia =  (float)numMedia*100/totalMedia;
        return decimalFormat.format(rateText) + "% " + decimalFormat.format(ratePhoto) + "% " +decimalFormat.format(rateMedia) + "%";
    }
    @ResponseBody
    @RequestMapping("/GetAllRate")
    public static ArrayList<RankRecord> GetAllRate() {
        ArrayList<RankRecord> rankRecords = new ArrayList<>();
        //获得用户列表
        ArrayList<String> userIDList = UserSQL.getAllUser();
        for(String id:userIDList){
            rankRecords.add(new RankRecord(id,"",id,""));
        }
        //将用户id替换为用户名
        for(RankRecord rankRecord:rankRecords)
            rankRecord.setUserName(UserSQL.getUserNameByID(rankRecord.getUserName()));
        //获得Rate
        for(RankRecord rankRecord:rankRecords)
            rankRecord.setWorkName(GetRate(rankRecord.getId()));
        return rankRecords;
    }

    @ResponseBody
    @RequestMapping("/UpdateMedia")
    public static String UpdateMedia(String id,String order,String name,String path){
        return LearnSQL.UpdateMedia(id,order,name,path);
    }

    @ResponseBody
    @RequestMapping("/DeleteMedia")
    public static String DeleteMedia(String id){
        return LearnSQL.DeleteMedia(id);
    }

    @ResponseBody
    @RequestMapping("/AddMedia")
    public static String AddMedia(String order,String name,String path){
        return LearnSQL.AddMedia(order,name,path);
    }
}
