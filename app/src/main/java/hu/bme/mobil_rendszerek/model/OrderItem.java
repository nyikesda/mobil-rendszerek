package hu.bme.mobil_rendszerek.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class OrderItem extends SugarRecord {
  
  @SerializedName("date")
  private Date date = null;
  
  @SerializedName("cost")
  private Integer cost = null;
  
  @SerializedName("count")
  private Integer count = null;
  
  @SerializedName("productName")
  private String productName = null;
  
  @SerializedName("orderItemId")
  private Integer orderItemId = null;
  
  @SerializedName("departmentId")
  private Integer departmentId = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getCost() {
    return cost;
  }
  public void setCost(Integer cost) {
    this.cost = cost;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getCount() {
    return count;
  }
  public void setCount(Integer count) {
    this.count = count;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getProductName() {
    return productName;
  }
  public void setProductName(String productName) {
    this.productName = productName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getOrderItemId() {
    return orderItemId;
  }
  public void setOrderItemId(Integer orderItemId) {
    this.orderItemId = orderItemId;
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
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(date, orderItem.date) &&
        Objects.equals(cost, orderItem.cost) &&
        Objects.equals(count, orderItem.count) &&
        Objects.equals(productName, orderItem.productName) &&
        Objects.equals(orderItemId, orderItem.orderItemId) &&
        Objects.equals(departmentId, orderItem.departmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, cost, count, productName, orderItemId, departmentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderItem {\n");
    
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    orderItemId: ").append(toIndentedString(orderItemId)).append("\n");
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
