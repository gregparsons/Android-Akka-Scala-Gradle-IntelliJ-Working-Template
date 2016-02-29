package com.swimr.OnboardApp.Actors

import akka.actor.{Actor, Props}
import android.util.Log
import com.swimr.OnboardApp.Actors.HardwareChildren.{PilotActor, CompassActor}
import com.swimr.OnboardApp.Actors.HardwareManagerActor.HARDWARE_MGR_SAY_HELLO

/**
  * Creates and supervises all hardware services. Coordinates boot-up/preflight process.
  *
  */
object HardwareManagerActor {

	object HARDWARE_MGR_SAY_HELLO

	def buildProps:Props = {
		Props(new HardwareManagerActor())
	}
}
class HardwareManagerActor extends Actor{
	val TAG="HardwareManagerActor"


	def receive = {
		case HARDWARE_MGR_SAY_HELLO =>{

			Log.d(TAG,"Hello")

		}
		case _ => {}
	}

	/**
	  * Runs after constructor
	  */
	override def preStart = {
		super.preStart()
		Log.d(TAG,s"[HardwareManagerActor.preStart] starting")

		context.actorOf(Props[CompassActor],"compass-actor")
		context.actorOf(Props[PilotActor],"pilot-actor")

	}

	override def postStop = {
		super.postStop
		Log.d(TAG,"[HardwareManagerActor.postStop]")
	}


}
