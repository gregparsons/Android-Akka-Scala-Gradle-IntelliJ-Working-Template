package com.swimr.OnboardApp.Actors.HardwareChildren

import akka.actor.Actor
import android.util.Log

/**
  * Created by alpine on 2/28/16.
  */
class CompassActor extends Actor{
	val TAG="CompassActor"

	def receive = {
		case _ => {}
	}

	override def preStart = {
		super.preStart()
		Log.d(TAG,s"[CompassActor.preStart] starting")

	}

	override def postStop = {
		super.postStop
		Log.d(TAG,"[CompassActor.postStop]")
	}
}
