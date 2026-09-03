package com.example.food_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class WhoseTurnFragment : Fragment() {

    private val names = mutableListOf("Anne", "Baba")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_whose_turn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameInput = view.findViewById<EditText>(R.id.name_input)
        val addButton = view.findViewById<Button>(R.id.btn_add)
        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup_WhoseTurn)
        val wheelView = view.findViewById<WheelView>(R.id.wheelView)
        val spinButton = view.findViewById<Button>(R.id.spinButton)
        val resultCard = view.findViewById<CardView>(R.id.resultCard)
        val resultText = view.findViewById<TextView>(R.id.resultText)

        fun refreshChips() {
            chipGroup.removeAllViews()
            names.forEachIndexed { index, name ->
                val chip = Chip(requireContext()).apply {
                    text = name
                    isCloseIconVisible = true
                    setChipBackgroundColorResource(R.color.sk_surface_raised)
                    setTextColor(resources.getColor(R.color.sk_text, null))
                    chipStrokeColor = resources.getColorStateList(R.color.sk_border, null)
                    chipStrokeWidth = 2f
                    closeIconTint = resources.getColorStateList(R.color.sk_remove, null)
                    setOnCloseIconClickListener {
                        names.removeAt(index)
                        wheelView.names = names.toList()
                        resultCard.visibility = View.GONE
                        refreshChips()
                    }
                }
                chipGroup.addView(chip)
            }
            wheelView.names = names.toList()
        }

        fun addName() {
            val value = nameInput.text.toString().trim()
            if (value.isEmpty()) return
            names.add(value)
            nameInput.text.clear()
            resultCard.visibility = View.GONE
            refreshChips()
        }

        addButton.setOnClickListener { addName() }
        nameInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addName()
                true
            } else {
                false
            }
        }

        spinButton.setOnClickListener {
            if (names.size < 2) return@setOnClickListener
            resultCard.visibility = View.GONE
            spinButton.isEnabled = false
            wheelView.spin { winner ->
                resultText.text = winner
                resultCard.visibility = View.VISIBLE
                spinButton.isEnabled = true
            }
        }

        refreshChips()
    }

    companion object {
        fun newInstance() = WhoseTurnFragment()
    }
}