import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static final Scanner systemIn = new Scanner(System.in);

    public static void main(String[] args) {
        String prefix = "resources\\";
        String suffix = ".java";
        ArrayList<String> files = new ArrayList<>();

        System.out.println("JAVA Quera Merger");
        System.out.println("Please put all off your source files in a folder names resources in project directory then,");
        System.out.println("Enter all of your public class names-with primary one- (Enter -1 for end)");
        while (true) {
            String input = systemIn.next();
            if (input.equals("-1")) {
                break;
            }
            files.add(input);
        }
//        System.out.println("Enter your primary class name (the one with psvm)");
        String primaryClass = "default"; //= systemIn.next();
//        if (!files.contains(primaryClass)) {
//            files.add(primaryClass);
//        }

        HashSet<String> imports = new HashSet<>();
        ArrayList<String> fileRead = new ArrayList<>();

        int classBeginingIndex = -1;
        for (String filename :
                files) {
            File file = new File(prefix + filename + suffix);
            Scanner scanner;
            try {
                scanner = new Scanner(file);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
//                if (!filename.equals(primaryClass)) {
                if(input.contains("public class ")) {
                    input = input.replace("public class ", "class ");
                    classBeginingIndex = fileRead.size();
                }
//                }

                if(input.contains("public static void main(String[]") || input.contains("public static void main (String[]")){
                     primaryClass = filename;
                     String classLine =  fileRead.get(classBeginingIndex);
                     classLine = classLine.replace("class ","public class ");
                     fileRead.remove(classBeginingIndex);
                     fileRead.add(classBeginingIndex,classLine);
                }


                if ((input.length() > 5) && input.substring(0, 6).equals("import")) {
                    imports.add(input);
                } else {
                    fileRead.add(input);
                }
            }
        }

        PrintWriter writer;
        try {
            writer = new PrintWriter(primaryClass + suffix);
        } catch (
                Exception e) {
            e.printStackTrace();
            return;
        }
        for (
                String imp :
                imports) {
            writer.println(imp);
        }
        for (
                String write :
                fileRead) {
            writer.println(write);
        }
        writer.close();
        if(primaryClass == null){
            System.out.println("No primary class found\n");
        }
        System.out.println("Your file is ready \"" + primaryClass + suffix + "\"");
    }

}
