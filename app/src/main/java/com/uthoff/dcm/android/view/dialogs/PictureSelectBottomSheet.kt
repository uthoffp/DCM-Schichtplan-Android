package com.uthoff.dcm.android.view.dialogs;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.viewmodel.AbRequestViewModel;

class PictureSelectBottomSheet(private val viewModel: AbRequestViewModel) :
    BottomSheetDialogFragment() {
    private lateinit var v: View
    private lateinit var sourceLauncher: ActivityResultLauncher<Intent>
    private lateinit var btnCamera: ImageButton
    private lateinit var btnGallery: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.bottomsheet_picture_select, container, false)
        setUpUi()

        sourceLauncher=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent: Intent? = result.data
                    viewModel.setImage(intent?.extras)
                    dismiss()
                }
            }
        return v
    }

    private fun setUpUi() {
        btnCamera = v.findViewById(R.id.btmsht_picture_btn_camera)
        btnGallery = v.findViewById(R.id.btmsht_picture_btn_galery)

        btnCamera.setOnClickListener { onClickCamera() }
        btnGallery.setOnClickListener { onClickGallery() }
    }

    private fun onClickCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        sourceLauncher.launch(intent)
    }

    private fun onClickGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        sourceLauncher.launch(intent)
    }
}
