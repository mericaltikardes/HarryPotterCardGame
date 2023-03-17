package com.mericaltikardes.harrypottercardgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import com.mericaltikardes.harrypottercardgame.utils.BoardCardSize
import com.mericaltikardes.harrypottercardgame.R

class PlayerCounterPageActivity : AppCompatActivity() {

    private var board: BoardCardSize = BoardCardSize.FOUR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_counter_page)

        val playerCountCard =
            findViewById<MaterialButtonToggleGroup>(R.id.materialButtonToggleGroupCard)
        val button = findViewById<Button>(R.id.runBtn)

        playerCountCard.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.fourCard -> {
                        Toast.makeText(
                            this,
                            "Four Card Chocing",
                            Toast.LENGTH_SHORT
                        ).show()
                        board = BoardCardSize.FOUR
                    }
                    R.id.sixteenCard -> {
                        Toast.makeText(
                            this,
                            "Sixteen Card Chocing",
                            Toast.LENGTH_SHORT
                        ).show()
                        board = BoardCardSize.SIXTEEN
                    }
                    R.id.thirtySixCard -> {
                        Toast.makeText(
                            this,
                            "Thirty Six Card Chocing",
                            Toast.LENGTH_SHORT
                        ).show()
                        board = BoardCardSize.TWENTFOUR
                    }
                }
            }
        }
        button.setOnClickListener {
            val intent = Intent(this, SingleGameActivity::class.java)
            intent.putExtra("boardSize", board)
            startActivity(intent)
        }

    }
}
