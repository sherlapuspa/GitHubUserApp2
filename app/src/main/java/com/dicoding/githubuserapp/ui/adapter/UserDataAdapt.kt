package com.dicoding.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.data.response.UserData
import com.dicoding.githubuserapp.databinding.UserDataItemBinding

class UserDataAdapt : RecyclerView.Adapter<UserDataAdapt.UserVH>() {

    private var listData = ArrayList<UserData>()
    private var callback: OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.callback = callback
    }

    fun setUserList(users: ArrayList<UserData>) {
        listData.clear()
        listData.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserVH(val bind: UserDataItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(users: UserData) {
            bind.root.setOnClickListener {
                callback?.onItemClicked(users)
            }

            bind.apply {
                Glide.with(itemView).load(users.avatarUrl).centerCrop().into(imgUserAvt)

                tvLogin.text = users.login
                tvUrl.text = users.htmlUrl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val views = UserDataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserVH(views)

    }

    override fun onBindViewHolder(holder: UserVH, loc: Int) {
        holder.bind(listData[loc])
    }

    override fun getItemCount(): Int = listData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserData)
    }

}