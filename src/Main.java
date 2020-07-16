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
        File dir1 = new File("C://Games//src");
        if (dir1.mkdir()){
            strBuilder.append("src directory created\n");
        }

        File dir2 = new File("C://Games//res");
        if (dir2.mkdir()){
            strBuilder.append("res directory created\n");
        }

        File dir3 = new File("C://Games//savegames");
        if (dir3.mkdir()){
            strBuilder.append("savegames directory created\n");
        }

        File dir4 = new File("C://Games//temp");
        if (dir4.mkdir()){
            strBuilder.append("temp directory created\n");
        }

        File dir5 = new File("C://Games//src//main");
        if (dir5.mkdir()){
            strBuilder.append("src//main directory created\n");
        }

        File dir6 = new File("C://Games//src//test");
        if (dir6.mkdir()){
            strBuilder.append("src//temp directory created\n");
        }

        File mainFile = new File("C://Games//src//main//Main.java");
        try {
            if (mainFile.createNewFile())
                strBuilder.append("Main.java file created\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        File utilsFile = new File("C://Games//src//main//Utils.java");
        try {
            if (utilsFile.createNewFile())
                strBuilder.append("Utils.java file created\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        File dir7 = new File("C://Games//res//drawables");
        if (dir7.mkdir()){
            strBuilder.append("res//drawables directory created\n");
        }

        File dir8 = new File("C://Games//res//vectors");
        if (dir8.mkdir()){
            strBuilder.append("res//vectors directory created\n");
        }

        File dir9 = new File("C://Games//res//icons");
        if (dir9.mkdir()){
            strBuilder.append("res//icons directory created\n");
        }

        File tempFile = new File("C://Games//temp//temp.txt");
        try {
            if (tempFile.createNewFile())
                strBuilder.append("temp.txt file created\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try (FileWriter writer = new FileWriter(tempFile, false)){
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
