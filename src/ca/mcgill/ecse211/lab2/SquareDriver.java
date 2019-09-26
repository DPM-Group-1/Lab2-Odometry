package ca.mcgill.ecse211.lab2;

//static import to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab2.Resources.*;

/**
 * This class is used to drive the robot on the demo floor.
 */
public class SquareDriver {

  /**
   * Drives the robot in a square of size 3x3 Tiles. It is to be run in parallel
   * with the odometer and odometer correction classes to allow testing their functionality.
   */
  public static void drive() {
    // reset the motors
    LEFT_MOTOR.stop();
    RIGHT_MOTOR.stop();
    LEFT_MOTOR.setAcceleration(3000);
    RIGHT_MOTOR.setAcceleration(3000);
    

    // Sleep for 2 seconds
    Main.sleepFor(2000);

    for (int i = 0; i < 4; i++) {
      // drive forward three tiles
      LEFT_MOTOR.setSpeed(FORWARD_SPEED);
      RIGHT_MOTOR.setSpeed(FORWARD_SPEED + SPEED_DELTA);

      LEFT_MOTOR.rotate(convertDistance(3 * TILE_SIZE), true);
      RIGHT_MOTOR.rotate(convertDistance(3 * TILE_SIZE), false);

      // turn 90 degrees clockwise
      LEFT_MOTOR.setSpeed(ROTATE_SPEED);
      RIGHT_MOTOR.setSpeed(ROTATE_SPEED + SPEED_DELTA);

      LEFT_MOTOR.rotate(convertAngle(90.0), true);
      RIGHT_MOTOR.rotate(-convertAngle(90.0), false);
    }
  }

  /**
   * Converts input distance to the total rotation of each wheel needed to cover that distance.
   * 
   * @param distance
   * @return the wheel rotations necessary to cover the distance
   */
  public static int convertDistance(double distance) {
    return (int) ((180.0 * distance) / (Math.PI * WHEEL_RAD));
  }

  /**
   * Converts input angle to the total rotation of each wheel needed to rotate the robot by that
   * angle.
   * 
   * @param angle
   * @return the wheel rotations necessary to rotate the robot by the angle
   */
  public static int convertAngle(double angle) {
    return convertDistance(Math.PI * TRACK * angle / 360.0);
  }
}
