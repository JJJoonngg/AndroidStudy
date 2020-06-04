package com.jjjoonngg.exoplayerexample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var exoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            var bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
            var trackSelector: TrackSelector =
                DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

            val videoURI = Uri.parse(VIDEO_URL)

            val dataSourceFactory = DefaultHttpDataSourceFactory("ExoPlayer_VIDEO")
            val extractorsFactory = DefaultExtractorsFactory()
            val mediaSource = ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null)

            exo_player_view.setPlayer(exoPlayer)
            exoPlayer.prepare(mediaSource)
            exoPlayer.setPlayWhenReady(true)
        }catch (exception:Exception){
            Log.e("MainActivity", "Error : " + exception.toString())
        }
    }

    companion object {
        private const val VIDEO_URL =
            "https://sites.google.com/site/ubiaccessmobile/sample_video.mp4"
    }
}
