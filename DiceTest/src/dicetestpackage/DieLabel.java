/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicetestpackage;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JLabel;

/**
 *
 * @author n-evans
 */
public class DieLabel extends JLabel {
private Random      rand            = new Random();
private int         dieValue        = 6;
private final int   NUMBER_OF_SIDES = 6;
private ImageIcon[] dieImage        = new ImageIcon[ NUMBER_OF_SIDES + 1 ];
    // instance data members for use with animating the die
    private boolean           isDieAnimated   = true;  // assume that user wants animated die
    private final static int  DELAY           = 200;   // time between successive images in ms
    private final static int  FRAME_COUNT_MAX = 9;     // maximum number of frames to show
    private final static int  FRAME_COUNT_MIN = 5;     // minimum number of frames to show
    private int               frameCount      = 0;     // current frame count
    private int               frameCountLimit = 0;     // total frames to show for an animation
    private Timer             animationTimer;          // animation actionEvent generator
    private boolean           isClickable     = true;  // blocks mouse events during animation 
    
    // instance data members for use with the playability of the die
    private boolean           isDieBeingHeld  = true;   // tracks whether die is being held
    private final static String IS_HELD_MSG   = "held"; // displayed when die is being held
    private final static String IS_USABLE_MSG = " ";    // message to display when die is active

    /**
     * Creates new form DieLabel
     */
    public DieLabel() {
        initComponents();
        for ( int i = 1; i <= NUMBER_OF_SIDES; i++ ) {
        String filename = "/images/die" + i + ".gif";
        dieImage[i]     = new ImageIcon( this.getClass().getResource(filename) );
        }
        // set up our animation timer
        animationTimer = new Timer( DELAY, new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
        animationTimerActionPerformed(evt);
        }
        });
        
        // counters are used to control the number of times the Timer repeats...
        animationTimer.setRepeats( false );
        
        // set up listener for mouse events
        this.addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent evt ) { dieLabelMouseClicked( evt ); }
        });

    }
    
    private void animationTimerActionPerformed( java.awt.event.ActionEvent evt ) {
        isClickable = false;  // prevent mouse click events from messing things up...
        frameCount++;         // increment our animation frame counter

        // if we reached our limit, display the true value and restore mouse events; 
        // otherwise, show some random value...
        if ( frameCount < frameCountLimit ) {
            int value = rand.nextInt( NUMBER_OF_SIDES ) + 1;
            this.setIcon( dieImage[ value ] );
            animationTimer.start();
        } 
        else { 
            this.setIcon( dieImage[ dieValue ]); 
            isClickable = true;
        }
    }

    public int rollDie() {
        // make sure that the die is not being held, as held die should not be "rolled"...
        if ( this.isHeld() ) { return dieValue; }
            
        // initialize the frame counting variables for this roll
        frameCount = 0;
        int       range = FRAME_COUNT_MAX - FRAME_COUNT_MIN + 1;
        frameCountLimit = FRAME_COUNT_MIN + rand.nextInt( range );
        
        // "roll" the die and start the animation if we're to do so
        dieValue = rand.nextInt( NUMBER_OF_SIDES ) + 1;
        if   ( isDieAnimated ) { animationTimer.start(); }
        else                   { setIcon( dieImage[ dieValue ] ); }
        
        return dieValue;
    }

    public int getDieValue() {
        return dieValue;
    }
    
    public boolean setDieValue( int value ) {
        if (value > 6 || value < 0)
            return false;
        else
            dieValue = value;
        return true;
    }

    private void dieLabelMouseClicked( MouseEvent evt ) {
       if ( this.isClickable ) { this.holdDie( !this.isHeld() ); }
    }



    public void setAnimation( boolean animationState ) {
        isDieAnimated = animationState;
    }
    
    public boolean isAnimated() {
       return isDieAnimated; 
    }



    public void holdDie( boolean holdState ) {
        isDieBeingHeld = holdState;
        if ( this.isHeld() ) { this.setText(IS_HELD_MSG); }
        else                 { this.setText(IS_USABLE_MSG); }
    }
    
    public boolean isHeld() {
        return isDieBeingHeld;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/die6.gif"))); // NOI18N
        setText("Held");
        setVerticalAlignment(javax.swing.SwingConstants.TOP);
        setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setMaximumSize(new java.awt.Dimension(80, 110));
        setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
