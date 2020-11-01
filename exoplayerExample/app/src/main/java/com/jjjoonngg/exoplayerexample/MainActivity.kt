package com.jjjoonngg.exoplayerexample

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jjjoonngg.exoplayerexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val context: Context = this
    private lateinit var binding: ActivityMainBinding

    private var player: SimpleExoPlayer? = null
    private var currentWindow: Int = 0
    private var playBackPosition: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initPlayer()
    }

    override fun onRestart() {
        super.onRestart()
        initPlayer()
    }

    private fun initPlayer() {
        if (player == null) {
            player = SimpleExoPlayer.Builder(context).build()
            binding.videoView.player = player
        }
        with(player!!) {
            setMediaItem(MediaItem.fromUri(Uri.parse(VIDEO_URL)))
            with(binding) {
                videoView.setControllerOnFullScreenModeChangedListener {
                    if (checkLandscapeOrientation()) {
                        changeOrientationToLandscape(false)
                    } else {
                        changeOrientationToLandscape(true)
                    }
                }
            }
            prepare()
            seekTo(currentWindow, playBackPosition)
            playWhenReady = false
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            with(player!!) {
                binding.videoView.player = null
                playBackPosition = currentPosition
                currentWindow = currentWindowIndex
                playWhenReady = false
                release()
            }
            player = null
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else {
            showSystemUI()
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            val params =
                binding.videoView.layoutParams as ConstraintLayout.LayoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height =
                (250 * binding.videoView.resources.displayMetrics.density).toInt()
            binding.videoView.layoutParams = params
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun checkLandscapeOrientation(): Boolean {
        val orientation = resources.configuration.orientation
        return orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    private fun changeOrientationToLandscape(shouldLandScape: Boolean) {
        requestedOrientation = if (shouldLandScape) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onBackPressed() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            changeOrientationToLandscape(false)
        } else {
            super.onBackPressed()
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }


    companion object {
        private const val VIDEO_URL =
            "https://sites.google.com/site/ubiaccessmobile/sample_video.mp4"
    }
}
