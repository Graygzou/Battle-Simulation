/**
  * @author Grégoire Boiron <gregoire.boiron@gmail.com>
  */

package com.graygzou.Creatures

class Dragon(name: String, health: Double, armor : Double, strength: Double, regeneration: Double)
  extends Entity(name, health, armor, strength, regeneration) {

  /**
    * Creatures with a fly speed receive the Fly skill for free as a class skill.
    * Creature can have a bonus or penalty depending on his Maneuverability and his Size
    * @param x
    * @param y
    * @param z
    */
  def Fly(x: Double, y: Double, z: Double) =
    // Check the Maneuverability attribute

    // Check the Size attribute

    // Make or not the movement
    println(s"I'm flying to ($x $y $z) position")

}
