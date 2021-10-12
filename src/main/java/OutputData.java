import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class OutputData {

    public void outputToFile(String fileName, Map<String, String> memberMap, int count, Map<String, Integer> tierCount) {
        Path path = Paths.get(fileName);
        ArrayList<String> outputContent = new ArrayList<>();
        TreeMap<String, String> sortedMemberMap = new TreeMap<>(memberMap);
        TreeMap<String, Integer> sortedTierCount = new TreeMap<>(tierCount);

        outputContent.add("Total Member Found: " + count + "\n");
        outputContent.add("Tiers: ");
        sortedTierCount.forEach((K, V) -> outputContent.add(K + " : " + V));
        outputContent.add("\nMember List:");
        sortedMemberMap.forEach((K, V) -> outputContent.add(outputContent.size() - (tierCount.size()+2) + ") Name: " + V + " | ID: " + K));
        try {
            Files.write(path, outputContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
