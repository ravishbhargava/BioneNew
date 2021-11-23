package com.bione.model.questionnaire;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Datum {

@SerializedName("question")
@Expose
private String question;
@SerializedName("options")
@Expose
private List<Option> options = null;
@SerializedName("type")
@Expose
private String type;
@SerializedName("fields")
@Expose
private List<Field> fields = null;

public String getQuestion() {
return question;
}

public void setQuestion(String question) {
this.question = question;
}

public List<Option> getOptions() {
return options;
}

public void setOptions(List<Option> options) {
this.options = options;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<Field> getFields() {
return fields;
}

public void setFields(List<Field> fields) {
this.fields = fields;
}

}