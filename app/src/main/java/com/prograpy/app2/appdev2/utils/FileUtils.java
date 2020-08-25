package com.prograpy.app2.appdev2.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtils {

    private Context context;

    private static final String FOLDER_NAME = "meyou_chat";
    private static final String TAG = "FileUtils";


    public FileUtils(Context context){
        this.context = context;
        createFolder();
    }


    private void createFolder(){

        File dir = new File(context.getFilesDir(), FOLDER_NAME);

        if(!dir.exists()){
            dir.mkdir();
            D.log(TAG, "Create Folder > " + context.getFilesDir() + FOLDER_NAME);
        }
    }

    public boolean isFileExists(String fileName){
        return new File(context.getFilesDir() +"/"+ FOLDER_NAME,fileName +".txt").exists();
    }


    public void createFile(String fileName){
        try {
            new File(context.getFilesDir() +"/"+ FOLDER_NAME,fileName +".txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFileText(String fileName, String writeText){

        File file = new File(context.getFilesDir() +"/"+ FOLDER_NAME,fileName +".txt");

        D.log(TAG, "File Path > " + file.getPath());

        try {
            FileOutputStream fOut = new FileOutputStream(file, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            if(writeText.contains(System.getProperty("line.separator")))
                writeText = writeText.replaceAll(System.getProperty("line.separator"), "&#xA");

            myOutWriter.append(writeText);
            myOutWriter.append("\n");
            myOutWriter.close();
            fOut.close();

            D.log(TAG, "File Write");

        } catch (Exception e) {
            e.printStackTrace() ;
        }

    }


    public ArrayList<String> loadItemsFromFile(String fileName) {

        ArrayList<String> items = new ArrayList<String>();

        File file = new File(context.getFilesDir() +"/"+ FOLDER_NAME,fileName +".txt");

        D.log(TAG, "File Path > " + file.getPath());

        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;

                while ((str = bufrd.readLine()) != null) {
                    items.add(str) ;
                }

                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }


        return items;
    }

}

