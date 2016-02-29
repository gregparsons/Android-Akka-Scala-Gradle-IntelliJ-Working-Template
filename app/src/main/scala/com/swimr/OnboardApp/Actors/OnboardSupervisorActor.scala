package com.swimr.OnboardApp.Actors

import akka.actor.{ActorRef, Actor, Props}
import android.util.Log
import com.swimr.OnboardApp.Actors.HardwareChildren.CompassActor
import com.swimr.OnboardApp.Actors.MissionManagerActor.MISSION_CMD_START
import com.swimr.OnboardApp.Actors.OnboardSupervisorActor._


/**
  * Creates and supervises all onboard Akka Actors. First level children are the Mission-Manager-Actor and the
  * Hardware-Manager-Actor.
  *
  */
object OnboardSupervisorActor {

	object SUPERVISOR_CMD_START

	def buildProps(callsign:String):Props = {
		// Pass any parameters through to constructor here
		Props(new OnboardSupervisorActor(callsign))
	}

}

case class OnboardSupervisorActor(callsign:String) extends Actor{
	val TAG = "OnboardSupervisorActor"

	def receive = {

		case SUPERVISOR_CMD_START=>{
			Log.d(TAG,"[OnboardSupervisorActor.receive.SUPERVISOR_CMD_START]")

			val msnMgr:Option[ActorRef] = context.child("mission-manager-actor")
			msnMgr match {
				case Some(mgr) =>{
					mgr ! MISSION_CMD_START
				}
				case None =>{
					Log.d(TAG, "Mission manager not found")
				}
			}
		}

		case _ => {}
	}

	override def preStart = {
		super.preStart()
		Log.d(TAG,s"[OnboardSupervisorActor.preStart] ${callsign} starting")

		// Start some child actors: mission and hardware managers
		this.context.actorOf(MissionManagerActor.buildProps,"mission-manager-actor")
		this.context.actorOf(HardwareManagerActor.buildProps,"hardware-manager-actor")
	}

	override def postStop = {
		super.postStop
		Log.d(TAG,"[OnboardSupervisorActor.postStop]")
	}


}
