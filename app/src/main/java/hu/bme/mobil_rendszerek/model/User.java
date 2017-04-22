package hu.bme.mobil_rendszerek.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class User  extends SugarRecord implements Serializable {
  
  @SerializedName("username")
  private String username = null;
  
  @SerializedName("privilege")
  private Integer privilege = null;
  
  @SerializedName("lastName")
  private String lastName = null;
  
  @SerializedName("firstName")
  private String firstName = null;
  
  @SerializedName("departmentId")
  private Integer departmentId = null;
  
  @SerializedName("userId")
  private Integer userId = null;
  
  @SerializedName("credential")
  private String credential = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getPrivilege() {
    return privilege;
  }
  public void setPrivilege(Integer privilege) {
    this.privilege = privilege;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
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

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getUserId() {
    return userId;
  }
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getCredential() {
    return credential;
  }
  public void setCredential(String credential) {
    this.credential = credential;
  }

}
