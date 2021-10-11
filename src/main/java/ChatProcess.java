import org.apache.commons.lang3.StringUtils;
import pojo.ChatPOJO;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatProcess implements MemberRecords {

    private ArrayList<String> member_Messages = new ArrayList<>();

    private int count = 0;
    private int chatSize = 0;
    private HashMap<String, String> memberMap;
    private HashMap<String, Integer> tierCount;
    private String m_Name, m_ID, m_Title, msg_Type, msg_SecondaryText;
    ArrayList<String> msgTypes = new ArrayList<>();

    public ChatProcess(ArrayList<ChatPOJO> chatARL, HashMap<String, String> mbrMap, int mbrCount, HashMap<String, Integer> t_Count) {
        int counter = 1;
        count = mbrCount;
        memberMap = mbrMap;
        tierCount = t_Count;
        chatSize = chatARL.size();

        for (ChatPOJO chatObj : chatARL) {
            try {
                System.out.print("Processing Chat Messages: " + counter + " / " + chatSize + "\r");

                m_ID = chatObj.getAuthor().getId();
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
                }
                counter++;
                Thread.sleep(5);
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
    public HashMap<String, String> getMemberMap() {
        return memberMap;
    }
    @Override
    public HashMap<String, Integer> getTierCount() {
        return tierCount;
    }
}
