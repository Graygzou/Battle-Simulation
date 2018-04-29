/**
 * @author : Grégoire Boiron <gregoire.boiron@gmail.com>
 * @version 0.0.1
 */

package com.graygzou.Engine;

import com.graygzou.Cluster.BattleSimulationCluster;
import com.graygzou.Creatures.Entity;
import com.graygzou.Creatures.Entity3D;
import com.graygzou.TeamEntities;
import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

// Custom graphe imports

/** This class call the JME3 engine to render the fight in 3D
 * It has to be in Java since the engine need specific method
 * */
public class Engine3D extends SimpleApplication implements AnalogListener, ActionListener {

    private Nifty nifty;

    private StartScreenState startScreenState;

    public Engine3D() {

    }

    /**
     * // You initialize game objects:
     *         //      create or load objects and position them.
     *         //      make objects appear in the scene by attaching them to the rootNode.
     *         //
     *         // You initialize variables:
     *         //      create variables to track the game state.
     *         //      set variables to their start values.
     *         //
     *         //You initialize keys and mouse actions:
     *         //      the following input bindings are pre-configured:
     *         //      W A S D keys – Move around in the scene
     *         //      Mouse movement and arrow keys – Turn the camera
     *         //      Esc key – Quit the game
     *         //      Define your own additional keys and mouse click actions.
     */
    @Override
    public void simpleInitApp() {

        // Init the UI
        registerInput();


        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);

        nifty = niftyDisplay.getNifty();
         /*
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);

        nifty = niftyDisplay.getNifty();
        //nifty.fromXml("Interface/Nifty/HelloJme.xml", "start", this);

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);*/

        startScreenState = new StartScreenState(this);
        stateManager.attach(startScreenState);
        // [...] boilerplate init nifty omitted
        nifty.fromXml("Interface/screen.xml", "hud", startScreenState); //one of the XML screen elements needs to reference StartScreenState controller class


        guiViewPort.addProcessor(niftyDisplay);
         /*
        ViewPort niftyView = renderManager.createPreView("NiftyView", cam);
        niftyView.setClearFlags(true, true, true);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                niftyView);
        nifty = niftyDisplay.getNifty();
        //nifty.fromXml("all/intro.xml", "start");
        try {
            nifty.validateXml("Interface/screen.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        nifty.fromXml("Interface/screen.xml", "hud", this);
        //niftyView.addProcessor(niftyDisplay);

        //nifty.addXml("Interface/Screens/OptionsScreen.xml");
        //nifty.addXml("Interface/Screens/StartScreen.xml");
        nifty.gotoScreen("start");

        //StartScreenState screenControl = (StartScreenState) nifty.getScreen("start").getScreenController();
        //stateManager.attach(screenControl);
        //guiViewPort.addProcessor(niftyDisplay);*/


        /*
        Texture2D depthTex = new Texture2D(1024, 768, Image.Format.Depth);
        FrameBuffer fb = new FrameBuffer(1024, 768, 1);
        fb.setDepthTexture(depthTex);

        Texture2D tex = new Texture2D(1024, 768, Image.Format.RGBA8);
        tex.setMinFilter(Texture.MinFilter.Trilinear);
        tex.setMagFilter(Texture.MagFilter.Bilinear);

        fb.setColorTexture(tex);
        niftyView.setClearFlags(true, true, true);
        niftyView.setOutputFrameBuffer(fb);

        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", tex);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);*/


        //You initialize keys and mouse actions:




        /** this blue box is our player character */
        /*
        Box b = new Box(1, 1, 1);
        player = new Geometry("blue cube", b);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(mat);
        rootNode.attachChild(player);*/

    }

    //onEnable()/onDisable() can be used for managing things that should
    //only exist while the state is enabled. Prime examples would be scene
    //graph attachment or input listener attachment.
    protected void createUI() {

        // Setup your Nifty display
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                getAssetManager(),
                getInputManager(),
                getAudioRenderer(),
                getGuiViewPort());

        Nifty nifty = niftyDisplay.getNifty();
        getGuiViewPort().addProcessor(niftyDisplay);
        getFlyByCamera().setDragToRotate(true);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        // start the screen
        nifty.gotoScreen("hud");
}

    protected void onDisable() {
        //Called when the state was previously enabled but is now disabled
        //either because setEnabled(false) was called or the state is being
        //cleaned up.
    }

    public void quit(){
        nifty.gotoScreen("end");
    }

    public void registerInput() {
        inputManager.addMapping("moveForward", new KeyTrigger(keyInput.KEY_UP), new KeyTrigger(keyInput.KEY_W));
        inputManager.addMapping("moveBackward", new KeyTrigger(keyInput.KEY_DOWN), new KeyTrigger(keyInput.KEY_S));
        inputManager.addMapping("nextEntity", new KeyTrigger(keyInput.KEY_RIGHT), new KeyTrigger(keyInput.KEY_D));
        inputManager.addMapping("previousEntity", new KeyTrigger(keyInput.KEY_LEFT), new KeyTrigger(keyInput.KEY_A));
        inputManager.addMapping("displayPosition", new KeyTrigger(keyInput.KEY_P));
        inputManager.addListener((InputListener)this, "nextEntity","previousEntity", "displayPosition");
        inputManager.addListener((InputListener) this, "displayPosition");
    }

    /* Use the main event loop to trigger repeating actions. */
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (name.equals("moveRight")) {
            //teaGeom.move(5 * tpf, 0, 0);
        }
        if (name.equals("moveLeft")) {
            //teaGeom.move(-5 * tpf, 0, 0);

        }

    }

    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("displayPosition") && keyPressed) {
            //teaGeom.move(10, 10, 10);
        }
        if (name.equals("nextEntity") && keyPressed) {
            startScreenState.nextEntityCameraFocus();
        }
        if (name.equals("previousEntity") && keyPressed) {
            startScreenState.previousEntityCameraFocus();
        }
    }
}