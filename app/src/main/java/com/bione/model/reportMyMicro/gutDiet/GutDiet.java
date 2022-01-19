package com.bione.model.reportMyMicro.gutDiet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GutDiet {

@SerializedName("gut_restoration_diet")
@Expose
private GutRestorationDiet gutRestorationDiet;
@SerializedName("gut_maintenance_diet")
@Expose
private GutMaintenanceDiet gutMaintenanceDiet;

public GutRestorationDiet getGutRestorationDiet() {
return gutRestorationDiet;
}

public void setGutRestorationDiet(GutRestorationDiet gutRestorationDiet) {
this.gutRestorationDiet = gutRestorationDiet;
}

public GutMaintenanceDiet getGutMaintenanceDiet() {
return gutMaintenanceDiet;
}

public void setGutMaintenanceDiet(GutMaintenanceDiet gutMaintenanceDiet) {
this.gutMaintenanceDiet = gutMaintenanceDiet;
}

}