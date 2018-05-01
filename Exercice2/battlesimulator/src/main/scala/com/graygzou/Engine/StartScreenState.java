package com.graygzou.Engine;

import com.graygzou.Cluster.BattleSimulationCluster;
import com.graygzou.Creatures.GraphEntity;
import com.graygzou.Creatures.VisualizationEntity3D;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.chatcontrol.ChatEntryModelClass;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import java.util.HashMap;

/**
 * @author: Grégoire Boiron <gregoire.boiron@gmail.com>
 * @version: 0.0.1
 */
public class StartScreenState extends BaseAppState implements ScreenController {

    // Parameters from the bind
    private Nifty nifty;
    private Screen screen;
    // End

    private boolean gameFinished = false;
    private float gameFinishCountDown = 5f;
    private float playNewTurnCountDown = 1f; // Play a turn each two seconds
    private final int floorsize = 40;

    private Node localRootNode = new Node("Start Screen RootNode");
    private Node localGuiNode = new Node("Start Screen GuiNode");
    private Node entities = new Node("Entities");
    private final ColorRGBA backgroundColor = ColorRGBA.Gray;
    public static Material mat_default;

    public static Application app;

    // TEST
    HashMap<GraphEntity, Spatial> entityVisualization;

    // OTHERS
    private BattleSimulationCluster game;
    private boolean isPlaying = false;

    protected Geometry player;

    private int currentEntityIdFocused = 0;

    private ChaseCamera camera;

    public StartScreenState(Engine3D engine3D) {
        game = new BattleSimulationCluster("Fight1", "local[1]");
        entityVisualization = new HashMap<>();
    }


    /**
     * Fill the listbox with items. In this case with Strings.
     */
    public void fillMyListBoxTeams() {

        // TEAM 1
        ListBox<ChatEntryModelClass> listBox1 = (ListBox<ChatEntryModelClass>) this.screen.findNiftyControl("listBoxTeam1", ListBox.class);
        NiftyImage newImage = nifty.getRenderEngine().createImage(this.screen, "/Interface/teapot.png",false);
        // false means don't linear filter the image, true would apply linear filtering
        listBox1.addItem(new ChatEntryModelClass("orc1", newImage));
        listBox1.addItem(new ChatEntryModelClass("orc2", newImage));

        // TEAM 2
        ListBox<ChatEntryModelClass> listBox2 = (ListBox<ChatEntryModelClass>) screen.findNiftyControl("listBoxTeam2", ListBox.class);
        // false means don't linear filter the image, true would apply linear filtering
        listBox2.addItem(new ChatEntryModelClass("Angel Random", newImage));
        listBox2.addItem(new ChatEntryModelClass("Angel Slayer d'orcs", newImage));
    }

    @Override
    protected void initialize(Application app) {
        this.app = app;

        //It is technically safe to do all initialization and cleanup in the
        //onEnable()/onDisable() methods. Choosing to use initialize() and
        //cleanup() for this is a matter of performance specifics for the
        //implementor.
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        ((SimpleApplication) app).getRootNode().attachChild(localRootNode);
        ((SimpleApplication) app).getGuiNode().attachChild(localGuiNode);
        ((SimpleApplication) app).getViewPort().setBackgroundColor(backgroundColor);


        /** init the screen */

        this.mat_default = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

        // Create the regular graph
        this.game.setupGame("/FightConfigs/Fight1/entities3D.txt", "/FightConfigs/Fight1/relations.txt", true);

        // This does everything
        localRootNode.attachChild(entities);

        // You initialize game objects and register them one
        this.registerEntity();

        this.initializeFloor();

        this.fillMyListBoxTeams();

        //((SimpleApplication) app).getCamera().setRotation(((SimpleApplication) app).getCamera().getRotation().fromAngleAxis(-30f, Vector3f.UNIT_Y));
        //((SimpleApplication) app).getCamera().setLocation(new Vector3f(0,20f,0));
        // initialize camera and variables
        ((SimpleApplication) app).getFlyByCamera().setEnabled(false);
        camera = new ChaseCamera(((SimpleApplication) app).getCamera(), ((Node)entities.getChild(currentEntityIdFocused)).getChild(1), ((SimpleApplication) app).getInputManager());
        // Smooth camera motion
        camera.setSmoothMotion(true);
    }

    private void updateEnemy() {
        // flush all the previous entities.
        //entities.detachAllChildren();

        for(GraphEntity entity : game.getEntities()) {
            // TODO ICI
            entityVisualization.get(entity);
        }

        System.out.println("Comparaison between 'graphs'");
        for(Spatial n : entities.getChildren()) {
            System.out.println(n.toString());
        }
        System.out.println("============================");
        game.printCurrentGraph();
        System.out.println("Fin Comparaison between 'graphs'");
    }

    public void registerEntity() {
        // Attach the current entity to the rootNode
        for(GraphEntity entity : game.getEntities()) {
            entityVisualization.put(entity, ((VisualizationEntity3D) entity).getNode());
            entities.attachChild(entityVisualization.get(entity));
        }

    }

    private void initializeFloor() {
        // create a floor
        float gridsize = floorsize / 10;
        Box bx = new Box(gridsize / 2, 0.02f, 0.02f);
        Material matx = mat_default.clone();
        matx.setColor("Color", ColorRGBA.Cyan);
        matx.setColor("GlowColor", ColorRGBA.Blue);
        Box by = new Box(0.02f, 0.02f, gridsize / 2);
        Material maty = mat_default.clone();
        maty.setColor("Color", ColorRGBA.Cyan);
        maty.setColor("GlowColor", ColorRGBA.Blue);

        Box floor = new Box(gridsize / 2 - 0.01f, 0.01f, gridsize / 2 - 0.01f);
        Material matfloor = mat_default.clone();
        matfloor.setColor("Color", ColorRGBA.LightGray);

        for (int x = (int) -gridsize * 5; x <= gridsize * 5; x++) {
            for (int y = (int) -gridsize * 5; y <= gridsize * 5; y++) {
                Geometry geomx = new Geometry("Grid", bx);
                geomx.setMaterial(matx);
                geomx.center().move(new Vector3f(x * gridsize, 0, y * gridsize + gridsize / 2));

                localRootNode.attachChild(geomx);

                if (y == (int) -gridsize * 5) {
                    Geometry geomxend = geomx.clone();
                    geomxend.center().move(new Vector3f(x * gridsize, 0, y * gridsize - gridsize / 2));
                    localRootNode.attachChild(geomxend);
                }

                Geometry geomy = new Geometry("Grid", by);
                geomy.setMaterial(maty);
                geomy.center().move(new Vector3f(x * gridsize + gridsize / 2, 0, y * gridsize));

                localRootNode.attachChild(geomy);

                if (x == (int) -gridsize * 5) {
                    Geometry geomyend = geomy.clone();
                    geomyend.center().move(new Vector3f(x * gridsize - gridsize / 2, 0, y * gridsize));
                    localRootNode.attachChild(geomyend);
                }

                Geometry geomfloor = new Geometry("Floor", floor);
                geomfloor.setMaterial(matfloor);
                geomfloor.center().move(new Vector3f(x * gridsize, 0, y * gridsize));

                localRootNode.attachChild(geomfloor);
            }
        }
    }


    public void nextEntityCameraFocus() {
        currentEntityIdFocused = (currentEntityIdFocused + 1) % (entities.getChildren().size() - 1);
        camera.setSpatial(((Node)entities.getChild(currentEntityIdFocused)).getChild(1));

        // Smooth camera motion
        camera.setSmoothMotion(true);
    }

    public void previousEntityCameraFocus() {
        currentEntityIdFocused = (currentEntityIdFocused - 1) % (entities.getChildren().size() - 1);
        if (currentEntityIdFocused < 0) currentEntityIdFocused += entities.getChildren().size() - 1;
        camera.setSpatial(((Node)entities.getChild(currentEntityIdFocused)).getChild(1));

        // Smooth camera motion
        camera.setSmoothMotion(true);
    }

    @Override
    protected void cleanup(Application app) {
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        ((SimpleApplication) app).getRootNode().detachChild(localRootNode);
        ((SimpleApplication) app).getGuiNode().detachChild(localGuiNode);
    }

    //onEnable()/onDisable() can be used for managing things that should
    //only exist while the state is enabled. Prime examples would be scene
    //graph attachment or input listener attachment.
    @Override
    protected void onEnable() {
        //Called when the state is fully enabled, ie: is attached and
        //isEnabled() is true or when the setEnabled() status changes after the
        //state is attached.
    }

    @Override
    protected void onDisable() {
        //Called when the state was previously enabled but is now disabled
        //either because setEnabled(false) was called or the state is being
        //cleaned up.
    }

    @Override
    public void update(float tpf) {
        if(isPlaying) {

            // check if game is over
            if (gameFinished) {
                if (gameFinishCountDown <= 0) {
                    cleanup();
                    //this.stop();
                    return;
                }
                gameFinishCountDown -= tpf;
                return;
            } else if (!gameFinished && !game.isFightNotFinished()) {
                ((Engine3D) app).printFinalResult(game.getFightResult(), game.getCurrentTurnNumber());
                gameFinished = true;
            }

            //updateLasers(tpf);

            updateEnemy();

            // Check if it's time to update the model
            if (playNewTurnCountDown <= 0) {
                System.out.println("Turn n°" + game.getCurrentTurnNumber());
                game.playOneTurn(tpf);
                playNewTurnCountDown = 1f;
            }
            playNewTurnCountDown -= tpf;

            // TODO
            //updateHealthBars();

            // Wait one second
            /*
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }

    /*
    private void checkGameState() {
        // check which team won the game
        // TODO make a method in cluster that check if the game is done
        if(entitiesLeft.length > 0) {
            System.out.println("The winning team is : " + entitiesLeft(0)._2.getTeam) // Get the first team
        } else {
            println("You are all dead !! HAHAHAHA")
        }
        if ( <= 0) {
            BitmapText hudText = new BitmapText(guiFont, false);
            hudText.setSize(guiFont.getCharSet().getRenderedSize()); // font size
            hudText.setColor(ColorRGBA.Red); // font color
            hudText.setText("You WON !"); // the text
            hudText.setLocalTranslation(settings.getWidth() / 2 - hudText.getLineWidth() / 2, settings.getHeight() / 2 - hudText.getLineHeight() / 2, 0); // position
            guiNode.attachChild(hudText);
            gameFinished = true;
        } else if (playerHealth <= 0) {
            BitmapText hudText = new BitmapText(guiFont, false);
            hudText.setSize(guiFont.getCharSet().getRenderedSize()); // font size
            hudText.setColor(ColorRGBA.Red); // font color
            hudText.setText("You LOST !"); // the text
            hudText.setLocalTranslation(settings.getWidth() / 2 - hudText.getLineWidth() / 2, settings.getHeight() / 2 - hudText.getLineHeight() / 2, 0); // position
            guiNode.attachChild(hudText);
            gameFinished = true;
        }
    }*/

    private void updateHealthBars() {
        // update health bars of all entities
        /*
        for(Node currentNode : gameEntities.values()) {
            // TODO : Retrieve the life of the entity
            //((Quad) ((Geometry) currentNode.getChild("healthbar")).getMesh()).updateGeometry(enemyHealth / 100 * 4, 0.2f);
        }*/
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind(" + screen.getScreenId() + ")");
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    @Override
    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void clickPlayButton() {
        isPlaying = true;
    }

    public void clickPauseButton() {
        isPlaying = false;
    }
}