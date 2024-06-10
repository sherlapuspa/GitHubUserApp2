package com.dicoding.githubuserapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.vm.UserDetailVM
import com.dicoding.githubuserapp.databinding.ActivityUserDetailBinding
import com.dicoding.githubuserapp.ui.adapter.PagerAdapt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_UNAME = "extra_uname"
        const val EXTRA_UID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar"
        const val EXTRA_HTML_URL = "extra_html"
    }

    private lateinit var bind: ActivityUserDetailBinding
    private lateinit var vm: UserDetailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.loadingBar.visibility = View.VISIBLE

        val uname = intent.getStringExtra(EXTRA_UNAME)
        val id = intent.getIntExtra(EXTRA_UID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val htmlUrl = intent.getStringExtra(EXTRA_HTML_URL)
        val bundle = Bundle().apply { putString(EXTRA_UNAME, uname) }

        val btnPrev: ImageButton = findViewById(R.id.btn_prev)
        btnPrev.setOnClickListener(this)

        vm = ViewModelProvider(this).get(UserDetailVM::class.java)

        vm.setUserDetails(uname.toString())
        vm.getUserDetails().observe(this, { userDetails ->
            if (userDetails != null) {
                bind.apply {
                    tvName.text = userDetails.name
                    tvLogin.text = userDetails.login
                    tvFollowers.text = "${userDetails.followers} Followers"
                    tvFollowing.text = "${userDetails.following} Following"

                    Glide.with(this@UserDetailActivity).load(userDetails.avatarUrl).centerCrop()
                        .into(imgUserAvt)
                }

                bind.loadingBar.visibility = View.GONE
            }
        })
        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = vm.checkUserExistence(id)
            withContext(Dispatchers.Main) {
                isChecked = count ?: 0 > 0
                bind.toggleFav.isChecked = isChecked
            }
        }

        bind.toggleFav.setOnClickListener {
            isChecked = !isChecked
            val validUname = uname ?: ""
            val validAvatarUrl = avatarUrl ?: ""
            val validHtmlUrl = htmlUrl ?: ""
            if (isChecked) {
                vm.addToFavUser(validUname, id, validAvatarUrl, validHtmlUrl)
            } else {
                vm.deleteFromFav(id)
            }
            bind.toggleFav.isChecked = isChecked
        }
        val pagerAdapt = PagerAdapt(this, supportFragmentManager, bundle)
        bind.apply {
            vpFollow.adapter = pagerAdapt
            tlFollow.setupWithViewPager(vpFollow)
        }

        bind.btnShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check Github Profile of ${intent.getStringExtra(EXTRA_UNAME)} at ${
                        intent.getStringExtra(EXTRA_HTML_URL)
                    }"
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Share With"))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_prev -> {
                val toPrev = Intent(this@UserDetailActivity, MainActivity::class.java)
                startActivity(toPrev)
                finish()
            }
        }
    }

}