/**
 * @author : Grégoire Boiron <gregoire.boiron@gmail.com>
 * @version 0.0.1
 */

package Engine;

import regular.Team;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;

// Custom graphe imports

/** This class call the JME3 engine to render the fight in 3D
 * It has to be in Java since the engine need specific method
 *
 * @author: Grégoire Boiron <gregoire.boiron@gmail.com>
 * @version: 0.0.1
 */
public class Engine3D extends SimpleApplication implements AnalogListener, ActionListener {

    private Nifty nifty;

    private GameScreenState gameScreenState;

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

        gameScreenState = new GameScreenState(this);
        stateManager.attach(gameScreenState);
        // [...] boilerplate init nifty omitted
        try {
            nifty.validateXml("/Assets/Interface/screen.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        nifty.fromXml("/Assets/Interface/screen.xml", "hud", gameScreenState); //one of the XML screen elements needs to reference GameScreenState controller class

        guiViewPort.addProcessor(niftyDisplay);
    }

    /**
     * When the selection of the ListBox changes this method is called.
     */
    /*
    @NiftyEventSubscriber(id="myCustomListBox")
    public void onMyListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<JustAnExampleModelClass> event) {

        List<String> selection = event.getSelection();
        for (String selectedItem : selection) {
            System.out.println("listbox selection [" + selectedItem + "]");
        }

        List<JustAnExampleModelClass> selection = event.getSelection();
    }*/

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
            gameScreenState.nextEntityCameraFocus();
        }
        if (name.equals("previousEntity") && keyPressed) {
            gameScreenState.previousEntityCameraFocus();
        }
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public void setGuiFont(String newFont) {
        guiFont = assetManager.loadFont(newFont);
    }

    //@ assignable
    public void printFinalResult(int result, int numberOfTurn) {
        // Print basic information
        BitmapText hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize()); // font size
        hudText.setColor(ColorRGBA.Red); // font color
        hudText.setText("The fight is done in " + numberOfTurn + " turn(s).");
        hudText.setLocalTranslation(settings.getWidth() / 2 - hudText.getLineWidth() / 2, (settings.getHeight() * 2 / 3 - hudText.getLineHeight() / 2) + 100, 0); // position
        guiNode.attachChild(hudText);

        // Print the result
        BitmapText winningTeam = new BitmapText(guiFont, false);
        winningTeam.setSize(guiFont.getCharSet().getRenderedSize());
        winningTeam.setColor(ColorRGBA.Red);
        switch (result) {
            case -2: winningTeam.setText("You are all dead !! HAHAHAHA");
            case -1: winningTeam.setText("It's a tie !");
            default: winningTeam.setText("The winning team is : " + Team.apply(result));
        }
        winningTeam.setLocalTranslation(settings.getWidth() / 2 - winningTeam.getLineWidth() / 2, (settings.getHeight() / 2 - winningTeam.getLineHeight() / 2) + 10, 0);
        guiNode.attachChild(winningTeam);
    }
}