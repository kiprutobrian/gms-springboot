package cloudit.africa.GMS.Model;

public class DataTest {
  private String value;
  private Double data;

  public DataTest(String name,Double data) {
    this.value = name;
    this.data = data;
  }

  public Double getData() {
    return data;
  }

  public void setData(Double data) {
    this.data = data;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
