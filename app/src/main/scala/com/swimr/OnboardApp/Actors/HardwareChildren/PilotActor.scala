package com.swimr.OnboardApp.Actors.HardwareChildren

import akka.actor.{ActorRef, Actor}
import android.util.Log
import com.swimr.OnboardApp.Actors.HardwareChildren.PilotActor._


object PilotActor {

	// Messages Pilot Actor can send and receive
	case class 	PILOT_CMD_GOTO_COORDINATE(x:Double, y:Double, z:Double, yaw:Double)

	// Verification/safety: put an ID in the goto command and pass it back with this
	object		PILOT_RESULT_ARRIVED

	object 		PILOT_ARE_YOU_STARTED		// to the pilot
	case class	PILOT_IS_STARTED(pilotRef:ActorRef)			// from the pilot
	object 		PILOT_SAY_HELLO

}


class PilotActor extends Actor{
	val TAG = "PilotActor"

	def receive = {

		case cmd:PILOT_CMD_GOTO_COORDINATE =>{
			gotoCoordinate(cmd.x, cmd.y, cmd.z, cmd.yaw, sender())
		}

		case PILOT_ARE_YOU_STARTED =>{
			sender ! PILOT_IS_STARTED(self)
		}

		case PILOT_SAY_HELLO =>{
			Log.d(TAG,"Hello")
		}


		case _ => {
			???
		}
	}

	override def preStart = {
		super.preStart()
		Log.d(TAG, "[PilotActor.preStart]")
	}

	override def postStop = {
		Log.d(TAG, "[PilotActor.postStop]")
		super.postStop()
	}


	def gotoCoordinate(x:Double, y:Double, z:Double, yaw:Double, sender:ActorRef) = {
		Log.d(TAG,s"[PilotActor.gotoCoordinate] (${x}, ${y}, ${z}, ${yaw}")



		def loop(i: Int): Int = {
			Log.d(TAG,"[gotoCoordinate countdown] i = " + i)
			Thread.sleep(1000)
			if (i == 0) {
				return i
			}
			loop(i - 1)
		}
		loop(10)



		sender ! PILOT_RESULT_ARRIVED

	}



}
