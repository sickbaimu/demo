package elearning.sql;


import elearning.entity.TextChapter;
import elearning.entity.TextSection;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author lele
 * @version 1.00
 * @description
 * @Date 2019/7/12 19:49
 */
public class LearnSQL extends BaseSQL{



    /**
     * 获得id为chapter_id章、顺序为section_order的节
     * @param chapter_id 章ID
     * @param section_order 节顺序
     * @return 节对象（若id为-1，表示未找到，若id为-2表示已至章首，若id为-3表示已至章尾。
     */
    public static TextSection GetTextSection(String chapter_id, String section_order){
        TextSection textSection = new TextSection("-1",null,null,null);
        try{
            init();
            rs = stmt.executeQuery("select *from section where _order = "+section_order+" and chapter_id = "+chapter_id);
            while(rs.next())
            {
                textSection = new TextSection(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        if(textSection.getId().equals("-1")){//没有查找到的情况
            if(section_order.equals("0")){//已至章首的情况
                textSection.setId("-2");
            }else if(section_order.equals(String.valueOf(GetChapterSize(chapter_id)+1))){//已至章尾的情况
                textSection.setId("-3");
            }
        }
        return textSection;
    }

    /**
     * 获得某一章的节数
     * @param chapter_id 章id
     * @return 节数
     */
    public static int GetChapterSize(String chapter_id){
        int size = 0;
        try{
            init();
            rs = stmt.executeQuery("select *from section where chapter_id = "+chapter_id);
            while(rs.next())
            {
                size++;
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return size;
    }

    public static TextChapter GetTextChapter(String chapter_id){
        TextChapter textChapter = new TextChapter(null,null,null,null);
        try{
            init();
            rs = stmt.executeQuery("select *from chapter where id = "+chapter_id);
            while(rs.next())
            {
                textChapter = new TextChapter(rs.getString(1),rs.getString(2),rs.getString(3),null);
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return textChapter;
    }

    public static ArrayList<TextSection> GetTextSections(){
        ArrayList<TextSection> textSections = new ArrayList<>();
        try{
           init();
            rs = stmt.executeQuery("select *from section order by _order");
            while(rs.next())
            {
                textSections.add(new TextSection(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return textSections;
    }

    public static ArrayList<TextChapter> GetTextChapters(){
        ArrayList<TextChapter> textChapters = new ArrayList<>();
        try{
            init();
            rs = stmt.executeQuery("select *from chapter order by _order");
            while(rs.next())
            {
                textChapters.add(new TextChapter(rs.getString(1),rs.getString(2),rs.getString(3),null));
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return textChapters;
    }

    public static String GetMediaPath(String name) {
        String path = "-1";
        try{
            init();
            rs = stmt.executeQuery("select *from media");
            while(rs.next()) {
                if(rs.getString("name").equals(name))
                path=rs.getString("path");
            }
            closeQuery();
        }catch(SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return path;
    }
}
