package com.example.comple.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.comple.R

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val ageSpinner: Spinner = view.findViewById(R.id.ageSpinner)

        // Lista de edades (15 a 40 años)
        val ageList = (15..40).map { it.toString() }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ageList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = adapter

        // Configuración del botón para redirigir al Login
        val loginRedirectTextView: TextView = view.findViewById(R.id.loginRedirectTextView)
        loginRedirectTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_nav_login)
        }

        return view
    }
}
