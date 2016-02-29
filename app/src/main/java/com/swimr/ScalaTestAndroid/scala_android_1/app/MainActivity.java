package com.swimr.ScalaTestAndroid.scala_android_1.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.swimr.ScalaTestAndroid.scala_android_1.scala.MyScalaClass;
// import com.swimr.ScalaTestAndroid.scala_android_1.app.MyScalaClass;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MyScalaClass myclass = new MyScalaClass();
		String myScalaString = myclass.sayHello();

		((TextView)findViewById(R.id.myTextview)).setText(myScalaString);

	}

}
