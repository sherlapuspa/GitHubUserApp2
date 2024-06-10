package com.dicoding.githubuserapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.database.UserFav
import com.dicoding.githubuserapp.data.response.UserData
import com.dicoding.githubuserapp.databinding.ActivityUserFavBinding
import com.dicoding.githubuserapp.ui.adapter.UserDataAdapt
import com.dicoding.githubuserapp.vm.UserFavVM

class UserFavActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var bind: ActivityUserFavBinding
    private lateinit var adapt: UserDataAdapt
    private lateinit var vm: UserFavVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityUserFavBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnPrev.setOnClickListener(this)

        adapt = UserDataAdapt()
        adapt.notifyDataSetChanged()

        vm = ViewModelProvider(this).get(UserFavVM::class.java)

        adapt.setOnItemClickCallback(object : UserDataAdapt.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                Intent(this@UserFavActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_UNAME, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_UID, data.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR_URL, data.avatarUrl)
                    it.putExtra(UserDetailActivity.EXTRA_HTML_URL, data.htmlUrl)
                    startActivity(it)
                }
            }

        })

        bind.apply {
            rvFavUser.setHasFixedSize(true)
            rvFavUser.layoutManager = LinearLayoutManager(this@UserFavActivity)
            rvFavUser.adapter = adapt
        }
        vm.getFavUser()?.observe(this, {
            if (it != null) {
                val mappedList = mapUserFavsToList(it)
                adapt.setUserList(mappedList)
            }
        })
    }

    private fun mapUserFavsToList(userList: List<UserFav>): ArrayList<UserData> {
        val mappedListUsers = ArrayList<UserData>()
        for (user in userList) {
            val mappedUser = UserData(
                user.login, user.id, user.avatarUrl, user.htmlUrl
            )
            mappedListUsers.add(mappedUser)
        }
        return mappedListUsers
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_prev -> {
                val toPrev = Intent(this@UserFavActivity, MainActivity::class.java)
                startActivity(toPrev)
            }
        }
    }
}