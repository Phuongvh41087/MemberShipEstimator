import org.apache.commons.lang3.StringUtils;
import pojo.inputObj.ChatPOJO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatProcess implements MemberRecords {

    private ArrayList<String> member_Messages = new ArrayList<>();

    private int count = 0;
    private int chatSize = 0;

    private Map<String, String> memberMap;
    private Map<String, Integer> tierCount;
    private Map<String, Integer> titleCount;
    private Map<String, Integer> headerPrimaryCount;

    private String m_Name, m_ID, m_Title, msg_Type, msg_SecondaryText, msg_HeaderPrimaryText;
    ArrayList<String> msgTypes = new ArrayList<>();

    public ChatProcess(ArrayList<ChatPOJO> chatARL, Map<String, String> mbrMap, int mbrCount, Map<String, Integer> t_Count,
                       Map<String, Integer> title_Count, Map<String, Integer> headerPrimary_Count) {
        int counter = 1;
        count = mbrCount;
        memberMap = new HashMap<>(mbrMap);
        tierCount = new HashMap<>(t_Count);
        titleCount = new HashMap<>(title_Count);
        headerPrimaryCount = new HashMap<>(headerPrimary_Count);
        chatSize = chatARL.size();

        for (ChatPOJO chatObj : chatARL) {
            try {
                System.out.print("Processing Chat Messages: " + counter + " / " + chatSize + "\r");

                m_ID = chatObj.getAuthor().getId();
                msg_HeaderPrimaryText = chatObj.getHeaderPrimaryText();
                if ((msg_HeaderPrimaryText != null) && (!msg_HeaderPrimaryText.equals("null"))) {
                    headerPrimaryCount.put(msg_HeaderPrimaryText, headerPrimaryCount.getOrDefault(msg_HeaderPrimaryText, 0) + 1);
                }

                msg_SecondaryText = chatObj.getHeaderSecondaryText();
                if ((msg_SecondaryText != null) && (!msg_SecondaryText.equals("null"))){
                    tierCount.put(msg_SecondaryText, tierCount.getOrDefault(msg_SecondaryText, 0) + 1);
                }

                if (!(memberMap.containsKey(m_ID))) {
                    m_Name = chatObj.getAuthor().getName();
                    if (chatObj.getAuthor().getBadges() != null) {
                        m_Title = chatObj.getAuthor().getBadges().get(0).getTitle();
                        if (StringUtils.containsIgnoreCase(m_Title, "member")) {
                            memberMap.put(m_ID, m_Name);
                            titleCount.put(m_Title, titleCount.getOrDefault(m_Title, 0) + 1);
                            count++;
                        }
                    }
                    msg_Type = chatObj.getMessageType();
                    if ((msg_SecondaryText != null) && (!msg_SecondaryText.equals("null"))) {
                            //  && (member_Messages.stream().anyMatch(member_msg -> member_msg.equals(msg_SecondaryText)))) {
                        if (!(memberMap.containsKey(m_ID))) {
                            memberMap.put(m_ID, m_Name);
                            count++;
                        }
                        if (member_Messages.stream().noneMatch(member_msg -> member_msg.equals(msg_SecondaryText))) {
                            member_Messages.add(msg_SecondaryText);
                        }
                    }
                    if ((msg_HeaderPrimaryText != null) && (!msg_HeaderPrimaryText.equals("null"))
                        && (StringUtils.containsIgnoreCase(msg_HeaderPrimaryText, "member"))) {
                        if (!(memberMap.containsKey(m_ID))) {
                            memberMap.put(m_ID, m_Name);
                            count++;
                        }
                    }
                }
                counter++;
                Thread.sleep(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("");
    }

    public int getCount() {
        return count;
    }
    @Override
    public Map<String, String> getMemberMap() {
        return memberMap;
    }
    @Override
    public Map<String, Integer> getTierCount() {
        return tierCount;
    }
    @Override
    public Map<String, Integer> getTitleCount() {
        return titleCount;
    }
    @Override
    public Map<String, Integer> getHeaderPrimaryCount() {
        return headerPrimaryCount;
    }
}
