import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class OutputData {

    public void outputToFile(String fileName, HashMap<String, String> memberMap, int count, HashMap<String, Integer> tierCount) {
        Path path = Paths.get(fileName);
        ArrayList<String> outputContent = new ArrayList<>();

        outputContent.add("Total Member Found: " + count + "\n");
        outputContent.add("Tiers: ");
        tierCount.forEach((K, V) -> {
            outputContent.add(K + " : " + V);
        });
        outputContent.add("\n Member List:");
        memberMap.forEach((K, V) -> {
            outputContent.add(outputContent.size() - (tierCount.size()+2) + ") Name: " + V + " | ID: " + K);
        });
        try {
            Files.write(path, outputContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
