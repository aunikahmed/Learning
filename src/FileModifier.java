import org.w3c.dom.Document;

import java.io.*;
import java.util.List;

/**
 * Created by muzaddid on 8/17/14.
 */
public class FileModifier {

    List<File> fileList;
    XMLFileParser fileParser;

    public void backupAndModifyAll(List<File> fileList) {

        fileParser=new XMLFileParser();
        this.fileList = fileList;

        for (File file:fileList){

            boolean backUpFileCreated=createBackupFile(file);
            if(backUpFileCreated) {
                System.out.println(file.getName() + " backup created");
            }
            else{
                System.out.println(file.getName() + " backup can not be created.\nFile not modified" );
                continue;
            }

            boolean fileModified=modify(file);
            if(fileModified){
                System.out.println(file.getName() +"modified");
            }
            else{
                System.out.println(file.getName() +"can not be modified");
            }
        }
    }

    private boolean modify(File file){


        Document document=fileParser.parseAndModify(file);
        if(document==null){
            return false;
        }
        fileParser.writeModifiedXMLInFile(file.getAbsolutePath(), document);

        return true;

    }




    private boolean createBackupFile(File file){

        FileReader fileReader=null;
        try {
                fileReader = new FileReader(file);
         }catch (FileNotFoundException fileNotFound){
            System.out.println("File not found");
            return false;
         }

        BufferedReader bufferedReader=new BufferedReader(fileReader);

        File backupFile = new File(file.getAbsolutePath()+".bak");

        try {
            if (backupFile.createNewFile()){
                System.out.println("File is created!");
            }else{
                System.out.println("File already exists.");
            }
        } catch (IOException e) {

            System.out.println("IOException!!!!! Backup can not be created");
            return false;
        }

        Writer writer = null;
        String line=null;
        //output the contents in the backup file
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(backupFile.getAbsolutePath()), "utf-8"));
            while ((line = bufferedReader.readLine()) != null) {

                writer.write(line+"\n");
            }
        } catch (IOException ex) {
            System.out.println("IOException!!!!! Backup can not be created");
            return false;
        } finally {
            try {
                writer.close();

            } catch (Exception ex) {}
        }


        return true;
    }


}
