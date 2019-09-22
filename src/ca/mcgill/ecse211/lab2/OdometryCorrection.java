package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MedianFilter;
import lejos.hardware.Sound; // TODO Remove sound package.

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 100;

  /*
   * Here is where the odometer correction code should be run.
   */
  public void run() {
    long correctionStart, correctionEnd;
    
    SampleProvider colorSensorSensorProvider = COLOR_SENSOR.getRedMode(); // Create a sample provider and a sample array.
    MedianFilter colorSensorMedianFilter = new MedianFilter(colorSensorSensorProvider, 5); // Use a median filter to filter out noise.
    float[] colorSample = new float[colorSensorMedianFilter.sampleSize()]; // Create a sample array.
    float[] lastSample = new float[colorSensorMedianFilter.sampleSize()]; // Create another array to store the last result.

    while (true) {
      correctionStart = System.currentTimeMillis();
      
      colorSensorSensorProvider.fetchSample(colorSample, 0); // Get a sample.
      
      if (colorSample[0] - lastSample[0] < MIN_INTENSITY_DIFF) { // If the ground is dark than in previous samplings...
        
        Sound.beep(); // TODO Remove beep.
        
        double[] position = ODOMETER.getXYT(); // Get position to identify which line has been crossed.
        double correctedXPos = position[0];
        double correctedYPos = position[1];
        
        if ( (position[2] > 315 && position[2] < 45) || (position[2] > 135 && position[2] < 225) ) { // Along Y axis.
          if (position[1] < TILE_SIZE * 1.5) {
            correctedYPos = 1*TILE_SIZE - (COLOR_SENSOR_OFFSET * Math.cos(Math.toRadians(position[2])));
          } else if (position[1] < TILE_SIZE * 2.5) {
            correctedYPos = 2*TILE_SIZE - (COLOR_SENSOR_OFFSET * Math.cos(Math.toRadians(position[2])));
          } else {
            correctedYPos = 3*TILE_SIZE - (COLOR_SENSOR_OFFSET * Math.cos(Math.toRadians(position[2])));
          }
        } else { // Along X axis.
          if (position[0] < TILE_SIZE * 1.5) {
            correctedYPos = 1*TILE_SIZE - (COLOR_SENSOR_OFFSET * Math.sin(Math.toRadians(position[2])));
          } else if (position[0] < TILE_SIZE * 2.5) {
            correctedYPos = 2*TILE_SIZE - (COLOR_SENSOR_OFFSET * Math.sin(Math.toRadians(position[2])));
          } else {
            correctedYPos = 3*TILE_SIZE - (COLOR_SENSOR_OFFSET * Math.sin(Math.toRadians(position[2])));
          }
        }
        
        ODOMETER.setXYT(correctedXPos, correctedYPos, position[2]);
          
      }
      
      lastSample[0] = colorSample[0];

      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));
      }
    }
  }
  
}
