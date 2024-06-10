package com.dicoding.githubuserapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.databinding.FragmentUserFollowBinding
import com.dicoding.githubuserapp.ui.activity.UserDetailActivity
import com.dicoding.githubuserapp.ui.adapter.UserDataAdapt
import com.dicoding.githubuserapp.vm.FollowingVM

class FollowingFragment : Fragment(R.layout.fragment_user_follow) {

    private var _bind: FragmentUserFollowBinding? = null
    private val bind get() = _bind!!
    private lateinit var vm: FollowingVM
    private lateinit var adapt: UserDataAdapt
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
        username = arguments?.getString(UserDetailActivity.EXTRA_UNAME).toString()

        _bind = FragmentUserFollowBinding.bind(view)

        adapt = UserDataAdapt()
        adapt.notifyDataSetChanged()

        bind.apply {
            rvFollows.setHasFixedSize(true)
            rvFollows.layoutManager = LinearLayoutManager(activity)
            rvFollows.adapter = adapt
        }
        loadingBar(true)
        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingVM::class.java
        )
        vm.setFollowingList(username)
        vm.getFollowingList().observe(viewLifecycleOwner, {
            if (it != null) {
                adapt.setUserList(it)
                loadingBar(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun loadingBar(isLoadingBar: Boolean) {
        if (isLoadingBar) {
            bind.loadingBar.visibility = View.VISIBLE
        } else {
            bind.loadingBar.visibility = View.GONE
        }
    }
}