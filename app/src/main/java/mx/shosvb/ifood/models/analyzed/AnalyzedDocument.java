package mx.shosvb.ifood.models.analyzed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnalyzedDocument {

@SerializedName("documentSentiment")
@Expose
private DocumentSentiment documentSentiment;
@SerializedName("language")
@Expose
private String language;
@SerializedName("sentences")
@Expose
private List<Sentence> sentences = null;

public DocumentSentiment getDocumentSentiment() {
return documentSentiment;
}

public void setDocumentSentiment(DocumentSentiment documentSentiment) {
this.documentSentiment = documentSentiment;
}

public String getLanguage() {
return language;
}

public void setLanguage(String language) {
this.language = language;
}

public List<Sentence> getSentences() {
return sentences;
}

public void setSentences(List<Sentence> sentences) {
this.sentences = sentences;
}

}