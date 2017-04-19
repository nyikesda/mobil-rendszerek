package hu.bme.mobil_rendszerek.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Objects;

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

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Department department = (Department) o;
    return Objects.equals(name, department.name) &&
        Objects.equals(monthlyQuota, department.monthlyQuota) &&
        Objects.equals(departmentId, department.departmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, monthlyQuota, departmentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Department {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    monthlyQuota: ").append(toIndentedString(monthlyQuota)).append("\n");
    sb.append("    departmentId: ").append(toIndentedString(departmentId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
