package com.hebaelsaid.android.athletesapp.ui.feature.homelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hebaelsaid.android.athletesapp.databinding.FragmentHomeListBinding

class HomeListFragment : Fragment() {
    private lateinit var binding: FragmentHomeListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeListBinding.inflate(inflater, container, false)
        return binding.root
    }

}