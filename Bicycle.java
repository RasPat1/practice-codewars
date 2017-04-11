public class Bicycle {
  int gears;
  int topSpeed;
  int currentGear;
  int currentPosition;
  int currentSpeed;
  String bikeName;
  static int maxLineLength = 20;

  public Bicycle(int gears, int topSpeed, String bikeName) {
    this.gears = gears;
    this.topSpeed = topSpeed;
    this.bikeName = bikeName;
    currentGear = 1;
    currentSpeed = 0;
    currentPosition = 0;
  }

  public void pedal(int revs) {
    int speedPerGear = topSpeed / gears;
    int gearRevs = revs * currentGear;
    currentSpeed = gearRevs * speedPerGear;
    move(1);
  }

  public void shiftUp() {
    if (currentGear < gears) {
      currentGear++;
    }
  }

  public void shiftDown() {
    if (currentGear > 1) {
      currentGear--;
    }
  }

  public void slowDown(int speed) {
    if (currentSpeed > speed) {
      currentSpeed -= speed;
    }
  }

  public void glide(int seconds) {
    while (seconds > 0) {
      currentPosition += currentSpeed;
      slowDown(1);
      try {
        Thread.sleep(1000);
        seconds--;
        display();
      } catch (Exception e) {};
    }

  }

  public void display() {
    int nameLength = bikeName.length();
    int segLength = (maxLineLength - nameLength - 2)/2;
    String lineSeg = "";
    for (int i = 0; i < segLength; i++) {
      lineSeg += "-";
    }
    String extraSeg = lineSeg.length() % 2 == 1 ? "-" : "";
    System.out.println(lineSeg + extraSeg + " " + bikeName + " " + lineSeg);

    System.out.println(padCenter("Position", currentPosition));
    System.out.println(padCenter("Speed", currentSpeed));
    System.out.println(padCenter("Gear", currentGear));
  }

  private <T> String padCenter(String key, T value) {
    return padCenter(key, String.valueOf(value));
  }

  private String padCenter(String key, String value) {
    int size = key.length() + value.length();
    int paddingSize = maxLineLength - size;
    String padding = "";
    for (int i = 0; i < paddingSize; i++) {
      padding += " ";
    }
    return key + padding + value;
  }

  private void move(int seconds) {
    currentPosition += currentSpeed * seconds;
  }

  public static void main(String[] args) {
    Bicycle myBike = new Bicycle(6, 24, "Stinger");
    myBike.display();
    myBike.pedal(5);
    myBike.shiftUp();
    myBike.pedal(2);
    myBike.display();
    myBike.shiftDown();
    myBike.display();
    myBike.pedal(2);
    myBike.shiftDown();
    myBike.shiftDown();
    myBike.shiftDown();
    myBike.pedal(2);
    myBike.display();
    myBike.glide(10);
  }
}