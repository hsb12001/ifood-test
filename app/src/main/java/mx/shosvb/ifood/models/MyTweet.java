package mx.shosvb.ifood.models;

public class MyTweet{
    public Integer sentimentColor;
    public String text;
    public String emojiText;


    public MyTweet( String t ){
        this.text=t;
    }

    public void setSentimentColor(Integer sentimentColor) {
        this.sentimentColor = sentimentColor;
    }
    public Integer getSentimentColor() {
        return sentimentColor;
    }

    public void setEmojiText(String emojiText) {
        this.emojiText = emojiText;
    }

    public String getEmojiText() {
        return emojiText;
    }
}
