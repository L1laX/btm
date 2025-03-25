package classes;

public class Station {
  private String name;
  private String type;
  private Boolean isInterChange;

  public Station(String name, String type, Boolean isInterChange) {
    this.name = name;
    this.type = type;
    this.isInterChange = isInterChange;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Station [ name=" + name + ", type=" + type + " + isInterChange=" + isInterChange + " ]";
  }

  public Boolean getIsInterChange() {
    return isInterChange;
  }

  public void setIsInterChange(Boolean isInterChange) {
    this.isInterChange = isInterChange;
  }
}