package codeJava;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.LinkedList;

public class Message {
    //This variables are MUST
    private String messageID ="";
    private long time;
    private String receivedFrom = "";
    private int contents = 0; //has to be very last header!
    private LinkedList<String> body= new LinkedList<>();
    //This variables are may
    private String sentTo = "";
    private String subject = "";
    private String topic = "";

    //message generating
    private String[] names = new String[] {"Jakub", "Lucie", "Peter", "Mike"};
    private String[] randomText = new  String[]{"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " ,
            "Nam pulvinar purus at massa vehicula, at tristique lacus dignissim. Morbi porta congue quam, et dignissim magna. " ,
            "Aenean sem nunc, pulvinar maximus felis nec, gravida viverra libero. Cras aliquet velit et justo auctor pharetra. " ,
            "Suspendisse in nibh magna. Nam sed condimentum nulla. Curabitur at efficitur leo. " ,
            "Aliquam turpis neque, placerat et nisl eu, gravida maximus elit.",
            "Empty message" };
    private String[] subjects = new String[] {"Jakub sleeps", "Lucie plays", "Peter jumps", "Michal is here", ""};
    private String[] topics = new String[] { "#potato", "#carrot", "#honey"};


    private byte[] salt;


    public Message(String messageID, String receivedFrom) throws NoSuchAlgorithmException {
        messageIDBuilder(messageID);
        this.time = TimeManager.timeInstance_UnixEpochTime_sec();
        this.receivedFrom = receivedFrom;
    }

    public Message(String messageID, long time, String receivedFrom, int contents, LinkedList<String> body, String subject, String topic) {
        this.messageID = messageID;
        this.time = time;
        this.receivedFrom = receivedFrom;
        this.contents = contents;
        this.body = body;
        this.subject = subject;
        this.topic = topic;
    }

    public Message(){
        this.time = TimeManager.timeInstance_UnixEpochTime_sec();
    }



    public String getMessageID() {
        return messageID;
    }

    public long getTime() {
        return time;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public int getContents() {
        return contents;
    }

    public String getSentTo() {
        return sentTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getTopic() {
        return topic;
    }

    public byte[] getSalt(){ //TODO: Just for testing
        return salt;
    }

    public LinkedList<String> getBody() {
        return body;
    }

    public void messageIDBuilder(String messageString) throws NoSuchAlgorithmException {
        salt = Hashing.getSalt();
        this.messageID = this.messageID.concat( Hashing.get_SHA_256_hash(messageString,salt) );
    }


    public Message generateMessage() throws NoSuchAlgorithmException {
        time = (long) (Instant.now().getEpochSecond() - Math.random()*1000000);

        receivedFrom = randomFromArray(names);
        sentTo = randomFromArray(names);
        subject = randomFromArray(subjects);
        topic = randomFromArray(topics);
        contents = (int) Math.ceil(Math.random()*4);
        for (int i = 0; i < contents; i++){
            body.add(randomFromArray(randomText));
        }
        messageIDBuilder("" + time + " " + receivedFrom + " " + sentTo + " " + topic + " " + subject + " " + contents);
        return this;
    }

    private String randomFromArray(String[] array){
        return array[(int) Math.floor(Math.random()*array.length)];
    }
}
