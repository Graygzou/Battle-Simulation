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
  */
class Entity(args: Array[String]) extends Serializable { //position: Vector3f

  // Set the entity team to a dummy value.
  var ownTeam = 0;
  var ownPosition = 0;

  // Basic members fields.
  var ownType = ""
  var ownHealth = 0.0
  var ownArmor = 0.0
  var ownStrength = 0.0
  var ownRegeneration = 0.0

  initClassFields()

  def initClassFields() =
    ownTeam = args(0).toInt
    ownType = args(1)
    ownHealth = args(2).toDouble
    ownArmor = args(3).toDouble
    ownStrength = args(4).toDouble
    ownRegeneration = args(5).toDouble

  // World coordinate
  //var ownPosition = position;

  // Accessors
  def getTeam = ownTeam
  def getPosition = ownPosition

  def getType = ownType
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


  override def toString: String =
    s"Type: ${getType}, Position: ${getPosition}, Team: ${getTeam} Health: ${getHealth}, " +
      s"Armor: ${getArmor}, Strength: ${getStrength}, Regeneration: ${getRegeneration}."

  // ----- Others ------

}
