/**
 * A menu bar that allows the user to save, load, start, or quit a game. 
 * of Minesweeper.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MSMenuBar extends JMenuBar
{
   private Minefield field;
   private Minesweeper frame;
   private final JFileChooser FC = new JFileChooser(".");

   /**
    * Sets up the menu bar, giving it one menu with three items.
    *
    * @param _field The model object.
    * @param _frame The parent frame.
    */
   public MSMenuBar(Minefield _field, Minesweeper _frame)
   {
      super();

      field = _field;
      frame = _frame;
      
      // Build the menu.
      JMenu fileMenu = new JMenu("File");
      fileMenu.add(new NewGameMenuItem());
      fileMenu.add(new SaveGameMenuItem());
      fileMenu.add(new LoadGameMenuItem());
      fileMenu.add(new QuitGameMenuItem());
      
      // Keyboard shortcut
      fileMenu.setMnemonic(KeyEvent.VK_F);
      add(fileMenu);
   }


   /**
    * Allows the user to begin a new game.
    */
   private class NewGameMenuItem extends JMenuItem implements ActionListener
   {
      public NewGameMenuItem()
      {
         super("Start new game");
         setMnemonic(KeyEvent.VK_N);
         addActionListener(this);
      }

      public void actionPerformed(ActionEvent e)
      {
         // Build an option pane that allows the user
         // to set up a new game.
         JPanel widthPanel = new JPanel();
         JTextField wField = new JTextField("20", 5);
         widthPanel.add(new JLabel("Width:  "));
         widthPanel.add(wField);

         JPanel heightPanel = new JPanel();
         JTextField hField = new JTextField("20", 5);
         heightPanel.add(new JLabel("Height: "));
         heightPanel.add(hField);

         JPanel minePanel = new JPanel();
         JTextField mField = new JTextField("50", 5);
         minePanel.add(new JLabel("Mines:  "));
         minePanel.add(mField);

         JPanel optionPanel = new JPanel(new GridLayout(0,1));
         optionPanel.add(widthPanel);
         optionPanel.add(heightPanel);
         optionPanel.add(minePanel);

         int returnVal = JOptionPane.showConfirmDialog(
                                       frame,
                                       optionPanel,
                                       "New game options",
                                       JOptionPane.OK_CANCEL_OPTION);

         // If applicable, start the new game.
         if(returnVal == JOptionPane.OK_OPTION)
         {
            try
            {
               int width = Integer.parseInt(wField.getText().trim());
               int height = Integer.parseInt(hField.getText().trim());
               int mines = Integer.parseInt(mField.getText().trim());

               field = new Minefield(width, height, mines);
               frame.loadGame(field);
            }
            catch(Exception ex)
            {
               JOptionPane.showMessageDialog(frame,
                                             "Error",
                                             "Please enter integer values.",
                                             JOptionPane.ERROR_MESSAGE);

            }
         }
      } // end actionPerformed()
   } // end NewGameMenuItem


   /**
    * Allows the user to save the current game.
    */
   private class SaveGameMenuItem extends JMenuItem implements ActionListener
   {
      public SaveGameMenuItem()
      {
         super("Save game");
         setMnemonic(KeyEvent.VK_S);
         addActionListener(this);
      }

      public void actionPerformed(ActionEvent e)
      {
         int returnVal = FC.showSaveDialog(frame);

         // Save the game, if applicable
         if(returnVal == JFileChooser.APPROVE_OPTION)
         {
            try
            {
               File saveFile = FC.getSelectedFile();
               ObjectOutputStream out =
                  new ObjectOutputStream(new FileOutputStream(saveFile));
               out.writeObject(field);
               out.close();
            }
            catch(FileNotFoundException ex)
            {
               JOptionPane.showMessageDialog(frame,
                                             "Error",
                                             "Unable to open file.",
                                             JOptionPane.ERROR_MESSAGE);
            }
            catch(IOException ex)
            {
               JOptionPane.showMessageDialog(frame,
                                             "Error",
                                             "Error writing file.",
                                             JOptionPane.ERROR_MESSAGE);
            }
         } // end actionPerformed()
      }
   } // end SaveGameMenuItem


   /**
    * Allows the user to load a saved game.
    */
   private class LoadGameMenuItem extends JMenuItem implements ActionListener
   {
      public LoadGameMenuItem()
      {
         super("Load game");
         setMnemonic(KeyEvent.VK_L);
         addActionListener(this);
      }

      public void actionPerformed(ActionEvent e)
      {
         int returnVal = FC.showOpenDialog(frame);

         // Load the game, if applicable
         if(returnVal == JFileChooser.APPROVE_OPTION)
         {
            try
            {
               File loadFile = FC.getSelectedFile();
               ObjectInputStream in =
                  new ObjectInputStream(new FileInputStream(loadFile));
               field = (Minefield) in.readObject();
               frame.loadGame(field);
               in.close();
            }
            catch(FileNotFoundException ex)
            {
               JOptionPane.showMessageDialog(frame,
                                             "Error",
                                             "Unable to open file.",
                                             JOptionPane.ERROR_MESSAGE);
            }
            catch(IOException ex)
            {
               JOptionPane.showMessageDialog(frame,
                                             "Error",
                                             "Error reading file.",
                                             JOptionPane.ERROR_MESSAGE);
            }
            catch(Exception ex)
            {
               JOptionPane.showMessageDialog(frame,
                                             "Error",
                                             "Not a valid save file.",
                                             JOptionPane.ERROR_MESSAGE);
            }
         }
      } // end actionPerformed()
   } // end LoadGameMenuItem


   /**
    * Quits the game.
    */
   private class QuitGameMenuItem extends JMenuItem implements ActionListener
   {
      public QuitGameMenuItem()
      {
         super("Quit");
        setMnemonic(KeyEvent.VK_Q);
        addActionListener(this);
      }

      public void actionPerformed(ActionEvent e)
      {
          int returnVal = JOptionPane.showConfirmDialog(
                                      frame,
                                      "Do you really want to quit?",
                                      "Quit",
                                      JOptionPane.YES_NO_OPTION);
   
          if(returnVal == JOptionPane.YES_OPTION)
          {
             System.exit(0);
          }
      }
   }
}
