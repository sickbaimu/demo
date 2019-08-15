package elearning.controller;


import elearning.entity.TextChapter;
import elearning.entity.TextSection;
import elearning.patch.TextContent;
import elearning.sql.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @return
     */
    @ResponseBody
    @RequestMapping("/GetTextContent")
    public TextContent GetTextContent(String chapter_id, String section_order){
        //获得章名
        TextChapter textChapter = GetTextChapter(chapter_id);
        //获得节信息
        TextSection textSection = GetTextSection(chapter_id,section_order);
        TextContent textContent = new TextContent(null,textChapter.getName(),textChapter.getOrder(),textSection.getName(),textSection.getOrder(),null);
        textContent.setFlag(textSection.getId());
        if(Integer.parseInt(textContent.getFlag())>=0)
            textContent.setContent(ReadFile("data/text/"+textChapter.getName()+"/"+textSection.getName()+".txt"));
        return textContent;
    }

    /**
     * 获得图片的文字描述
     * @param photoName 图片名
     * @return 文字描述
     */
    @ResponseBody
    @RequestMapping("/GetPhotoDescription")
    public String GetPhotoDescription(String photoName){
        return ReadFile("src/main/resources/static/photo/"+photoName+".txt");
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
}
