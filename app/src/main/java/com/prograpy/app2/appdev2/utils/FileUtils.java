package com.prograpy.app2.appdev2.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
            Log.d(TAG, "Create Folder > " + context.getFilesDir() + FOLDER_NAME);
        }
    }

    public boolean isFileExists(String fileName){
        return new File(context.getFilesDir() + FOLDER_NAME,fileName +".txt").exists();
    }

    public void writeFileText(String fileName, String writeText){

        File file = new File(context.getFilesDir() + FOLDER_NAME,fileName +".txt");

        Log.d(TAG, "File Path > " + context.getFilesDir() + FOLDER_NAME +fileName +".txt");

        FileWriter fw = null;

        BufferedWriter bufwr = null;

        try {
            // open file.
            fw = new FileWriter(file);
            bufwr = new BufferedWriter(fw);
            bufwr.write(writeText);
            bufwr.newLine();

            Log.d(TAG, "File Write");

            bufwr.flush();

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        try {
            // close file.
            if (bufwr != null) {
                bufwr.close();
            }

            if (fw != null) {
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }


    public ArrayList<String> loadItemsFromFile(String fileName) {

        ArrayList<String> items = new ArrayList<String>();

        File file = new File(context.getFilesDir() + FOLDER_NAME,fileName +".txt");

        Log.d(TAG, "File Path > " + context.getFilesDir() + FOLDER_NAME +fileName +".txt");

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

