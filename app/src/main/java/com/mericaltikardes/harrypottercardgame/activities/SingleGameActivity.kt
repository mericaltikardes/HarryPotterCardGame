package com.mericaltikardes.harrypottercardgame.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mericaltikardes.harrypottercardgame.utils.BoardCardSize
import com.mericaltikardes.harrypottercardgame.CardsDatasSituation
import com.mericaltikardes.harrypottercardgame.R
import com.mericaltikardes.harrypottercardgame.adapters.RecyclerViewAdapter
import com.mericaltikardes.harrypottercardgame.adapters.RecyclerViewAdapter.*

class SingleGameActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"

    }

    private var numPairsFound = 0
    private var indexOfSingleSelectedCard: Int? = null
    private lateinit var cardMemory: List<CardsDatasSituation>
    private lateinit var recyclerViewCard: RecyclerView
    private lateinit var board: BoardCardSize

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_counter_page)

        board = intent.getSerializableExtra("boardSize") as BoardCardSize
        createGame()
    }


    fun createGame() {
        setContentView(R.layout.activity_single_game)

        val gryfindor = listOf(
            R.drawable.albus_dumbledore,
            R.drawable.arthur_weasley,
            R.drawable.hagrid,
            R.drawable.harry_potter,
            R.drawable.hermione_granger,
            R.drawable.lily_potter,
            R.drawable.minerva_mcgonagall,
            R.drawable.peterpettigrewww,
            R.drawable.remus_lupin,
            R.drawable.ron_weasley,
            R.drawable.sirius_black
        )
        val hufflepuff = listOf(
            R.drawable.cedric,
            R.drawable.ernestmacmillan,
            R.drawable.fat_friar,
            R.drawable.hannahabbout,
            R.drawable.helga_hufflepuf,
            R.drawable.leanne,
            R.drawable.newt_scamander,
            R.drawable.nymphadora_tonks,
            R.drawable.pomona_sprout,
            R.drawable.silvanus_kettleburn,
            R.drawable.tedlupin
        )
        val ravenclaw = listOf(
            R.drawable.chocang,
            R.drawable.filiusfilitwick,
            R.drawable.garrickollivander,
            R.drawable.glideroylockhart,
            R.drawable.lunalovegood,
            R.drawable.marcusbelby,
            R.drawable.myrtlewarren,
            R.drawable.padmapatil,
            R.drawable.quirinusquirrell,
            R.drawable.rowena_rawenclaw,
            R.drawable.sybilltrelawney
        )
        val slyterhin = listOf(
            R.drawable.andromedatonksss,
            R.drawable.bellatrix_lestrange,
            R.drawable.dolores_umbridge,
            R.drawable.draco_malfoy,
            R.drawable.evan_rosier,
            R.drawable.horace_slughorn,
            R.drawable.letalastrange,
            R.drawable.lucius_malfoy,
            R.drawable.narcissa_malfoy,
            R.drawable.severus_snape,
            R.drawable.voldemort
        )

        val choosenImages = gryfindor.shuffled().take(board.getCardCount() / 8) +
                hufflepuff.shuffled().take(board.getCardCount() / 8) +
                ravenclaw.shuffled().take(board.getCardCount() / 8) +
                slyterhin.shuffled().take(board.getCardCount() / 8)
        val randomizedImages = ((choosenImages + choosenImages)).shuffled()
        recyclerViewCard = findViewById(R.id.recyclerViewCards)

        cardMemory = randomizedImages.map { CardsDatasSituation(it, false, false) }
        //Recycler view adaptörü ve rowları için
        recyclerViewCard.adapter = RecyclerViewAdapter(this, board, cardMemory, object :
            CardClickListener {
            override fun onCardClicked(position: Int) {
                Log.i(TAG, "This Card Clicked $position")
                updateGameWithFlip(position)
            }
        })
        //recycler view eğer dinamik veriden oluşuyorsa bu sayede veri daha optimize çalışıyormuş !!!!
        recyclerViewCard.setHasFixedSize(true)
        recyclerViewCard.layoutManager = GridLayoutManager(this, board.getWidth())
    }

    private fun updateGameWithFlip(position: Int): Boolean {
        val card = cardMemory[position]
        recyclerViewCard.adapter?.notifyDataSetChanged()

        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            val foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)

            indexOfSingleSelectedCard = null
        }
        card?.faceup = !card.faceup
        return foundMatch
    }

    @SuppressLint("SuspiciousIndentation")
    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cardMemory[position1].identifier != cardMemory[position2].identifier) {
            return false
        }

        cardMemory[position1].matched = true
        cardMemory[position2].matched = true
        numPairsFound++

        if (numPairsFound == (board.getCardCount() / 2)) {
            Toast.makeText(this, "you won the game", Toast.LENGTH_LONG).show()
            val intent =
                Intent(this, CongratulationsPageActivity::class.java)
            startActivity(intent)
        }

        return true
    }

    private fun restoreCards() {
        for (card in cardMemory) {
            if (!card.matched) {
                card.faceup = false
            }
        }
    }


}

