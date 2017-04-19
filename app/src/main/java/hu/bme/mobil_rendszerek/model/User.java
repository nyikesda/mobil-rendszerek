package hu.bme.mobil_rendszerek.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Objects;

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

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(username, user.username) &&
        Objects.equals(privilege, user.privilege) &&
        Objects.equals(lastName, user.lastName) &&
        Objects.equals(firstName, user.firstName) &&
        Objects.equals(departmentId, user.departmentId) &&
        Objects.equals(userId, user.userId) &&
        Objects.equals(credential, user.credential);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, privilege, lastName, firstName, departmentId, userId, credential);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    privilege: ").append(toIndentedString(privilege)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    departmentId: ").append(toIndentedString(departmentId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    credential: ").append(toIndentedString(credential)).append("\n");
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
