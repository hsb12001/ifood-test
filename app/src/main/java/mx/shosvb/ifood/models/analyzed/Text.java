package mx.shosvb.ifood.models.analyzed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Text {

@SerializedName("content")
@Expose
private String content;
@SerializedName("beginOffset")
@Expose
private Integer beginOffset;

public String getContent() {
return content;
}

public void setContent(String content) {
this.content = content;
}

public Integer getBeginOffset() {
return beginOffset;
}

public void setBeginOffset(Integer beginOffset) {
this.beginOffset = beginOffset;
}

}