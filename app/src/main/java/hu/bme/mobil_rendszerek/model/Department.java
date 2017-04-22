package hu.bme.mobil_rendszerek.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class Department extends SugarRecord {
  
  @SerializedName("name")
  private String name = null;
  
  @SerializedName("monthlyQuota")
  private Integer monthlyQuota = null;
  
  @SerializedName("departmentId")
  private Integer departmentId = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getMonthlyQuota() {
    return monthlyQuota;
  }
  public void setMonthlyQuota(Integer monthlyQuota) {
    this.monthlyQuota = monthlyQuota;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getDepartmentId() {
    return departmentId;
  }
  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

}
