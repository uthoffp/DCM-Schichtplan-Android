package com.uthoff.dcm.android.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.datasource.CompanyRepository
import com.uthoff.dcm.android.viewmodel.CompanyViewModel


class ContactFragment : Fragment() {
    private val viewModel: CompanyViewModel = CompanyViewModel()

    private lateinit var imgCompany: ImageView
    private lateinit var txtCompany: TextView
    private lateinit var txtStreet: TextView
    private lateinit var txtCity: TextView
    private lateinit var txtPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_contact, container, false)
        setUpUi(view)
        setUpViewModel()
        return view
    }

    private fun setUpUi(view: View) {
        imgCompany = view.findViewById(R.id.frag_contact_img_company)
        txtCompany = view.findViewById(R.id.frag_contact_txt_company)
        txtStreet = view.findViewById(R.id.frag_contact_txt_street)
        txtCity = view.findViewById(R.id.frag_contact_txt_city)
        txtPhone = view.findViewById(R.id.frag_contact_txt_phone)
    }

    private fun setUpViewModel() {
        viewModel.company.observe(viewLifecycleOwner, {
            val cityPostCode = "${it.Postcode}, ${it.City}"
            txtCompany.text = it.CompanyName1
            txtCity.text = cityPostCode
            txtStreet.text = it.Street
            txtPhone.text  = it.Phone
        })
    }
}