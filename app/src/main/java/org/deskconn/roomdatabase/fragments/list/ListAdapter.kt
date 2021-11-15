package org.deskconn.roomdatabase.fragments.list

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentProvider
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_items.view.*
import org.deskconn.roomdatabase.MainActivity
import org.deskconn.roomdatabase.R
import org.deskconn.roomdatabase.User
import org.deskconn.roomdatabase.data.UserViewModel
import org.deskconn.roomdatabase.fragments.update.UpdateFragmentArgs

class ListAdapter(activity: Activity) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()
    private var listFragment: ListFragment = ListFragment()
    private var mActivity: Activity = activity




//    private val args by navArgs<UpdateFragmentArgs>()

    //private lateinit var userViewModel: UserViewModel

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.idTextView.text = currentItem.id.toString()
        holder.itemView.textViewFirstName.text = currentItem.firstName
        holder.itemView.textViewLastName.text = currentItem.lastName
        holder.itemView.textViewAge.text = currentItem.age.toString()

        holder.itemView.updateId.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.listItemsId.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.deleteId.setOnClickListener {
            mActivity.let { it1 -> listFragment.deleteUserDialog(currentItem, it1) }
            //Log.e("DeleteClick, ")
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
}