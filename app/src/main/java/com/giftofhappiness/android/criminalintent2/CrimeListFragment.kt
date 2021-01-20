package com.giftofhappiness.android.criminalintent2


import android.content.Context
import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.telecom.Call
import android.text.format.DateFormat.format
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.format.DateFormat
import androidx.lifecycle.Observer
import java.util.*

private const val TAG ="CrimeListFragment"

class CrimeListFragment: Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
//    private var adapter : CrimeAdapter? = null
    private var adapter:CrimeAdapter? = CrimeAdapter(emptyList())



    private val crimeListViewModel: CrimeListViewModel by lazy {

        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }




    companion object{
        fun newInstance(): CrimeListFragment{
            return CrimeListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner, Observer { crimes ->
            crimes?.let{
                Log.i(TAG,"Got crimes ${crimes.size}")
                updateUI(crimes)
            }
        })



    }
    private inner class CrimeHolder(view:View):RecyclerView.ViewHolder(view),View.OnClickListener{

        private lateinit var crime: Crime
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView : TextView = itemView.findViewById(R.id.crime_date)



        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(context,"${crime.title} pressed!", Toast.LENGTH_SHORT).show()
            callbacks?.onCrimeSelected(crime.id)
        }


        fun bind(crime: Crime) {

            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = DateFormat.format("EEEE,MMM dd,yyyy", this.crime.date)
            if(crime.isSolved==true){ solvedImageView.visibility = View.VISIBLE
            }else{
                solvedImageView.visibility = View.GONE
            }

        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>): RecyclerView.Adapter<CrimeHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

    }

    private fun updateUI(crimes: List<Crime>){


        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    interface Callbacks {

     fun onCrimeSelected(crimeId: UUID)

    }
    private var callbacks : Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }




}






