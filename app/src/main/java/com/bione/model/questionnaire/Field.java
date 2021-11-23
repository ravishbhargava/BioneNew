package com.bione.model.questionnaire;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Field {

@SerializedName("question")
@Expose
private String question;
@SerializedName("type")
@Expose
private String type;
@SerializedName("options")
@Expose
private List<Option> options = null;

public String getQuestion() {
return question;
}

public void setQuestion(String question) {
this.question = question;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<Option> getOptions() {
return options;
}

public void setOptions(List<Option> options) {
this.options = options;
}

}