import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

@Command (name="getDownloadCommand", mixinStandardHelpOptions = true, version = "Get Download Command 1.0",
        description = "Get command for Youtube Chat Downloader")
public class GetDownloadCommand implements Callable<Integer> {

    @Option (names = {"-id", "--id"}, description = "Channel ID")
    private String channelID;
    @Option (names = {"-b", "--before"}, description = "Published Before (yyyy-MM-dd)")
    private String endDate;
    @Option (names = {"-a", "--after"}, description = "Published After (yyyy-MM-dd)")
    private String beginningDate;
    @Option (names = {"-o", "--output"}, description = "Output File", defaultValue = "download-command.txt")
    private String outputFile;

    private static final String CLIENT_SECRETS= "client_secret.json";
    private static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");

    private static final String APPLICATION_NAME = "Youtube Chat Downloader";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String IRYS_CHANNEL = "UC8rcEBzJSleTkf_-agPM20g";

    // OAuth Credential
    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        InputStream in = GetDownloadCommand.class.getResourceAsStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .build();

        Credential credential =
                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void writeOutput(SearchListResponse response) {
        List<SearchResult> searchResultList = new ArrayList<>();
        if (response.getItems().size() > 0) {
            searchResultList.addAll(response.getItems());
            System.out.println("Channel: " + searchResultList.get(0).getSnippet().getChannelTitle());
            ArrayList<String> outputContent = new ArrayList<>();
            outputContent.add("Channel: " + searchResultList.get(0).getSnippet().getChannelTitle());

            try {
                Path path = Paths.get(outputFile);

                for (SearchResult searchResult : searchResultList) {
                    System.out.println("ID: " + searchResult.getId().getVideoId());
                    outputContent.add("ID: " + searchResult.getId().getVideoId());
                    System.out.println("Title: " + searchResult.getSnippet().getTitle());
                    outputContent.add("Title: " + searchResult.getSnippet().getTitle());
                    System.out.println("Date: " + searchResult.getSnippet().getPublishedAt());
                    outputContent.add("Date: " + searchResult.getSnippet().getPublishedAt());
                    outputContent.add("chat_downloader https://www.youtube.com/watch?v=" + searchResult.getId().getVideoId()
                                    + " --cookies \"f:/chat-downloader/cookies.txt\" --message_groups \"messages superchat\" --output \"f:/chat-downloader/IRyS_"
                                    + searchResult.getSnippet().getPublishedAt() + ".json\" \n");
                    System.out.println("==========");
                    Thread.sleep(2);
                }

                Files.write(path, outputContent);
            } catch (Exception ex) {
                System.out.println("Error while writing to file!");
            }
        }
    }

    @Override
    public Integer call() throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        if ((beginningDate == null) || (endDate == null)) {
            System.out.println("Error!  Missing Beginning / End Date");
        } else {
            if (channelID == null) {
                System.out.println("Channel ID not specified.  Defaulting to IRyS' channel");
                channelID = IRYS_CHANNEL;
            }
            /*  OAuth
            YouTube youtubeService = getService();
            */
            //  API Key
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            YouTube youtubeAPIKeyService = new YouTube.Builder(httpTransport, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName(APPLICATION_NAME).build();
            //
            ArrayList<String>  partList = new ArrayList<>();
            partList.add("snippet");
            partList.add("id");
            ArrayList<String>  typeList = new ArrayList<>();
            typeList.add("video");
            String bgDate = beginningDate + "T00:00:00.00Z";
            String eDate = endDate + "T00:00:00.00Z";
            /*
            IRyS' ID : UC8rcEBzJSleTkf_-agPM20g
            */
            YouTube.Search.List request = youtubeAPIKeyService.search()
                    .list(partList);
            SearchListResponse response = request.setKey("AIzaSyCXfAhRN-yO7gl78TY9OkJfZAP6RlZ7TEw")
                    .setChannelId(channelID)
                    .setMaxResults(75L)
                    .setOrder("date")
                    .setPublishedAfter(bgDate)
                    .setPublishedBefore(eDate)
                    .setType(typeList)
                    .execute();

            writeOutput(response);
        }

        return 0;
    }

    public static void main(String[] args)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        GetDownloadCommand getDownloadCommand = new GetDownloadCommand();

        int exitCode = new CommandLine(getDownloadCommand).execute(args);
        System.exit(exitCode);
    }
}
