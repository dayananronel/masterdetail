package com.ronel.masterdetail.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ronel.masterdetail.R
import com.ronel.masterdetail.adapter.ITunesAdapter
import com.ronel.masterdetail.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    //init our viewmodel
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMainBinding.inflate(inflater)


        binding.lifecycleOwner = this
        binding.viewModel  = viewModel

        binding.movieList.adapter = ITunesAdapter(ITunesAdapter.OnClickListener{
            viewModel.displayMovies(it)
        })

        viewModel.navigateToSelectedMovie.observe(this, Observer {
            if(null != it){
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root

    }
}