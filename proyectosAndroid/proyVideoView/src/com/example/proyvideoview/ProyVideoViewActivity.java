package com.example.proyvideoview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class ProyVideoViewActivity extends Activity {

	private VideoView mVideoView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mVideoView = (VideoView) this.findViewById(R.id.surface_view);

		// Para usar con streaming seria:
		// mVideoView.setVideoURI()Uri.parse(URLstring);
		mVideoView.setVideoPath("data/video.mp4");
		mVideoView.setMediaController(new MediaController(this));
		mVideoView.start();
		mVideoView.requestFocus();

	}
}
