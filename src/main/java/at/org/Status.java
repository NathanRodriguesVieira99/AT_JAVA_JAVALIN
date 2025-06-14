package at.org;

/* 
 CLASSE PARA INFORMAR O STATUS E O TIMESTAMP DE "/status" 
*/

public class Status {
  private final String status;
  private final String timestamp;

  public Status(String status, String timestamp) {
    this.status = status;
    this.timestamp = timestamp;
  }

  public String getStatus() {
    return status;
  }

  public String getTimestamp() {
    return timestamp;
  }
}
