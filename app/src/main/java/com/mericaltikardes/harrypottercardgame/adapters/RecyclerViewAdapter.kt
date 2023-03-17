package com.mericaltikardes.harrypottercardgame.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mericaltikardes.harrypottercardgame.utils.BoardCardSize
import com.mericaltikardes.harrypottercardgame.CardsDatasSituation
import com.mericaltikardes.harrypottercardgame.R
import kotlin.math.min

class RecyclerViewAdapter(
    private val context: Context,
    private val board: BoardCardSize,
    private val card: List<CardsDatasSituation>,
    private val cardClickListener: CardClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val MARGIN_SIZE = 10
        private const val TAG = "RecyclerViewAdapter"
    }

    interface CardClickListener {
        fun onCardClicked(position: Int) {}
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {
            val cardMemory = card[position]
            imageButton.setImageResource(
                if (card[position].faceup) {
                    card[position].identifier
                } else {
                    R.drawable.harry_potter_cards_back_image
                }
            )

            imageButton.setOnClickListener {
                Log.i(TAG, "Clicked on position:$position")
                cardClickListener.onCardClicked(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardwith = parent.width / board.getWidth() - (2 * MARGIN_SIZE)
        val cardheight = parent.height / board.getHeight() - (2 * MARGIN_SIZE)
        val cardSideLength = min(cardheight, cardwith)
        val view = LayoutInflater.from(context).inflate(R.layout.single_card, parent, false)
        val crdViewSize =
            view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
        crdViewSize.width = cardSideLength
        crdViewSize.height = cardSideLength
        crdViewSize.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int = board.getCardCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ViewHolder(holder.itemView).bind(position)

    }


}
