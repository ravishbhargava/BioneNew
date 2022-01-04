package com.bione.model.reportMyMicro.tips;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Tips {

@SerializedName("tip1")
@Expose
private String tip1;
@SerializedName("tip2")
@Expose
private String tip2;
@SerializedName("tip3")
@Expose
private String tip3;
@SerializedName("tip4")
@Expose
private String tip4;
@SerializedName("tip5")
@Expose
private String tip5;
@SerializedName("tip6")
@Expose
private String tip6;

public String getTip1() {
return tip1;
}

public void setTip1(String tip1) {
this.tip1 = tip1;
}

public String getTip2() {
return tip2;
}

public void setTip2(String tip2) {
this.tip2 = tip2;
}

public String getTip3() {
return tip3;
}

public void setTip3(String tip3) {
this.tip3 = tip3;
}

public String getTip4() {
return tip4;
}

public void setTip4(String tip4) {
this.tip4 = tip4;
}

public String getTip5() {
return tip5;
}

public void setTip5(String tip5) {
this.tip5 = tip5;
}

public String getTip6() {
return tip6;
}

public void setTip6(String tip6) {
this.tip6 = tip6;
}

}