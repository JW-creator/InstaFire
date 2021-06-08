package com.example.instafire

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instafire.models.Post
import java.math.BigInteger
import java.security.MessageDigest

private const val TAG = "PostsAdapter"
class PostsAdapter (val context: Context, val posts: List<Post>)
    : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        Log.i(TAG, "createViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val ivPost: ImageView = itemView.findViewById(R.id.ivPost)
        private val tvRelativeTime: TextView = itemView.findViewById(R.id.tvRelativeTime)
        private val ivProfileImage: ImageView = itemView.findViewById(R.id.ivProfileImage)

        fun bind(post: Post) {
            val username = post.user?.username as String
            tvUsername.text = username
            tvDescription.text = post.description
            Glide.with(context).load(post.imageUrl).into(ivPost)
            Glide.with(context).load(getProfileImageUrl(username)).into(ivProfileImage)
            tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)
        }

        private fun getProfileImageUrl(username: String): String {
            val digest = MessageDigest.getInstance("MD5")
            val hash = digest.digest(username.toByteArray())
            val bigInt = BigInteger(hash)
            val hex = bigInt.abs().toString(16)
            return "https://www.gravatar.com/avatar/$hex?d=identicon"
        }
    }
}