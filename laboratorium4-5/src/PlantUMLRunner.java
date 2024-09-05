import java.io.*;

public class PlantUMLRunner {
    private static String PlantUmlJarPath;

    public static void setPlantUMLJarPath(String path){
        PlantUmlJarPath = path;
    }

    public static void generateDiagram(String umlData, String outputDirection, String outputName) throws IOException {

        File umlFile = new File(outputDirection, outputName+".uml");

        String command  = "java -jar" + PlantUmlJarPath + " " + umlFile.getAbsolutePath();

        Process process = Runtime.getRuntime().exec(command);

        try (OutputStream os = new FileOutputStream(umlFile)){
            os.write(umlData.getBytes());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
