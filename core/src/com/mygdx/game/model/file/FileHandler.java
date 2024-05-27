package com.mygdx.game.model.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public List<Save_Ranking> List_Ranking = new ArrayList<>(); // chỉ push ng thắng vô, sắp xếp.

    public void addRanking(String name, int point, String day){
        this.List_Ranking.add(new Save_Ranking(name, point, day));
    }
    public void Write_Ranking(){
        File file = new File("file/ranking.txt");
        try {
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);

            for(Save_Ranking sv : List_Ranking) {
                oos.writeObject(sv);
            }
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Read_Ranking(){
        File file = new File("file/ranking.txt");
        try {
            InputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);


            Save_Ranking sr = null;
            while(true){
                Object oj = ois.readObject();
                if(oj == null) break;
                else{
                    sr = (Save_Ranking) oj;
                    List_Ranking.add(sr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
