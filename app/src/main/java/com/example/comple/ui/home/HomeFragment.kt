package com.example.comple.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.comple.R
import com.example.comple.databinding.FragmentHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Contenedor para las vistas dinámicas
        val containerLayout: LinearLayout = root.findViewById(R.id.container_home)

        // Conexión a Firestore
        val db = Firebase.firestore
        db.collection("completos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val nombre = document.getString("nombre")
                    val descripcion = document.getString("descripcion")
                    val precio = document.getLong("precioAproximado")
                    val imagenURL = document.getString("imagenURL")

                    // Crear un TextView para el nombre y la descripción
                    val textView = TextView(requireContext()).apply {
                        text = "$nombre\n$descripcion ($precio CLP)"
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                    }


                    val imageView = ImageView(requireContext()).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            500
                        )
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setPadding(8, 8, 8, 8)
                    }

                    // Cargar la imagen con Glide
                    Glide.with(requireContext())
                        .load(imagenURL)
                        .placeholder(R.drawable.completo) // Imagen temporal
                        .error(R.drawable.completo) // Imagen en caso de error
                        .into(imageView)

                    // Agregar las vistas al LinearLayout
                    containerLayout.addView(textView)
                    containerLayout.addView(imageView)
                }
            }
            .addOnFailureListener { exception ->
                val errorTextView = TextView(requireContext()).apply {
                    text = "Error al cargar datos: ${exception.message}"
                    textSize = 16f
                    setPadding(8, 8, 8, 8)
                }
                containerLayout.addView(errorTextView)
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
