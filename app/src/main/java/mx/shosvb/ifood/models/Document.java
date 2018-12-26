package mx.shosvb.ifood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("language")
    @Expose
    private String language;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }


    public enum Language{
        ENGLISH("EN_US");

        String value;
        Language(String value){
            this.value=value;
        }
    }

    public enum Encoding{
        UTF8("UTF8");
        public String value;
        Encoding(String value){
            this.value=value;
        }
    }

    public enum Type{
        PLAIN_TEXT("PLAIN_TEXT"),
        HTML_TEXT("HTML");
        public String value;
        Type(String value){
            this.value=value;
        }
    }
}