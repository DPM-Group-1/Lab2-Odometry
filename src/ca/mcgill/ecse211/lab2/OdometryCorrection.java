package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MedianFilter;

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 10;

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
      
      if (colorSample[0] - lastSample[0] < MIN_INTENSITY_DIFF) {
        
        // TODO Calculate new (accurate) robot position
  
        // TODO Update odometer with new calculated (and more accurate) values, eg:
        //odometer.setXYT(0.3, 19.23, 5.0);
          
      }
      
      lastSample = colorSample;

      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));
      }
    }
  }
  
}
