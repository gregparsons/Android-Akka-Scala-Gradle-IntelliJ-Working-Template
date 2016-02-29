package com.swimr.OnboardApp.Actors

import akka.actor._
import android.util.Log
import com.swimr.OnboardApp.Actors.HardwareChildren.PilotActor.{PILOT_IS_STARTED, PILOT_ARE_YOU_STARTED, PILOT_SAY_HELLO}
import com.swimr.OnboardApp.Actors.HardwareChildren.CompassActor
import com.swimr.OnboardApp.Actors.HardwareManagerActor.HARDWARE_MGR_SAY_HELLO
import com.swimr.OnboardApp.Actors.MissionManagerActor.MISSION_CMD_START


/**
  *
  * One of two children of OnboardSupervisor. Needs to talk to the child of its peer, the hardware manager.
  *
  */
object MissionManagerActor{

	object MISSION_CMD_START
	object Msg_LoadFlightPlan
	object Msg_StartFlightPlan

	def buildProps:Props = {
		Props(new MissionManagerActor())
	}
}
class MissionManagerActor extends Actor{
	val TAG = "MissionManagerActor"
	val PATH_TO_PILOT = "akka://onboard-system/user/onboard-supervisor/hardware-manager-actor/pilot-actor"
	var _pilotRef : Option[ActorRef] = None


	def receive = {

		case MISSION_CMD_START =>{
			Log.d(TAG,"[MissionManagerActor.receive.MISSION_CMD_START]")

			// akka://onboard-system/user/onboard-supervisor/mission-manager-actor
			// context.actorSelection("akka://onboard-system/user/onboard-supervisor/hardware-manager-actor") !  Identify("hw-manager?")
			// context.actorSelection("akka://onboard-system/user/onboard-supervisor/hardware-manager-actor") !  HARDWARE_MGR_SAY_HELLO
			context.actorSelection(PATH_TO_PILOT) !  PILOT_ARE_YOU_STARTED


		}

		case PILOT_IS_STARTED(pilotRef:ActorRef) =>{
			_pilotRef = Some(pilotRef)

		}

		// Response from another actor when sent the Identify(object) message
		// The id sent is returned in correlationId
		// http://doc.akka.io/japi/akka/2.3.3/akka/actor/Identify.html
		case responder:ActorIdentity =>{
			Log.d(TAG,s"[MissionManagerActor.receive.ActorIdentity] Received identity from ${responder.correlationId}:\n\t" + responder.getRef.path )
		}

		case _ => {}
	}

	override def preStart = {
		super.preStart()
		Log.d(TAG,s"[MissionManagerActor.preStart] starting")
	}


	override def postStop = {
		super.postStop
		Log.d(TAG,"[MissionManagerActor.postStop]")
	}


}
