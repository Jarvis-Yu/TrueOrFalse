package dataClasses.in;

import java.util.*;

public class Car {
  public Queue<Street> currentAndRemainingStreets = new ArrayDeque<>();
  public int index;
  public final int pathLength;

  public InStructure inStructure;

  public Car(List<Street> currentAndRemainingStreets, int pathLength, InStructure inStructure) {
    this.currentAndRemainingStreets.addAll(currentAndRemainingStreets);
    this.index = 0;
    this.pathLength = pathLength;
    this.inStructure = inStructure;
  }

  public int remainingLength() {
    return pathLength - index;
  }

  public void moveToNextStreet(int currentTime) {
    currentAndRemainingStreets.poll();
    if (currentAndRemainingStreets.isEmpty()) { // 如果true, 加分
      inStructure.point +=
          inStructure.bonusPointPerCar + (inStructure.simulationLength - currentTime);
    } else { // 把车放到下一个street
      Street nextStreet = currentAndRemainingStreets.peek();
      nextStreet.addCarToStart(this, currentTime);
    }
  }

  @Override
  public String toString() {
    List<String> s = new ArrayList<>();
    for (Street currentAndRemainingStreet : currentAndRemainingStreets) {
      s.add(currentAndRemainingStreet.getStreetName());
    }
    return "Car{"
        + "streets="
        + s
        + ", index="
        + index
        + ", pathLength="
        + pathLength
        + '}';
  }
}
