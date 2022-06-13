package com.example.doctruyen

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.doctruyen.Adapter.MyApplication
import com.example.doctruyen.databinding.ActivityProfileEditBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityProfileEditBinding
    private  var imageUrl:Uri ?= null
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.updateBtn.setOnClickListener {
            validateData()
        }
        binding.profileTv.setOnClickListener {
            showImageAttachMenu()
        }
    }
    private var name = ""
    private fun validateData() {
        name = binding.nameEt.text.toString().trim()
        if (name.isEmpty()){
            Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show()
        }else{
            if(imageUrl==null){
                updateProfile("")
            }else{
                updateImage()

            }
        }
    }

    private fun updateImage() {
        progressDialog.setMessage("Uploading profile image")
        progressDialog.show()
        val  filePathAndName = "ProfileImages/"+firebaseAuth.uid
        val reference = FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUrl!!)
            .addOnSuccessListener {taskSnapshot->
                val uirTask:Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uirTask.isSuccessful);
                val uploadedImageUrl = "${uirTask.result}"
                updateProfile(uploadedImageUrl)
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failled to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    private fun updateProfile(uploadedImageUrl: String) {
        progressDialog.setMessage("Updating profile...")
        val hashMap:HashMap<String,Any> = HashMap()
        hashMap["name"]= "$name"
        if(imageUrl!=null){
            hashMap["profileImage"]= uploadedImageUrl
        }
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Log.d("T","$hashMap")
                Toast.makeText(this,"profile to update ",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failled to update ${e.message}",Toast.LENGTH_SHORT).show()


            }
    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"


                    val formatteDate = MyApplication.formatTimeStamp(timestamp.toLong())
                    binding.nameEt.setText(name)


                    try {
                        Glide.with(this@ProfileEditActivity)
                            .load(profileImage)
                            .placeholder(R.drawable.ic_person_gray)
                            .into(binding.profileTv)
                    } catch (e: Exception) {

                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
    private fun showImageAttachMenu(){
        val popuMenu = PopupMenu(this,binding.profileTv)
        popuMenu.menu.add(Menu.NONE,0,0,"Camera")
        popuMenu.menu.add(Menu.NONE,1,1,"Gallery")
        popuMenu.show()
        popuMenu.setOnMenuItemClickListener { item->
           val id = item.itemId
            if(id==0){
                pickImageCamera()
            } else if(id==1){
                pickImageGallery()
            }
            true

        }
    }

    private fun pickImageGallery() {
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE,"Temp_titlle")
//        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp_description")
//        imageUrl = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl)
//        cameraActivityResultLauncher.launch(intent)
                val intent = Intent(Intent.ACTION_PICK)
        intent.type= "image/*"
        galleryActivityResultLaucher.launch(intent)
    }

    private fun pickImageCamera() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type= "image/*"
//        galleryActivityResultLaucher.launch(intent)
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Temp_titlle")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp_description")
        imageUrl = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl)
        cameraActivityResultLauncher.launch(intent)
    }
    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback <ActivityResult>{ result->
            if(result.resultCode== Activity.RESULT_OK){
                val data = result.data
//                imageUrl =  data!!.data
                binding.profileTv.setImageURI(imageUrl)

            } else{
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
            }

        }
    )
    private val  galleryActivityResultLaucher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback <ActivityResult>{ result->
            if(result.resultCode== Activity.RESULT_OK){
                val data = result.data
                imageUrl =  data!!.data
                binding.profileTv.setImageURI(imageUrl)
                Log.e("Tesst","${data}")
            } else{
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
            }

        }
    )
}