import pojo.ChatPOJO;

import java.util.ArrayList;
import java.util.HashMap;

public class TierAnalyzer {

    ArrayList<String> tierMessages = new ArrayList<>();
    private int count = 0;
    private int chatSize = 0;
    private HashMap<String, String> memberMap = new HashMap<String, String>();
    private String m_Name, m_ID, m_Title, msg_Type, msg_SecondaryText;
    ArrayList<String> msgTypes = new ArrayList<>();

    public TierAnalyzer(ArrayList<ChatPOJO> chatARL) {
        int counter = 1;
        chatSize = chatARL.size();

        for (ChatPOJO chatObj : chatARL) {
            try {
                System.out.print("Analyzing Tier Messages: " + counter + " / " + chatSize + "\r");

                m_ID = chatObj.getAuthor().getId();
                m_Name = chatObj.getAuthor().getName();
                msg_Type = chatObj.getMessageType();
                msg_SecondaryText = chatObj.getHeaderSecondaryText();

                if ((msg_SecondaryText != null) && (!msg_SecondaryText.equalsIgnoreCase("null"))) {
                    if (!tierMessages.stream().anyMatch(member_msg -> member_msg.equals(msg_SecondaryText)))
                        tierMessages.add(msg_SecondaryText);
                }
                counter++;
                Thread.sleep(5);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("");
    }

    public ArrayList<String> getTierMessages() {
        return tierMessages;
    }

    public void writeTierMessages() {
        System.out.println();
        if (!tierMessages.isEmpty()) {
            System.out.println("Tier Messages:");
            tierMessages.stream().forEach(System.out::println);
            System.out.println();
        }
    }

}


