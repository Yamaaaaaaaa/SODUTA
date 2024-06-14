package com.mygdx.game.model.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygdx.game.model.entity.Bullet;
import com.mygdx.game.model.entity.Entity_Status;
import com.mygdx.game.model.entity.Monster;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FileHandler {
    public List<Point_SetEnding> List_Ranking; // chỉ push ng thắng vô, sắp xếp.
    private FileHandle fileHandle;
    private String filePath = "";
    public FileHandler() {
        this.List_Ranking = new ArrayList<>();

        fileHandle = Gdx.files.internal("file/ranking.txt");
        String tmp = fileHandle.file().getAbsolutePath();
        for(int i = 0 ;i  < tmp.length(); i++){
            if(tmp.charAt(i) == '\\'){
                filePath = filePath + tmp.charAt(i) + tmp.charAt(i);
            }
            else filePath = filePath + tmp.charAt(i);
        }

        this.Read_Ranking();
        this.coutRanking();

    }

    public void addRanking(String name, int point){
        this.List_Ranking.add(new Point_SetEnding(name, point));
        sortRanking();
        Write_Ranking();
    }
    public void coutRanking(){
        System.out.println("Xuat Ranking: ");
        for(Point_SetEnding s : this.List_Ranking){
            System.out.println(s.getName() + "--" + s.getPoint() + "--" + s.getDayRanking());
        }
    }
    public void Write_Ranking(){
        // PHƯƠNG THỨC NÀY DÙNG 1 LẦN DUY NHẤT LÀ KHI THOÁT CHƯƠNG TRÌNH.
       // File file = new File("D:\\A.Documents\\Code\\Lib_GDX_Learning\\Group5_BTCK_PGC-Endless_Way\\assets\\file\\ranking.txt");
       // File file = fileHandle.file();

        if(List_Ranking.size() > 7){
            ArrayList<Point_SetEnding> rejBullet = new ArrayList<Point_SetEnding>();
            for(int i = 7 ; i < List_Ranking.size(); i++){
                rejBullet.add(this.List_Ranking.get(i));
            }

            this.List_Ranking.removeAll(rejBullet);
        }

        File file = new File(this.filePath);
        try {
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);

            for(Point_SetEnding sv : List_Ranking) {
                oos.writeObject(sv);
            }
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Read_Ranking(){
        // PHƯƠNG THỨC NÀY DÙNG 1 LẦN DUY NHẤT LÀ KHI KHỞI TẠO CHƯƠNG TRÌNH:
            // ĐỌc file, luu list xuống ARRAAYLIST.
       // File file = new File("D:\\A.Documents\\Code\\Lib_GDX_Learning\\Group5_BTCK_PGC-Endless_Way\\assets\\file\\ranking.txt");

        File file = new File(this.filePath);
        try {
            InputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);


            Point_SetEnding sr = null;
            while(true){
                Object oj = ois.readObject();
                if(oj == null) break;
                else{
                    sr = (Point_SetEnding) oj;
                    List_Ranking.add(sr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sortRanking(){
        Collections.sort(List_Ranking);
    }
    public int checkRanking(int point){
        sortRanking();
        if(List_Ranking.size() < 7){
            for(int i = 0; i < List_Ranking.size(); i++){
                if(List_Ranking.get(i).getPoint() <= point){
                    return i + 1;
                }
            }
            return List_Ranking.size() + 1;
        }
        else{
            for(int i = 0; i < 7; i++){
                if(List_Ranking.get(i).getPoint() <= point){
                    return i + 1;
                }
            }
        }
        return 0;
    }
}
