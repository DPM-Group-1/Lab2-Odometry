package ca.mcgill.ecse211.lab2;

import java.text.DecimalFormat;

//static import to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab2.Resources.*;

/**
 * This class is used to display the content of the odometer variables (x, y, Theta)
 */
public class Display implements Runnable {

  private double[] position;
  private final long DISPLAY_PERIOD = 25;
  private long timeout = Long.MAX_VALUE;

  public void run() {
    
    LCD.clear();
    
    long updateStart, updateEnd;

    long tStart = System.currentTimeMillis();
    do {
      updateStart = System.currentTimeMillis();

      // Retrieve x, y and Theta information
      position = ODOMETER.getXYT();
      
      // Print x,y, and theta information
      DecimalFormat numberFormat = new DecimalFormat("######0.00");
      LCD.drawString("X: " + numberFormat.format(position[0]), 0, 0);
      LCD.drawString("Y: " + numberFormat.format(position[1]), 0, 1);
      LCD.drawString("T: " + numberFormat.format(position[2]), 0, 2);
      
      // this ensures that the data is updated only once every period
      updateEnd = System.currentTimeMillis();
      if (updateEnd - updateStart < DISPLAY_PERIOD) {
        try {
          Thread.sleep(DISPLAY_PERIOD - (updateEnd - updateStart));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } while ((updateEnd - tStart) <= timeout);

  }
  
  /**
   * Sets the timeout in ms.
   * 
   * @param timeout
   */
  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }

}
