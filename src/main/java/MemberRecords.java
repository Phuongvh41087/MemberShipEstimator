import java.util.HashMap;
import java.util.Map;

public interface MemberRecords {
    Map<String, String> getMemberMap();
    Map<String, Integer> getTierCount();
    Map<String, Integer> getTitleCount();
}
