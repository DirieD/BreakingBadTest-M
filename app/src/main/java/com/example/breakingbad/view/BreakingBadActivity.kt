package com.example.breakingbad.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.controller.BreakingBadController
import com.example.breakingbad.databinding.CharacterListBinding
import com.example.breakingbad.extensions.GenericAdapter
import com.example.breakingbad.extensions.GenericAdapter.OnListItemViewClickListener
import com.example.breakingbad.viewmodel.CharacterListViewModel
import com.example.breakingbad.viewmodel.CharacterViewModel
import com.example.lib.controllers
import kotlinx.android.synthetic.main.activity_breaking_bad.*
import kotlinx.android.synthetic.main.character_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class BreakingBadActivity : AppCompatActivity() {

    private val characterViewModel : CharacterListViewModel by viewModel()
    private val controller : BreakingBadController by controllers()
    private lateinit var recyclerViewAdapter: GenericAdapter<CharacterViewModel>
    private lateinit var spinnerListener: OnItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breaking_bad)
        setupView()
        setupObservers()
        setupListeners()
    }

    private fun setupView(){
        val binding = CharacterListBinding.inflate(layoutInflater)
        binding.viewModel = characterViewModel
        character_list_Container.addView(binding.root)
        controller.fetchCharacters()
        recyclerViewAdapter = GenericAdapter(R.layout.character_row)
        character_list.adapter = recyclerViewAdapter
        character_list.layoutManager = LinearLayoutManager(this)
        ArrayAdapter.createFromResource(this, R.array.appearances_array, android.R.layout.simple_dropdown_item_1line). also {
            spinnerAdapter -> spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            appearance_spinner.adapter = spinnerAdapter
        }
    }

    private fun setupObservers(){
        characterViewModel.characterProfilesLiveData.observe(this, Observer { recyclerViewAdapter.addItems(it) })
    }

    private fun setupListeners(){
        details_backdrop.setOnClickListener { controller.characterMinimised() }

        recyclerViewAdapter.setOnListItemViewClickListener( object : OnListItemViewClickListener {
            override fun onClick(view: View, position: Int) {
                controller.fetchCharacterById(recyclerViewAdapter.getItem(position).char_id)
            }
        })

        character_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{controller.searchCharacters(appearance_spinner.selectedItem.toString(),it)}
                return false
            }

        })

        spinnerListener = object: OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                controller.searchCharacters(appearance_spinner.selectedItem.toString(),"")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val search = character_search.query.toString()
                controller.searchCharacters(appearance_spinner.selectedItem.toString(),character_search.query.toString())
            }

        }
        appearance_spinner.onItemSelectedListener = spinnerListener

    }
}
