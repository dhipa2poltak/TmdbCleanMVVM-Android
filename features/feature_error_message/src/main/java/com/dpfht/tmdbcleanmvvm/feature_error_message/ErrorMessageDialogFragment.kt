package com.dpfht.tmdbcleanmvvm.feature_error_message

import android.os.Bundle
import android.view.View
import com.dpfht.tmdbcleanmvvm.feature_error_message.databinding.FragmentErrorMessageDialogBinding
import com.dpfht.tmdbcleanmvvm.framework.base.BaseBottomSheetDialogFragment

class ErrorMessageDialogFragment: BaseBottomSheetDialogFragment<FragmentErrorMessageDialogBinding>(R.layout.fragment_error_message_dialog) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    isCancelable = false

    binding.btnOk.setOnClickListener {
      dismiss()
    }

    //val args = ErrorMessageDialogFragmentArgs.fromBundle(requireArguments())

    arguments?.let {
      val message = it.getString("message")

      binding.tvMessage.text = message
    }
  }
}
