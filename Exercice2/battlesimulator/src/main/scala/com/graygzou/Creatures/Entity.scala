/**
  * @author : Grégoire Boiron <gregoire.boiron@gmail.com>
  *
  * Functions comments are from the following website :
  * http://www.d20pfsrd.com/alignment-description/movement/
  */

package com.graygzou.Creatures

/**
  *
  * Hint : We do not used case classes for representing entities because those are not immutable data
  * (they will take damages, move around and maybe die).
  * @param name
  * @param health
  * @param armor
  * @param strength
  * @param regeneration
  */
class Entity(name: String, health: Double, armor : Double, strength: Double, regeneration: Double ) { //position: Vector3f

  // Basic constructor
  var ownName = name
  var ownHealth = health
  var ownArmor = armor
  var ownStrength = strength
  var ownRegeneration = regeneration

  // World coordinate
  //var ownPosition = position;

  // Accessors
  def getName = ownName
  def getHealth = ownHealth
  def getArmor = ownArmor
  def getStrength = ownStrength
  def getRegeneration = ownRegeneration

  // Functions that will be used for the simulation

  // Attack
  def Attack(target:Entity, damages: Double) =
    println(s"I'm attacking ${target.toString} with the current damages $damages")


  // Regular movements on Land.

  /**
    * A walk represents unhurried but purposeful movement (3 miles per hour for an unencumbered adult human).
    * A character moving his speed one time in a single round, is walking when he or she moves.
    * @param x
    * @param y
    * @param z
    */
  def Walk(x: Double, y: Double, z: Double) =
    println(s"I'm walking to ($x $y $z) position")

  /**
    * A hustle is a jog (about 6 miles per hour for an unencumbered human). A character moving his speed twice in a
    * single round, or moving that speed in the same round that he or she performs a standard action or another move
    * action, is hustling when he or she moves.
    * @param x
    * @param y
    * @param z
    */
  def Hustle(x: Double, y: Double, z: Double) =
    println(s"I'm running to ($x $y $z) position")

  /**
    * Moving three times speed is a running pace for a character in heavy armor
    * (about 7 miles per hour for a human in full plate).
    * @param x
    * @param y
    * @param z
    */
  def RunTimes3(x: Double, y: Double, z: Double) =
    println(s"I'm runningx3 to ($x $y $z) position")

  /**
    * Moving four times speed is a running pace for a character in light, medium, or no armor (about 12 miles per hour
    * for an unencumbered human, or 9 miles per hour for a human in chainmail.)
    * @param x
    * @param y
    * @param z
    */
  def RunTimes4(x: Double, y: Double, z: Double) =
    println(s"I'm runningx4 to ($x $y $z) position")


  // TODO : right the correct body of this function
  override def toString: String = super.toString

  // ----- Others ------

}
