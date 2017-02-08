package GUI;

import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import rangerdepot.RangerDepot;

public class ErrorDialog extends JDialog
{
    public final GUI gui;
    public final Dimension screenSize;
    
    private final Component sourceDialog;
    private final JLabel errorLabel;
    
    private final Dimension size = new Dimension(300,100);
    
    private Clip popupClip;
    private boolean clipInitialized = false;
    
    public ErrorDialog(GUI g,Component d)
    {
        super();
        gui = g;
        sourceDialog = d;
        
        addWindowListener(gui);
        
        setVisible(false);
        setResizable(false);
        setSize(size);
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        /*Sound*/
        try
        {
            URL sound = RangerDepot.class.getResource("../resources/sounds/error.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
            popupClip = AudioSystem.getClip();
            popupClip.open(audioStream);
            popupClip.setFramePosition(0);
            clipInitialized = true;
        }
        catch(Exception e){e.printStackTrace();clipInitialized = false;}
        /*Sound End*/
        
        errorLabel = new JLabel("");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(errorLabel);
    }
    
    
    public void showError(String s)
    {
        errorLabel.setText("<html><p>"+s+"</p></html>");
        setLocation((int)((sourceDialog.getLocation().x + sourceDialog.getSize().getWidth()/2)-getSize().getWidth()/2),(int)((sourceDialog.getLocation().y + sourceDialog.getSize().getHeight()/2)-getSize().getHeight()/2));
        setVisible(true);
        requestFocus();
        playErrorSound();
    }
    public void playErrorSound()
    {
        if(clipInitialized)
        {
            popupClip.stop();
            popupClip.setFramePosition(0);
            popupClip.start();
        }
    }
}
