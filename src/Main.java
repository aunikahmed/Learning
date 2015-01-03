import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    private void runProgram(String directoryName){
        //getting all xml,xls file
        List<File> fileList= getXMLFiles(directoryName);

        FileModifier fileModifier=new FileModifier();
        fileModifier.backupAndModifyAll(fileList);
    }




    private List<File> getXMLFiles(String directoryName){

        List<File> fileList=new ArrayList<File>();

        File folder=new File(directoryName);

        for (File file : folder.listFiles()) {
           String fileName=file.getName();
            if(fileName.endsWith("xml")||fileName.endsWith("xls") ){
                fileList.add(file);
                //System.out.println(file.getName());
            }


        }

        return fileList;
    }

    public static void main(String[] args) {

        //getting folder name from user containing xml file
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter Folder name containing xml/xls file:");
        String folderName=scanner.nextLine();

        //Program entry point
        Main mainEntry=new Main();
        mainEntry.runProgram(folderName.trim());


    }


}
