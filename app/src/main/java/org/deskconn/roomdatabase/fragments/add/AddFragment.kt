package org.deskconn.roomdatabase.fragments.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import org.deskconn.roomdatabase.MainActivity
import org.deskconn.roomdatabase.R
import org.deskconn.roomdatabase.User
import org.deskconn.roomdatabase.data.UserViewModel

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java) Old Method using
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java] // Method without using get

        view.addButton.setOnClickListener {
            insertDataToDataBase()
        }

        return view
    }

    @SuppressLint("CommitTransaction")
    private fun insertDataToDataBase() {
        val firstName = firstNameET.text.toString()
        val lastName = lastNameET.text.toString()
        val age = ageET.text.toString()

        if (firstName.isEmpty() && lastName.isEmpty() && age.isEmpty()) {
            firstNameET.error = "First Name required"
            lastNameET.error = "Last Name required"
            ageET.error = "Age required"
            //Create user object

        } else if (firstName.isEmpty()) {
            firstNameET.error = "First Name required"
        } else if (lastName.isEmpty()) {
            lastNameET.error = "Last Name required"
        } else if (age.isEmpty()) {
            ageET.error = "Age required"
        } else {
            val user = User(0, firstName, lastName, Integer.parseInt(age))

            //Add data to database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()

            //Navigate Back
            findNavController().popBackStack(R.id.action_addFragment_to_listFragment, false)

            Log.e("Fragment", "${findNavController().popBackStack()}")
        }
    }

}