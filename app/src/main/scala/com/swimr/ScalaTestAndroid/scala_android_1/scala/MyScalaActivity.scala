package com.swimr.ScalaTestAndroid.scala_android_1.scala

import akka.actor.{ActorSystem, ActorRef}
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.swimr.OnboardApp.Actors.MissionManagerActor.MISSION_CMD_START
import com.swimr.OnboardApp.Actors.OnboardSupervisorActor.SUPERVISOR_CMD_START
import com.swimr.ScalaTestAndroid.scala_android_1.app.R

class MyScalaActivity extends Activity {

	val TAG = "MyScalaActivity"
	var onboardSupervisorOpt:Option[ActorRef] = None
	var onboardSystem:Option[ActorSystem] = None

	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val myclass: MyScalaClass = new MyScalaClass
		val myScalaString: String = myclass.sayHello
		(findViewById(R.id.myTextview).asInstanceOf[TextView]).setText(myScalaString)


		Log.d(TAG,"[MainScalaActivity.onCreate]");



	}

	protected override def onStart(): Unit ={
		super.onStart()
		Log.d(TAG,"[MainScalaActivity.onStart]");


		import com.swimr.OnboardApp.Actors.OnboardSupervisorActor

		val vehicleCallSign = "car_x-1"
		onboardSystem = Some(ActorSystem("onboard-system"))
		onboardSupervisorOpt = Some(onboardSystem.get.actorOf(OnboardSupervisorActor.buildProps(vehicleCallSign), "onboard-supervisor"))

		startTest()

	}

	def startTest() = {

		onboardSupervisorOpt.get ! SUPERVISOR_CMD_START

	}


}