package com.osmangaziyildiz.kutayindogamacerasi.util

import android.content.Context
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface AudioPlayer {

    fun playAudio(audioPath: String)
    fun stopAudio()
}

@Singleton
class AssetAudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
) : AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    override fun playAudio(audioPath: String) {
        try {
            stopAudio()
            val afd = context.assets.openFd(audioPath)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun stopAudio() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
        }
        mediaPlayer = null
    }
}