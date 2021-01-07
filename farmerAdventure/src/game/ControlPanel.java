package game;

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class ControlPanel extends JPanel implements ActionListener {
    private Game game;
    private JButton volumeUp,volumeDown, mute, fsx;
    private JButton pause, restart, quit, save, load, dev;
    private int selector;
    private JLabel labelMusic, labelGame;
    private JPanel musicPanel, gamePanel;
    private JProgressBar musicVisual;
    private JComboBox cBox;
    private static String[] dropDownOptions = {"Game options","Sounds options"};
    private static SoundClip controlSound;
    public ControlPanel(Game game){
        this.game = game;

        this.setPreferredSize(new Dimension(500,40));
        setLayout(new FlowLayout(FlowLayout.RIGHT,5,2));
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        musicPanel = new JPanel();
        gamePanel = new JPanel();
        //adding dropDownBox
        cBox = new JComboBox(dropDownOptions);
        cBox.addItemListener(
                new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange() == ItemEvent.DESELECTED){
                            System.out.println(cBox.getSelectedIndex());
                            setSelector(cBox.getSelectedIndex());
                        }
                    }
                }

        );
        //
        //Game panel
        //
        load = new JButton("Load");
        gamePanel.add(load);
        load.addActionListener(this);
        save = new JButton("Save");
        gamePanel.add(save);
        save.addActionListener(this);
        restart = new JButton("Restart");
        restart.addActionListener(this);
        gamePanel.add(restart);
        pause = new JButton("Pause");
        pause.addActionListener(this);
        gamePanel.add(pause);
        //
        // Music panel
        //
        musicVisual = new JProgressBar();
        musicVisual.setMaximum(20);
        musicVisual.setMinimum(1);
        musicVisual.setValue(5);
        musicVisual.setPreferredSize(new Dimension(75,25));
        fsx = new JButton("FSX: ON");
        musicPanel.add(fsx);
        fsx.addActionListener(this);
        volumeDown = new JButton("-");
        musicPanel.add(volumeDown);
        volumeDown.addActionListener(this);
        mute = new JButton("Mute");
        musicPanel.add(mute);
        mute.addActionListener(this);
        volumeUp = new JButton("+");
        musicPanel.add(volumeUp);
        volumeUp.addActionListener(this);
        musicPanel.add(musicVisual);
        addGamePanel();
        addCBox();

    }

    static{
        try{
            controlSound = new SoundClip("data/sounds/Select.wav");
            controlSound.setVolume(Game.getGameVolume());
        }catch (UnsupportedAudioFileException|IOException| LineUnavailableException e){
            System.out.println(e);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e){
        //this regain focus on game after using GUI
        addMouseListener(new GiveFocus(game.getFrame()));
        //This makes sounds
        if(!Game.isFsxMuted())
        ControlPanel.controlSound.play();

        if(e.getSource() == mute){
            if (game.isMuted() == false) {
                game.setGameVolume(game.getGameVolume());
                game.setVolume(0.001f);
                game.setMuted(true);
                if(!Game.isFsxMuted())
                this.fsx.doClick();
            } else if (game.isMuted() == true){
                game.setVolume(game.getGameVolume());
                game.setMuted(false);
                if(Game.isFsxMuted())
                    this.fsx.doClick();
            }


        }
        if(e.getSource() == fsx){
            System.out.println("SFX got clicked!");
            if(game.isFsxMuted() == false){
                game.setFsxMuted(true);
                fsx.setText("FSX: OFF");
            } else if (game.isFsxMuted() == true){
                game.setFsxMuted(false);
                fsx.setText("FSX: ON");
            }
        }
        if(e.getSource() == volumeDown){
            if (game.isMuted() == false && game.getGameVolume()>0.11f){
                    game.setVolume(game.getGameVolume() - 0.1f);
                    game.setGameVolume(game.getGameVolume() - 0.1f);
                musicVisual.setValue(musicVisual.getValue() - 1);
            } else {
                //volumeDown.setVisible(false);
            }
            if (game.isMuted() == true){
                game.setVolume(game.getGameVolume());
                game.setMuted(false);
            }

            System.out.println(game.getGameVolume());

        }
        if(e.getSource() == volumeUp){
            if (game.isMuted() == false && game.getGameVolume()<1.9f){
                try {
                    game.setVolume(game.getGameVolume() + 0.1f);
                    game.setGameVolume(game.getGameVolume() + 0.1f);
                    musicVisual.setValue(musicVisual.getValue() + 1);
                } catch (Exception e1){}
                System.out.println(game.getGameVolume());
            }
            if (game.isMuted() == true){
                game.setVolume(game.getGameVolume());
                game.setMuted(false);
            }
        }
        if(e.getSource() == restart && !Game.isTransitionInProgress() && !game.isPaused()){
            game.getView().getWorld().stop();
            Flower.setFlowerCounter(0);
            game.restartLevel();
        }
        if (e.getSource() == pause && !Game.isTransitionInProgress()){
            if (game.isPaused() == false){
                game.getView().getWorld().stop();
                game.setPaused(true);
                this.mute.doClick();
                pause.setText("Unpause");
            } else {
                game.getView().getWorld().start();
                game.setPaused(false);
                this.mute.doClick();
                pause.setText("Pause");
            }
        }
        if(e.getSource() == cBox){
            System.out.println("things in cBox was clicked");
            if(selector == 0){
                changeMtoG();
                System.out.println("I am in game exception");
            } else if(selector == 1){
                changeGtoM();
                System.out.println("I am in sounds exception");
            }
        }
        if(e.getSource() == save) {
            try {
                game.saveGame((GameLevel) game.getView().getWorld());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == load) {

                Loader l = new Loader();
                try {
                    l.populateIt(game);
                } catch (IOException ello) {
                    System.out.println(ello);
                }
        }
    }
    //
    //Methods
    //
    private void addCBox(){
        this.add(cBox);
        cBox.addActionListener(this);
    }
    private void addGamePanel(){
        add(gamePanel);
    }
    private void addMusicPanel(){
        add(musicPanel);
    }
    private void changeGtoM(){
        remove(gamePanel);
        remove(cBox);
        addMusicPanel();
        addCBox();
        this.revalidate();
        this.repaint();

    }
    private void changeMtoG(){
        remove(musicPanel);
        remove(cBox);
        addGamePanel();
        addCBox();
        this.revalidate();
        this.repaint();
    }
    //
    //Getters
    //

    //
    //Setters
    //


    public void setSelector(int selector) {
        this.selector = selector;
    }
}
