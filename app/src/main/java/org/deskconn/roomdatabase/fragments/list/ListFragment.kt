package org.deskconn.roomdatabase.fragments.list

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.deskconn.roomdatabase.R
import org.deskconn.roomdatabase.User
import org.deskconn.roomdatabase.data.UserViewModel

class ListFragment : Fragment() {

    //private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //RecyclerView
        val listAdapter = activity?.let { ListAdapter(it) }
        val recyclerView = view.recyclerViewId
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            listAdapter?.setData(user)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }

    /*private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(requireContext())
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }*/

    fun deleteUserDialog(user: User, activity: Activity) {
        println("Name: ${user.firstName}")
        userViewModel = UserViewModel(application = Application())
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton("Yes") { _, _ ->
            userViewModel.deleteUser(user)
            Toast.makeText(
                activity,
                "${user.firstName} ${user.lastName} Successfully deleted",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        builder.setIcon(R.drawable.ic_warning)
        builder.setTitle("Delete ${user.firstName} ${user.lastName}")
        builder.setMessage("Are you sure? Do you want to delete ${user.firstName} ${user.lastName}?")
        builder.create().show()
    }
}