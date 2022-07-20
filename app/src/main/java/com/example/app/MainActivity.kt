package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.app.databinding.ActivityMainBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var useKeyword: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGetImg.setOnClickListener {
            getSearchRandomImg()
        }

        binding.edTextKeyword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener getSearchRandomImg()
            }
            return@setOnEditorActionListener false
        }

        binding.checkBox.setOnClickListener {
            this.useKeyword = binding.checkBox.isChecked
            updateUI()
        }
        binding.checkBox.setOnCheckedChangeListener { _, isCheked ->
            //todo
        }

        updateUI()

//        binding.btnGetImg.setOnLongClickListener() {
//            getRandomNumber()
//        }
    }

    private fun getSearchRandomImg(): Boolean {
        val keyword = binding.edTextKeyword.text.toString()
        if (useKeyword && keyword.isBlank()) {
            binding.edTextKeyword.error = "Keyword is empty"
            return true
        }
        val encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random/800*600?${if (useKeyword) "?$encodedKeyword" else ""}")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.imgView)
        return false
    }

    private fun updateUI() = with(binding){
        checkBox.isChecked = useKeyword
        if (useKeyword) {
            edTextKeyword.visibility = View.VISIBLE
        } else {
            edTextKeyword.visibility = View.GONE
        }
    }

//    private fun setRandomImg() {
//        with(binding.imgView) {
//            layoutParams.width = resources.getDimensionPixelSize(R.dimen.img_width)
//            layoutParams.height = resources.getDimensionPixelSize(R.dimen.img_height)
//            requestLayout()
//            Glide.with(this)
//                .load("https://source.unsplash.com/random/800*600")
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(binding.imgView)
//        }
//    }
//
//    private fun getRandomNumber(): Boolean {
//        val number = Random.nextInt(100)
//        val message = getString(R.string.random_number, number)
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//        return false
//    }
}
