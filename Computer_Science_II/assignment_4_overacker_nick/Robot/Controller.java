/**
 * This implements the necessary functions to communicate
 * with the Parrot ARDrone.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.3
 */

import java.io.*;
import java.net.*;

public class Controller
{
   private PrintStream out = null;

   private double lX = 0; // linear velocity along the x axis
   private double lY = 0; // linear velocity along the y axis
   private double lZ = 0; // linear velocity along the z axis
   private double aZ = 0; // angular velocity along the z axis

   // The following Strings are robot commands.
   private static final String HANDSHAKE = "raw\r\n\r\n";

   private static final String TAKEOFF =
         "{\"receiver\":\"/ardrone/takeoff\",\"msg\":{},"
         + "\"type\":\"std_msgs/Empty\"}";

   private static final String LAND =
         "{\"receiver\":\"/ardrone/land\",\"msg\":{},"
         + "\"type\":\"std_msgs/Empty\"}";

   // To be used with printf
   private static final String SET_VELOCITY =
         "{\"receiver\":\"/cmd_vel\","
         + "\"msg\":{\"linear\":{\"x\":%.2f,"
                              + "\"y\":%.2f,"
                              + "\"z\":%.2f},"
                 + "\"angular\":{\"x\":%.2f,"
                              + "\"y\":%.2f,"
                              + "\"z\":%.2f}},"
         + "\"type\":\"geometry_msgs/Twist\"}";

   
   /**
    * Runs a simple test on the controller; don't use with the real robot!
    */
   public static void main(String[] args)
   {
      Controller c = new Controller("139.78.141.250", 9090);

      c.takeoff();
      c.setVelocity(0.25,0,0,0);
      try
      {
         Thread.sleep(2000);
      }
      catch(Exception e)
      {
      }

      c.setVelocity(0,0.25,0,0);
      try
      {
         Thread.sleep(2000);
      }
      catch(Exception e)
      {
      }

      c.setVelocity(0,0,0.25,0);
      try
      {
         Thread.sleep(2000);
      }
      catch(Exception e)
      {
      }

      c.setVelocity(0,0,0,1);
      try
      {
         Thread.sleep(2000);
      }
      catch(Exception e)
      {
      }
      c.land();
   }


   /**
    * Creates a Controller object and connects it to the given IP.
    *
    * @param IP The IP address of the robot to be controlled.
    * @param port The port on which to connect.
    */
   public Controller(String IP, int port)
   {
      // Initialize the PrintStream and start communication.
      openNewSocket(IP, port);
   }


   /**
    * Sends a takeoff command to the robot.
    */
   public void takeoff()
   {
      out.write(0);
      out.print(TAKEOFF);
      out.write(-1);
      out.flush();
   }


   /**
    * Sends a landing command to the robot.
    */
   public void land()
   {
      // Stop the robot before landing.
      stop();

      // Land.
      out.write(0);
      out.print(LAND);
      out.write(-1);
      out.flush();
   }


   /**
    * Sets the speed of the robot.
    *
    * @param _lX The linear velocity along the x-axis, in m/s.
    * @param _lY The linear velocity along the y-axis, in m/s.
    * @param _lZ The linear velocity along the z-axis, in m/s.
    * @param _aZ The angular velocity along the z-axis, in rad/s.
    */
   public void setVelocity(double _lX, double _lY, double _lZ, double _aZ)
   {
      // Make sure that we don't exceed the limits of 0.25 m/s and 1.0 rad/s.
      final double MAX_LINEAR = 0.25;
      final double MAX_ANGULAR = 1.0;

      // Add the new values to our current ones.
      lX += _lX;
      lY += _lY;
      lZ += _lZ;
      aZ += _aZ;

      // Bounds checking - handles both negative and positive bounds.
      lX = Math.abs(lX) > MAX_LINEAR ? Math.signum(lX)*MAX_LINEAR : lX;
      lY = Math.abs(lY) > MAX_LINEAR ? Math.signum(lY)*MAX_LINEAR : lY;
      lZ = Math.abs(lZ) > MAX_LINEAR ? Math.signum(lZ)*MAX_LINEAR : lZ;
      aZ = Math.abs(aZ) > MAX_ANGULAR ? Math.signum(aZ)*MAX_ANGULAR : aZ;

      // Now send the actual command.
      out.write(0);
      out.printf(SET_VELOCITY, lX, lY, lZ, 0.0, 0.0, aZ);
      out.write(-1);
      out.flush();
   }


   /**
    * Sets the ARDrone's velocity to zero.
    */
   public void stop()
   {
      lX = lY = lZ = aZ = 0.0;
      setVelocity(lX, lY, lZ, aZ);
   }


   /**
    * Closes the PrintStream (and therefore the Socket)
    * after landing the robot.
    *
    * @return True if the connection was successful.
    */
   public boolean openNewSocket(String IP, int port)
   {
      if(out != null)
      {
         land();
         out.close();
      }

      try
      {
         Socket s = new Socket(IP, port);
         out = new PrintStream(s.getOutputStream());
         out.print(HANDSHAKE);
         out.flush();
         return true;
      }
      catch(Exception e)
      {
         return false;
      }
   }

} // end Controller
