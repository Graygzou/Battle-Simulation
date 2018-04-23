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
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

// Custom graphe imports

/** This class call the JME3 engine to render the fight in 3D
 * It has to be in Java since the engine need specific method
 * */
public class Engine3D extends SimpleApplication {

    private BattleSimulationCluster game;

    protected Geometry player;

    private Spatial[] gameEntities;

    public Engine3D() {
        game = new BattleSimulationCluster("Fight1", "local[1]");
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
        // Create the regular graph
        game.initGame("/FightConfigs/Fight1/entities.txt", "/FightConfigs/Fight1/relations.txt", true);
        //game = new BattleSimulationCluster("Fight 1","local[*]");
        gameEntities = new Spatial[game.screenEntities().length];

        // You initialize game objects:
        for(TeamEntities currentTeam : game.screenTeams()) {
            System.out.println(currentTeam.countAliveEntity());
            for(int i = 0; i < currentTeam.countAliveEntity(); i++) {
                //Current entity
                System.out.println(currentTeam.getEntities()[i].getType());
                Entity3D currentEntity = (Entity3D) currentTeam.getEntities()[i];

                // Create the model of the entity
                currentEntity.setModelPath("Models/Teapot/Teapot.obj");
                Spatial current_spatial = assetManager.loadModel("Models/Teapot/Teapot.obj");

                // Place the entity in the world
                current_spatial.setLocalTranslation(currentEntity.getCurrentPosition());

                // Set the scale and the rotation of the entity
                current_spatial.setLocalScale(1, 1, 1); // TODO better

                // Material setup
                Material mat_default = new Material(assetManager,
                        "Common/MatDefs/Misc/Unshaded.j3md");
                System.out.println(currentTeam.getTeamColor());
                mat_default.setColor("Color", currentTeam.getTeamColor());
                current_spatial.setMaterial(mat_default);

                gameEntities[i] = current_spatial;

                // Attach the current entity to the rootNode
                rootNode.attachChild(current_spatial);
            }
        }

        // You initialize variables:

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

    /* Use the main event loop to trigger repeating actions. */
    @Override
    public void simpleUpdate(float tpf) {
        // tpf : time par frame

        for(Entity currentEntity : game.screenEntities()) {
            //currentEntity.
        }
        //player.rotate(0, 2*tpf, 0);

    }
}