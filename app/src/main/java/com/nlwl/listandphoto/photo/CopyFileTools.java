package com.nlwl.listandphoto.photo;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-10-17.
 */
public class CopyFileTools {

    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public static void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }




    /**
     * 从sd卡获取图片资源
     * @return
     */
    public static List<String> getImagePathFromSD( String filePath) {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    private static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     *获取路径和文件名称
     */
    public  static ArrayList<Map<String, Object>> getMapData(ArrayList<File> list){
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        HashMap<String,Object> item;
        int i = 0 ;
        for(i=0;i<list.size();i++){
            item = new HashMap<String,Object>();
            String path  = list.get(i).toString();
            String name = path.substring(path.lastIndexOf("/")+1,path.length());
            item.put("FileName",name);
            item.put("FilePath", path);
            data.add(item);
        }
        return data;
    }



    /***
     * 获取SD卡的绝对路径
     *
     * @return
     */

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }



}
