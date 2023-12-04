package com.example.scrollviewtets

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.Gravity
import android.animation.Animator
import android.graphics.Typeface


class MainActivity : AppCompatActivity() {

    private val descriptions = listOf(
        "Neste módulo inicial você irá conhecer a anatomia do violão, aprender a postura correta e praticar como segurar o seu instrumento.",
        "Nesse segundo módulo, você irá desenvolver habilidades de coordenação motora das mãos, praticar exercícios de dedilhado e tocar acordes básicos.",
        "Nesse módulo, você aprenderá a ler cifras, acordes básicos, transição entre acordes e tocará uma música com acordes simples.",
        "O quarto módulo introduzirá conceitos musicais básicos, explica a linguagem musical e ensinará a tocar escalas simples.",
        "Neste módulo, você se aprofundará em técnicas de dedilhado, praticará arpejos e tocará uma música com dedilhado.",
        "No sexto módulo você irá experimentar diferentes métodos de aprendizado utilizando partituras e tablaturas. Além disso, você irá tocar uma música a partir de sua partitura.",
        "Neste sétimo módulo, você aprenderá o que é uma pestana e praticará tocá-las em acordes. No final deste módulo você ainda tocará uma música que requer pestanas.",
        "Neste módulo, você aprenderá sobre ritmos e batidas, praticará batidas rítmicas e tocará uma música seguindo um ritmo específico.",
        "No nono módulo será explorado como melhorar o som utilizando técnicas de uso dos dedos e você tocará uma música com ênfase no uso dos dedos.",
        "No último módulo você poderá escolher suas músicas favoritas para praticar tudo o que você aprendeu nessa trilha!"
    )

    private val nomeItem = listOf(
        "Introdução ao Violão",
        "Coordenação Motora",
        "Leitura de Cifras e Acordes",
        "Teoria Musical Básica",
        "Técnicas de Dedilhado",
        "Partituras e Tablaturas",
        "Pestanas (Barré)",
        "Ritmos e Batidas",
        "Enriquecimento de Som",
        "Motivação e Prática"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<LinearLayout>(R.id.container)

        for (i in descriptions.indices) {
            val listItem = createListItem(nomeItem[i], descriptions[i])
            container.addView(listItem)
        }
    }

    private fun createListItem(itemName: String, description: String): View {
        val context = this

        val rectangle = LinearLayout(context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(16, 25, 16, 25) // margens
        rectangle.layoutParams = params
        rectangle.setBackgroundResource(R.drawable.rounded_rectangle)
        rectangle.orientation = LinearLayout.VERTICAL

        val textView = TextView(context)
        textView.text = itemName
        textView.setPadding(0, 0, 0, 0)
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
        textView.gravity = Gravity.CENTER
        textView.setTypeface(null, Typeface.BOLD)

        val innerContainer = LinearLayout(context)
        innerContainer.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.ichord)
        val imageParams = LinearLayout.LayoutParams(
            120,
            100
        )
        imageParams.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        imageView.layoutParams = imageParams

        val progressImage = ImageView(context)
        progressImage.setImageResource(R.drawable.proggression_bar_100)
        val progressImageParams = LinearLayout.LayoutParams(
            400,
            100
        )
        progressImageParams.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        progressImage.layoutParams = progressImageParams

        val descriptionView = TextView(context)
        descriptionView.text = description
        descriptionView.setPadding(25, 0, 25, 25)
        descriptionView.maxLines = Int.MAX_VALUE
        descriptionView.ellipsize = null
        descriptionView.visibility = View.GONE

        innerContainer.addView(imageView)
        innerContainer.addView(progressImage)

        rectangle.addView(textView)
        rectangle.addView(innerContainer)
        rectangle.addView(descriptionView)

        rectangle.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                toggleViewExpansion(descriptionView)
            }
            true
        }

        return rectangle
    }




    private fun toggleViewExpansion(descriptionView: TextView) {
        val isViewExpanded = descriptionView.visibility == View.VISIBLE

        if (isViewExpanded) {
            collapseView(descriptionView)
        } else {
            expandView(descriptionView)
        }
    }



    private fun expandView(view: View) {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                (view.parent as View).width,
                View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        val targetHeight = view.measuredHeight

        val initialHeight = 0
        val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
        valueAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            view.layoutParams.height = animatedValue
            view.requestLayout()
        }

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        valueAnimator.duration = 500
        view.visibility = View.VISIBLE
        valueAnimator.start()
    }

    private fun collapseView(view: View) {
        val initialHeight = view.height

        val valueAnimator = ValueAnimator.ofInt(initialHeight, 0)
        valueAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            view.layoutParams.height = animatedValue
            view.requestLayout()
        }

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                view.layoutParams.height = 0
                view.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        valueAnimator.duration = 500
        valueAnimator.start()
    }
}