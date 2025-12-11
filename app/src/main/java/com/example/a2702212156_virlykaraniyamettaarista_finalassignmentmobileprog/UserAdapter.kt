package com.example.a2702212156_virlykaraniyamettaarista_finalassignmentmobileprog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

typealias OnUserClickListener = (User) -> Unit

class UserAdapter(
    private val userList: List<User>,
    private val listener: OnUserClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, listener)
    }

    override fun getItemCount(): Int = userList.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.tv_username)
        private val emailTextView: TextView = itemView.findViewById(R.id.tv_email)
        private val detailButton: Button = itemView.findViewById(R.id.btn_view_detail) // <-- Find the new button

        fun bind(user: User, listener: OnUserClickListener) {
            usernameTextView.text = user.username
            emailTextView.text = user.email

            itemView.setOnClickListener {
                listener(user)
            }
            detailButton.setOnClickListener {
                listener(user)
            }

        }
    }
}