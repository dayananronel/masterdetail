package com.ronel.masterdetail.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ronel.masterdetail.R
import com.ronel.masterdetail.adapter.MoviesAdapter
import com.ronel.masterdetail.bean.ITunes
import com.ronel.masterdetail.databinding.FragmentMainBinding
import com.ronel.masterdetail.utils.ConnectionLiveData
import com.ronel.masterdetail.utils.isConnected

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application))
                .get(MainViewModel::class.java)
    }

    //Adapter
    private var viewModelAdapter: MoviesAdapter? = null

    //network validation
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding : FragmentMainBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main,
                container,false)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //instantiate network receiver
        connectionLiveData  = ConnectionLiveData(requireContext())

        viewModelAdapter  = MoviesAdapter(MoviesClick {
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })

        binding.root.findViewById<RecyclerView>(R.id.movie_list).apply {
            adapter = viewModelAdapter
        }

        binding.swipeRefresh.setOnRefreshListener{
            viewModel.getITunesMediaFromRepository().apply {
                viewModel.isNetworkAvailable.observe(viewLifecycleOwner, Observer {
                    when(it){
                        false -> {
                            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }


        //observe network receiver
        connectionLiveData.observe(this, Observer {
            viewModel.isNetworkAvailable.value = it
        })
        viewModel.isNetworkAvailable.value = requireContext().isConnected

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movies.observe(viewLifecycleOwner, Observer<List<ITunes>> { movie ->
            movie?.apply {
                viewModelAdapter?.movies = movie
            }
        })
    }

    class MoviesClick(val block: (ITunes) -> Unit) {
        /**
         * Called when a movie is clicked
         *
         * @param movie the movie that was clicked
         */
        fun onClick(movie: ITunes) = block(movie)
    }
}