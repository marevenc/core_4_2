import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        installGame();
        GameProgress save1 = new GameProgress(78, 12, 4, 8.3);
        GameProgress save2 = new GameProgress(56, 44, 32, 45.0);
        GameProgress save3 = new GameProgress(88, 90, 60, 192.2);
        String savePath1 = "C://Games//savegames//save1.dat";
        String savePath2 = "C://Games//savegames//save2.dat";
        String savePath3 = "C://Games//savegames//save3.dat";
        saveGame(savePath1, save1);
        saveGame(savePath2, save2);
        saveGame(savePath3, save3);
        String[] savesPath = {savePath1, savePath2, savePath3};
        zipFiles("C://Games//savegames//saves.zip", savesPath);
        try {
            Files.delete(Paths.get(savePath1));
            Files.delete(Paths.get(savePath2));
            Files.delete(Paths.get(savePath3));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void installGame(){
        StringBuilder strBuilder = new StringBuilder();
        String[] dirArray = {"C://Games//src",
                "C://Games//res",
                "C://Games//savegames",
                "C://Games//temp",
                "C://Games//src//main",
                "C://Games//src//test",
                "C://Games//res//drawables",
                "C://Games//res//vectors",
                "C://Games//res//icons"};

        String[] fileArray = {"C://Games//src//main//Main.java",
                "C://Games//src//main//Utils.java",
                "C://Games//temp//temp.txt"};

        for(String path : dirArray){
            File dir = new File(path);
            if (dir.mkdir()){
                String[] dirNames = path.split("//");
                strBuilder.append(dirNames[dirNames.length - 1]);
                strBuilder.append(" directory created\n");
            }
        }

        for(String path : fileArray){
            File file = new File(path);
            String[] fileNames = path.split("//");
            try {
                if (file.createNewFile()){
                    strBuilder.append(fileNames[fileNames.length - 1]);
                    strBuilder.append(" file created\n");}
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        try (FileWriter writer = new FileWriter("C://Games//temp//temp.txt", false)){
            writer.write(strBuilder.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveGame(String path,GameProgress save){
        try(FileOutputStream fos = new FileOutputStream(path);
           ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(save);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public  static void zipFiles(String path, String[] entryArray){
        try{
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(path);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(int i = 0; i < entryArray.length; i++){
                File srcFile = new File(entryArray[i]);
                FileInputStream fis = new FileInputStream(srcFile);
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int length;
                while((length = fis.read(buffer)) > 0){
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
