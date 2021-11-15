package org.deskconn.roomdatabase.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import org.deskconn.roomdatabase.R
import org.deskconn.roomdatabase.User
import org.deskconn.roomdatabase.data.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        //This view fetch the data from list update icon clicked position
        view.firstNameUpdateET.setText(args.currentUser.firstName)
        view.lastNameUpdateET.setText(args.currentUser.lastName)
        view.ageUpdateET.setText(args.currentUser.age.toString())

        view.updateButton.setOnClickListener {
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName = firstNameUpdateET.text.toString()
        val lastName = lastNameUpdateET.text.toString()
        val age = ageUpdateET.text.toString()

        if (firstName.isEmpty() && lastName.isEmpty() && age.isEmpty()) {
            firstNameUpdateET.error = "First Name required"
            lastNameUpdateET.error = "Last Name required"
            ageUpdateET.error = "Age required"
            //Create user object

        } else if (firstName.isEmpty()) {
            firstNameUpdateET.error = "First Name required"
        } else if (lastName.isEmpty()) {
            lastNameUpdateET.error = "Last Name required"
        } else if (age.isEmpty()) {
            ageUpdateET.error = "Age required"
        } else {
            //Create user object
            val updateUser = User(args.currentUser.id, firstName, lastName, Integer.parseInt(age))

            //Add data to database
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()

            //Navigate Back
            findNavController().popBackStack(R.id.action_updateFragment_to_listFragment, false)
            Log.e("Fragment", "${findNavController().popBackStack()}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menue_delete){
            deleteUserDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUserDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),
                "${args.currentUser.firstName} ${args.currentUser.lastName} Successfully removed",
                Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") {_, _, ->}
        builder.setIcon(R.drawable.ic_warning)
        builder.setTitle("Delete ${args.currentUser.firstName} ${args.currentUser.lastName}")
        builder.setMessage("Are you sure? Do you want to delete ${args.currentUser.firstName} ${args.currentUser.lastName}?")
        builder.create().show()
    }
}